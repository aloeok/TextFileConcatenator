package task.concatenator.provider.utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilenameSorter {
	private List<PathCompFilename> pathCompFilenameList;
	private List<Path> sortedPathList;
	
	public FilenameSorter () {
		pathCompFilenameList = null;
		sortedPathList = null;
	}
	
	public void build (List<Path> rawFileList) {
		init(rawFileList);
		sort();
		makeSortedPathList();
	}
	
	private void init (List<Path> rawFileList) {
		pathCompFilenameList = new ArrayList<>();
		for (Path p : rawFileList) {
			pathCompFilenameList.add(new PathCompFilename(p));
		}
	}
	private void sort () {
		Collections.sort(pathCompFilenameList);
	}
	private void makeSortedPathList () {
		sortedPathList = new ArrayList<>();
		for (PathCompFilename pcf : pathCompFilenameList) {
			sortedPathList.add(pcf.getPath());
		}
	}
	
	public List<Path> getSortedPathList () {
		return sortedPathList;
	}
}
