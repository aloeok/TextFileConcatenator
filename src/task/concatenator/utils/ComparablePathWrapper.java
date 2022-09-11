package task.concatenator.utils;

import java.nio.file.Path;

/**
 * DONE
 * A class that holds unchangeable Path and allows comparing Paths by file names
 */
public class ComparablePathWrapper implements Comparable<ComparablePathWrapper> {
	private final Path path;
	private final String fileName;
	
	public ComparablePathWrapper (Path path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}
	
	public Path getPath () {
		return path;
	}
	
	public String getFileName () {
		return fileName;
	}
	
	public int compareTo (ComparablePathWrapper o) {
		return fileName.compareTo(o.fileName);
	}
}
