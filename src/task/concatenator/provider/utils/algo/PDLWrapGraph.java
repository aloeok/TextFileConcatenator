package task.concatenator.provider.utils.algo;

import task.concatenator.provider.utils.PathDepListWrap;

import java.nio.file.Path;
import java.util.*;

public class PDLWrapGraph {
	private final List<PathDepListWrap> pathDepListWrapList;
	private final HashMap<Path, Integer> pathIndexesMap;
	
	private int[][] wrapGraph;
	
	public PDLWrapGraph (List<PathDepListWrap> pdlwList) {
		pathDepListWrapList = pdlwList;
		pathIndexesMap = new HashMap<>();
		wrapGraph = null;
	}
	
	public void build () {
		buildPathIndexesMap();
		initWrapGraph();
		fillWrapGraph();
	}
	private void buildPathIndexesMap () {
		for (int i = 0; i < pathDepListWrapList.size(); ++ i) {
			pathIndexesMap.put(pathDepListWrapList.get(i).getPath(), i);
		}
	}
	private void initWrapGraph () {
		wrapGraph = new int[pathDepListWrapList.size()][];
		for (int i = 0; i < wrapGraph.length; ++ i) {
			wrapGraph[i] = new int[pathDepListWrapList.get(i).getDependencies().size()];
		}
	}
	private void fillWrapGraph () {
		for (int i = 0; i < wrapGraph.length; ++ i) {
			for (int j = 0; j < wrapGraph[i].length; ++ j) {
				wrapGraph[i][j] = pathIndexesMap.get(pathDepListWrapList.get(i).getDependencies().get(j));
			}
		}
	}
	
	public boolean findCycleOrDoTopologicalSort (List<Integer> indexes) {
		int[] cl; cl = new int[wrapGraph.length]; Arrays.fill(cl, 0);
		int[] p; p = new int[wrapGraph.length]; Arrays.fill(p, -1);
		int[] cp; cp = new int[2]; cp[0] = -1;					//cp[0] <=> cycle_start, cp[1] <=> cycle_end
		
		searchCycle(cl, p, cp);
		
		if (cp[0] != -1) {									//----------IF-CYCLE-FOUND-----------------
			List<Integer> cycle = getCycle(cp, p);
			for (Integer ind : cycle)
				indexes.add(ind);
			return true;
		} else {							//-----------IF-CYCLE-NOT-FOUND--------TOPOLOGICAL-SORT--------
			List<Integer> order = topologicalSort(cl);
			for (Integer ind : order)
				indexes.add(ind);
			return false;
		}
	}
	private void searchCycle (int[] cl, int[] p, int[] cp) {
		for (int i = 0; i < wrapGraph.length; ++ i)
			if (dfs(i, wrapGraph, cl, p, cp))
				break;
	}
	private List<Integer> getCycle (int[] cp, int[] p) {
		List<Integer> cycle = new ArrayList<>();
		
		cycle.add(cp[0]);
		for (int v = cp[1]; v != cp[0]; v = p[v])
			cycle.add(v);
		cycle.add(cp[0]);
		Collections.reverse(cycle);
		
		return cycle;
	}
	private List<Integer> topologicalSort (int[] cl) {
		List<Integer> order = new ArrayList<>();
		Arrays.fill(cl, 0);
		for (int i = 0; i < wrapGraph.length; ++ i)
			if (cl[i] == 0)
				dfs_top(i, wrapGraph, cl, order);
		return order;
	}
	private boolean dfs (int v, int[][] g, int[] cl, int[] p, int[] cp) {
		cl[v] = 1;
		for (int i = 0; i < g[v].length; ++ i) {
			int to = g[v][i];
			if (cl[to] == 0) {
				p[to] = v;
				if (dfs(to, g, cl, p, cp))
					return true;
			} else if (cl[to] == 1) {
				cp[0] = to;
				cp[1] = v;
				return true;
			}
		}
		cl[v] = 2;
		return false;
	}
	private void dfs_top (int v, int[][] g, int[] cl, List<Integer> order) {
		cl[v] = 1;
		for (int i = 0; i < g[v].length; ++ i) {
			int to = g[v][i];
			if (cl[to] == 0)
				dfs_top(to, g, cl, order);
		}
		order.add(v);
	}
}
