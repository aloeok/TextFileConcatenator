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
	private List<ComparablePathWrapper> pathList;
	
	/** TESTED */
	public FileListProvider (Path rootDir) throws IOException {
		pathList = new ArrayList<>();
		Files.walkFileTree(rootDir, this);
		Collections.sort(pathList);
	}
	
	@Override
	public FileVisitResult visitFile (Path file, BasicFileAttributes attr) {
		if (attr.isRegularFile()) {
			pathList.add(new ComparablePathWrapper(file.toAbsolutePath(), file.getFileName().toString()));
		}
		return FileVisitResult.CONTINUE;
	}
	
	public List<ComparablePathWrapper> getPathList () {
		return pathList;
	}
}
