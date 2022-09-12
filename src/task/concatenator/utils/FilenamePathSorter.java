package task.concatenator.utils;

import task.concatenator.utils.wrap.PathCompFilenameWrap;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс содержит функции для реализации сортировки (списка файлов) по именам файлов
 */
public class FilenamePathSorter extends PathSorter {
	/**
	 * Список объектов-"обёрток", поддерживающих сравнение объектов Path по имени файла
	 */
	private List<PathCompFilenameWrap> pathCompFilenameWrapList;
	
	/**
	 * Конструктор
	 */
	public FilenamePathSorter() {
		pathCompFilenameWrapList = null;
	}
	
	/**
	 * Функция выполняет алгоритм сортировки и "строит" отсортированный список файлов в переменной pathCompFilenameWrapList
	 * Логическая реализация сортировки по именам файлов
	 */
	public void build (List<Path> rawFileList, Path rootDir) {
		init(rawFileList);
		sort();
		makeSortedPathList();
	}
	
	/**
	 * Функция инициализирует и заполняет список объектов-"обёрток", поддерживающих сравнение объектов Path по имени файла
	 */
	private void init (List<Path> rawFileList) {
		pathCompFilenameWrapList = new ArrayList<>();
		for (Path p : rawFileList) {
			pathCompFilenameWrapList.add(new PathCompFilenameWrap(p));
		}
	}
	
	/**
	 * Функция выполняет сортировку списка объектов-"обёрток" по именам соответствующих им Path'ов файлов
	 *
	 * !ВАЖНО: сортировка выполняется по номерам символов имени файла (например, "Z.txt" лексикографически меньше, чем "a.txt"
	 * из-за того, что символы заглавных букв имеют меньшие номера, чем символы строчных букв)
	 */
	private void sort () {
		Collections.sort(pathCompFilenameWrapList);
	}
	
	/**
	 * Функция заполняет список отсортированных файлов в соответствии с отсортированным списком обёрток
	 */
	private void makeSortedPathList () {
		sortedPathList = new ArrayList<>();
		for (PathCompFilenameWrap pcf : pathCompFilenameWrapList) {
			sortedPathList.add(pcf.getPath());
		}
	}
}
