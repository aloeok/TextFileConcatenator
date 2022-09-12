package task.concatenator.utils;

import task.concatenator.UI.SimpleUserInterface;

import java.nio.file.Path;
import java.util.List;

public abstract class PathSorter {
	public enum PathSortOption {LEX, DEP}
	public static PathSorter getPathSorter (PathSortOption option) {
		if (option == PathSortOption.LEX) {
			return new FilenamePathSorter();
		} else if (option == PathSortOption.DEP) {
			return new DepAwarePathSorter();
		} else {
			SimpleUserInterface.handleException("Incorrect PathSortOption");
			return null;
		}
	}
	protected List<Path> sortedPathList;
	protected PathSorter () {
		sortedPathList = null;
	}
	public abstract void build (List<Path> rawFileList, Path rootDir);
	public List<Path> getSortedPathList () {
		return sortedPathList;
	}
}
