package task.concatenator.utils;

import task.concatenator.UI.SimpleUserInterface;
import task.concatenator.utils.algo.PDLWrapGraph;
import task.concatenator.utils.wrap.PathDepListWrap;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DepAwarePathSorter extends PathSorter {
	private List<PathDepListWrap> pathDepListWrapList;
	private PDLWrapGraph wrapGraph;
	
	public DepAwarePathSorter() {
		pathDepListWrapList = null;
		wrapGraph = null;
	}
	
	public void build (List<Path> rawFileList, Path rootDir) {
		initDepListWrapList(rawFileList, rootDir);
		buildWrapGraph();
		trySort();
	}
	
	private void initDepListWrapList (List<Path> rawFileList, Path rootDir) {
		pathDepListWrapList = new ArrayList<>();
		for (Path p : rawFileList) {
			pathDepListWrapList.add(new PathDepListWrap(p, rootDir));
		}
	}
	private void buildWrapGraph () {
		wrapGraph = new PDLWrapGraph(pathDepListWrapList);
		wrapGraph.build();
	}
	private void trySort () {
		List<Integer> indexes = new ArrayList<>();
		boolean isCyclic = wrapGraph.findCycleOrDoTopologicalSort(indexes);
		
		if (isCyclic) {
			String cycleStr = getCycleStr(indexes);
			SimpleUserInterface.handleException("The concatenation cannot be done because there is a requirement loop. Here it is:\n" +
					cycleStr + "\n");
			return;
		}
		
		sortedPathList = new ArrayList<>();
		for (Integer ind : indexes) {
			sortedPathList.add(pathDepListWrapList.get(ind).getPath());
		}
	}
	
	private String getCycleStr (List<Integer> indexes) {
		String cycleStr = "";
		for (int i = 0; i < indexes.size() - 1; ++ i) {
			cycleStr += pathDepListWrapList.get(indexes.get(i)).getPath().toString();
			cycleStr += " -> \n";
		}
		cycleStr += pathDepListWrapList.get(indexes.get(indexes.size() - 1)).getPath().toString();
		return cycleStr;
	}
	
	public List<Path> getSortedPathList () {
		return sortedPathList;
	}
}
