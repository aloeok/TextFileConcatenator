package task.concatenator.provider.utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilenamePathSorter extends PathSorter {
	private List<PathCompFilenameWrap> pathCompFilenameWrapList;
	
	public FilenamePathSorter() {
		pathCompFilenameWrapList = null;
	}
	
	public void build (List<Path> rawFileList, Path rootDir) {
		init(rawFileList);
		sort();
		makeSortedPathList();
	}
	
	private void init (List<Path> rawFileList) {
		pathCompFilenameWrapList = new ArrayList<>();
		for (Path p : rawFileList) {
			pathCompFilenameWrapList.add(new PathCompFilenameWrap(p));
		}
	}
	private void sort () {
		Collections.sort(pathCompFilenameWrapList);
	}
	private void makeSortedPathList () {
		sortedPathList = new ArrayList<>();
		for (PathCompFilenameWrap pcf : pathCompFilenameWrapList) {
			sortedPathList.add(pcf.getPath());
		}
	}
}
