package task.concatenator.provider.utils;

import java.nio.file.Path;

public class PathCompFilename implements Comparable<PathCompFilename> {
	private final Path path;
	private final String fileName;
	
	public PathCompFilename (Path path) {
		this.path = path;
		this.fileName = path.getFileName().toString();
	}
	
	public Path getPath () {
		return path;
	}
	
	public int compareTo (PathCompFilename o) {
		return fileName.compareTo(o.fileName);
	}
}
