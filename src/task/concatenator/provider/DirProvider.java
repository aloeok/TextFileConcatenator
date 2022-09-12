package task.concatenator.provider;

import task.concatenator.UI.SimpleUserInterface;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;

/** DONE, TESTED */
public class DirProvider {
	/**
	 * TESTED
	 * Makes the Path object for a specified existing path in default file system
	 *
	 * @param dirPathStr - a string that represents the path
	 * @return Path object for the specified existing path or null if such path does not exist
	 */
	public Path getDirPath (String dirPathStr) {
		Path rootDirPath = FileSystems.getDefault().getPath(dirPathStr);
		if (Files.exists(rootDirPath)) {
			return rootDirPath.toAbsolutePath();
		} else {
			SimpleUserInterface.handleException("Directory not found: " + rootDirPath);
			return null;
		}
	}
}
