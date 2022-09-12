package task.concatenator.utils;

import task.concatenator.UI.SimpleUserInterface;
import task.concatenator.utils.algo.PDLWrapGraph;
import task.concatenator.utils.wrap.PathDepListWrap;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержит функции, реализующие логику сортировки (списка файлов) с учётом директив(зависимостей одного файла от других)
 */
/*
эффективность поиска зависимостей - !не измеренно, содержит операции с файлами!
сложность реализованного алгоритма сортировки - O(N + M), N - количество файлов, M - количество всех директив
	!алгоритм использует рекурсию!
 */
public class DepAwarePathSorter extends PathSorter {
	/**
	 * Список объектов-обёрток для Path'ов файлов, которые содержат списки Path'ов всех файлов, от которых зависит соответствующий файл
	 */
	private List<PathDepListWrap> pathDepListWrapList;
	
	/**
	 * Структура данных на которой реализована эффективная сортировка с учётом зависимостей
	 */
	private PDLWrapGraph wrapGraph;
	
	/**
	 * Конструктор
	 */
	public DepAwarePathSorter() {
		pathDepListWrapList = null;
		wrapGraph = null;
	}
	
	/**
	 * Функция реализует основную логику сортировки с учётом зависимостей
	 */
	public void build (List<Path> rawFileList, Path rootDir) {
		initDepListWrapList(rawFileList, rootDir);
		buildWrapGraph();
		trySort();
	}
	
	/**
	 * Функция создаёт для каждого файла объект-обёртку, который хранит Path'ы всех его зависимостей
	 */
	private void initDepListWrapList (List<Path> rawFileList, Path rootDir) {
		pathDepListWrapList = new ArrayList<>();
		for (Path p : rawFileList) {
			pathDepListWrapList.add(new PathDepListWrap(p, rootDir));
		}
	}
	
	/**
	 * Функция создаёт структуру данных, которая поддерживает эффективную сортировку с учётом зависимостей
	 */
	private void buildWrapGraph () {
		wrapGraph = new PDLWrapGraph(pathDepListWrapList);
		wrapGraph.build();
	}
	
	/**
	 * Функция находит цикл зависимостей и выводит его, завершая программу;
	 * Или выполняет сортировку с учётом зависимостей
	 */
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
	
	/**
	 * Функция принимает список индексов файлов из pathDepListWrapList и возвращает строку, демонстрирующую
	 * циклическую зависимость данных файлов друг от друга
	 */
	private String getCycleStr (List<Integer> indexes) {
		String cycleStr = "";
		for (int i = 0; i < indexes.size() - 1; ++ i) {
			cycleStr += pathDepListWrapList.get(indexes.get(i)).getPath().toString();
			cycleStr += " -> \n";
		}
		cycleStr += pathDepListWrapList.get(indexes.get(indexes.size() - 1)).getPath().toString();
		return cycleStr;
	}
}
