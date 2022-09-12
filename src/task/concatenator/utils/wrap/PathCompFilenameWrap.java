package task.concatenator.utils.wrap;

import java.nio.file.Path;

public class PathCompFilenameWrap implements Comparable<PathCompFilenameWrap> {
	private final Path path;
	private final String fileName;
	
	public PathCompFilenameWrap(Path path) {
		this.path = path;
		this.fileName = path.getFileName().toString();
	}
	
	public Path getPath () {
		return path;
	}
	
	public int compareTo (PathCompFilenameWrap o) {
		return fileName.compareTo(o.fileName);
	}
}
