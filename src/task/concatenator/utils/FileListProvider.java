package task.concatenator.utils;

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
 * DONE
 * class to create sorted list of files tht are in provided directory
 */
public class FileListProvider extends SimpleFileVisitor<Path> {
	private List<ComparablePathWrapper> wrappedPathList;
	private List<Path> pathList;
	
	/** TESTED */
	public FileListProvider (Path rootDir) throws IOException {
		wrappedPathList = new ArrayList<>();
		Files.walkFileTree(rootDir, this);
		Collections.sort(wrappedPathList);
		pathList = makeRawList();
	}
	
	@Override
	public FileVisitResult visitFile (Path file, BasicFileAttributes attr) {
		if (attr.isRegularFile()) {
			wrappedPathList.add(new ComparablePathWrapper(file.toAbsolutePath(), file.getFileName().toString()));
		}
		return FileVisitResult.CONTINUE;
	}
	
	private List<Path> makeRawList () {
		List<Path> paths = new ArrayList<>();
		for (ComparablePathWrapper wp : wrappedPathList) {
			paths.add(wp.getPath());
		}
		return paths;
	}
	
	public List<Path> getPathList () {
		return pathList;
	}
}
