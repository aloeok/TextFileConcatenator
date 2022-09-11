package task.concatenator.provider;

import task.concatenator.UI.SimpleUserInterface;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class FileListProvider extends SimpleFileVisitor<Path> {
	private List<Path> fileList;
	
	public FileListProvider () {
		fileList = null;
	}
	
	public void loadFileList (Path rootDir) {
		fileList = new ArrayList<>();
		try {
			Files.walkFileTree(rootDir, this);
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException while walking a file tree");
		}
	}
	
	@Override
	public FileVisitResult visitFile (Path file, BasicFileAttributes attr) {
		if (attr.isRegularFile()) {
			fileList.add(file.toAbsolutePath());
		}
		return FileVisitResult.CONTINUE;
	}
	
	public List<Path> getPathList () {
		return fileList;
	}
}
