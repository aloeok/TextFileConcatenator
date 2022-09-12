package task.concatenator.provider;

import task.concatenator.provider.utils.PathSorter;

import java.nio.file.Path;
import java.util.List;

public class AlgorithmProvider {
	public List<Path> sortPaths (PathSorter.PathSortOption option, List<Path> rawFileList, Path rootDir) {
		PathSorter pathSorter = PathSorter.getPathSorter(option);
		pathSorter.build(rawFileList, rootDir);
		return pathSorter.getSortedPathList();
	}
}
