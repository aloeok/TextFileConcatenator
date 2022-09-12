package task.concatenator.utils.algo;

import task.concatenator.utils.wrap.PathDepListWrap;

import java.nio.file.Path;
import java.util.*;

/**
 * Класс содержит функции для построения и поддержания структуры данных для реализации сортировки (списка файлов)
 * с учётом зависимостей и функции, реализующие сортировку с учётом зависимостей
 */
public class PDLWrapGraph {
	/**
	 * Список "обёрнутых" path-ов, которые требуется отсортировать, со списками зависимостей
	 *
	 * !! Порядок обёрток в списке не меняется для возможности создания списка смежности графа
	 * зависимостей файла по индексам их обёрток в списке
	 */
	private final List<PathDepListWrap> pathDepListWrapList;
	
	/**
	 * HashMap для эффективного нахождения индекса обёртки Path'а в pathDepListWrapList (в списке обёрток)
	 */
	private final HashMap<Path, Integer> pathIndexesMap;
	
	/**
	 * Список смежности графа зависимостей файлов (по индексам обёрток файлов в списке оюёрток)
	 */
	private int[][] wrapGraph;
	
	/**
	 * Конструктор
	 */
	public PDLWrapGraph (List<PathDepListWrap> pdlwList) {
		pathDepListWrapList = pdlwList;
		pathIndexesMap = new HashMap<>();
		wrapGraph = null;
	}
	
	/**
	 * Функция реализует логику алгоритма инициализации и заполнения структур данных:
	 * 1. Построить HashMap, ассоциирующую Path файла с индексом его обёртки в списке обёрток
	 * 2. Инициализировать размеры списка смежности графа зависимостей
	 * 3. Построить список смежности графа зависимостей
	 */
	public void build () {
		buildPathIndexesMap();
		initWrapGraph();
		fillWrapGraph();
	}
	
	/**
	 * Функция строит HashMap, ассоциирующую Path файла с индексом его обёртки в списке обёрток
	 */
	private void buildPathIndexesMap () {
		for (int i = 0; i < pathDepListWrapList.size(); ++ i) {
			pathIndexesMap.put(pathDepListWrapList.get(i).getPath(), i);
		}
	}
	
	/**
	 * Функция инициализирует размеры массива, содержащий список смежности графа зависимостей
	 */
	private void initWrapGraph () {
		wrapGraph = new int[pathDepListWrapList.size()][];
		for (int i = 0; i < wrapGraph.length; ++ i) {
			wrapGraph[i] = new int[pathDepListWrapList.get(i).getDependencies().size()];
		}
	}
	
	/**
	 * Функция строит список смежности графа зависимостей по индексам обёрток файлов в списке обёрток
	 */
	private void fillWrapGraph () {
		for (int i = 0; i < wrapGraph.length; ++ i) {
			for (int j = 0; j < wrapGraph[i].length; ++ j) {
				wrapGraph[i][j] = pathIndexesMap.get(pathDepListWrapList.get(i).getDependencies().get(j));
			}
		}
	}
	
	/**
	 * Функция получает на вход пустой список целых чисел
	 * Функция реализует логику алгоритма поиска цикла и сортировки списка обёрток:
	 * 1. Проверить наличие цикла
	 * 2. Если цикл есть - записать в переданный список индексы обёрток файлов в списке обёрток,
	 *    образующих цикл зависимостей
	 *    Если цикла нет - выполнить алгоритм топологической сортировки графа зависимостей и
	 *    записать порядок индексов обёрток файлов после сортировки в переданный список
	 */
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
	
	/**
	 * Функция проверяет наличие цикла в графе wrapGraph и записыает цикл в cl
	 */
	private void searchCycle (int[] cl, int[] p, int[] cp) {
		for (int i = 0; i < wrapGraph.length; ++ i)
			if (dfs(i, wrapGraph, cl, p, cp))
				break;
	}
	
	/**
	 * Функция возвращает список индексов, образующих цикл в графе wrapGraph
	 */
	private List<Integer> getCycle (int[] cp, int[] p) {
		List<Integer> cycle = new ArrayList<>();
		
		cycle.add(cp[0]);
		for (int v = cp[1]; v != cp[0]; v = p[v])
			cycle.add(v);
		cycle.add(cp[0]);
		Collections.reverse(cycle);
		
		return cycle;
	}
	
	/**
	 * Функция выполняет топологическую сортировку графа wrapGraph и возвращает отсортированный список вершин
	 */
	private List<Integer> topologicalSort (int[] cl) {
		List<Integer> order = new ArrayList<>();
		Arrays.fill(cl, 0);
		for (int i = 0; i < wrapGraph.length; ++ i)
			if (cl[i] == 0)
				dfs_top(i, wrapGraph, cl, order);
		return order;
	}
	
	/**
	 * Вспомогательная реализует обход в глубину одной вершины графа, вспомогательная функция для поиска цикла
	 */
	//!! РЕКУРСИВНАЯ ФУНКЦИЯ
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
	
	/**
	 * Функция реализует обход в глубину одной вершины графа, вспомогательная функция для топологической сортировки
	 */
	//!! РЕКУРСИВНАЯ ФУНКЦИЯ
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
