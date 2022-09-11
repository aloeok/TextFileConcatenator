package task.concatenator.utils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;

/** DONE, TESTED */
public class RootDirProvider {
	/**
	 * TESTED
	 * Makes the Path object for a specified existing path in default file system
	 *
	 * @param pathStr - a string that represents the path
	 * @return Path object for the specified existing path or null if such path does not exist
	 */
	public Path getPath (String pathStr) {
		Path rootDirPath = FileSystems.getDefault().getPath(pathStr);
		if (Files.exists(rootDirPath)) {
			return rootDirPath.toAbsolutePath();
		}
		return null;
	}
}
