package task.concatenator;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import task.concatenator.UI.SimpleUserInterface;
import task.concatenator.utils.PathSorter;

public class TextFileConcatenator {
	private final String rootDirStr;
	private final String concatFilePathStr;
	private final PathSorter.PathSortOption sortOpt;
	
	public TextFileConcatenator (String rootDirStr, String concatFilePathStr, PathSorter.PathSortOption sortOpt) {
		this.rootDirStr = rootDirStr;
		this.concatFilePathStr = concatFilePathStr;
		this.sortOpt = sortOpt;
	}
	
	public void work () {
		Path rootDir = getDirPath(rootDirStr);
		List<Path> rawFileList = getFileList(rootDir);
		List<Path> fileList = sortPaths(sortOpt, rawFileList, rootDir);
		concatenateFileListIntoFile(concatFilePathStr, fileList);
	}
	
	private Path getDirPath (String dirPathStr) {
		Path rootDirPath = FileSystems.getDefault().getPath(dirPathStr);
		if (Files.exists(rootDirPath)) {
			return rootDirPath.toAbsolutePath();
		} else {
			SimpleUserInterface.handleException("Directory not found: " + rootDirPath);
			return null;
		}
	}
	private List<Path> sortPaths (PathSorter.PathSortOption option, List<Path> rawFileList, Path rootDir) {
		PathSorter pathSorter = PathSorter.getPathSorter(option);
		pathSorter.build(rawFileList, rootDir);
		return pathSorter.getSortedPathList();
	}
	private List<Path> getFileList (Path rootDir) {
		List<Path> fileList = new ArrayList<>();
		try {
			Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile (Path file, BasicFileAttributes attr) {
					if (attr.isRegularFile()) {
						fileList.add(file.toAbsolutePath());
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException while walking a file tree");
		}
		return fileList;
	}
	private void concatenateFileListIntoFile (String concatFilePathStr, List<Path> files) {
		Path concatFilePath = FileSystems.getDefault().getPath(concatFilePathStr);
		try {
			FileChannel toChannel = FileChannel.open(concatFilePath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			FileChannel fromChannel;
			for (Path file : files) {
				fromChannel = FileChannel.open(file, StandardOpenOption.READ);
				fromChannel.transferTo(0, fromChannel.size(), toChannel);
				fromChannel.close();
			}
			toChannel.close();
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException during concatenation");
		}
	}
}
