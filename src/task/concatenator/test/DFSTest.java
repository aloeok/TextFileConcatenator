package task.concatenator.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DFSTest {
	public static int[][] readGraph () throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int n = Integer.parseInt(in.readLine());
		int[][] g = new int[n][];
		
		for (int i = 0; i < g.length; ++ i) {
			st = new StringTokenizer(in.readLine());
			g[i] = new int[st.countTokens()];
			for (int j = 0; j < g[i].length; ++ j) {
				g[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		return g;
	}
	
	public static void main (String[] args) {
		int[][] g = null;
		List<Integer> indexes = new ArrayList<>();
		
		try {
			g = readGraph();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		boolean isCyclic = findCycleOrDoTopologicalSort(g, indexes);
		
		if (isCyclic) {
			System.out.println("Cyclic");
			for (int i = 0; i < indexes.size() - 1; ++ i) {
				System.out.print(indexes.get(i) + " -> ");
			}
			System.out.println(indexes.get(indexes.size() - 1));
		} else {
			System.out.println("Acyclic");
			for (int i = 0; i < indexes.size(); ++ i) {
				System.out.print(indexes.get(i) + " ");
			}
			System.out.println();
		}
	}
	
	public static boolean findCycleOrDoTopologicalSort (int[][] g, List<Integer> indexes) {
		int[] cl;
		int[] p;
		int[] cp;							//cp[0] <=> cycle_start, cp[1] <=> cycle_end
		
		cl = new int[g.length]; Arrays.fill(cl, 0);
		p = new int[g.length]; Arrays.fill(p, -1);
		cp = new int[2]; cp[0] = -1;
		
		//------------------------------------------------------------CYCLE-SEARCH-----------
		for (int i = 0; i < g.length; ++ i)
			if (dfs(i, g, cl, p, cp))
				break;
		
		if (cp[0] != -1) {//-------------------------------IF-CYCLE-FOUND-----------------
			List<Integer> cycle = new ArrayList<>();
			
			cycle.add(cp[0]);
			for (int v = cp[1]; v != cp[0]; v = p[v])
				cycle.add(v);
			cycle.add(cp[0]);
			Collections.reverse(cycle);
			
			for (Integer ind : cycle)
				indexes.add(ind);
			
			return true;
		} else {	//-------------------------IF-CYCLE-NOT-FOUND---------------------TOPOLOGICAL-SORT--------
			List<Integer> order = new ArrayList<>();
			Arrays.fill(cl, 0);
			for (int i = 0; i < g.length; ++ i)
				if (cl[i] == 0)
					dfs_top(i, g, cl, order);
			
			for (Integer ind : order)
				indexes.add(ind);
			
			return false;
		}
	}
	
	public static boolean dfs (int v, int[][] g, int[] cl, int[] p, int[] cp) {
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
	
	public static void dfs_top (int v, int[][] g, int[] cl, List<Integer> order) {
		cl[v] = 1;
		for (int i = 0; i < g[v].length; ++ i) {
			int to = g[v][i];
			if (cl[to] == 0)
				dfs_top(to, g, cl, order);
		}
		order.add(v);
	}
}
