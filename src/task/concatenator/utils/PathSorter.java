package task.concatenator.utils;

import task.concatenator.UI.SimpleUserInterface;

import java.nio.file.Path;
import java.util.List;
/**
 * Класс, который содержит общие функции для выполнения сортировки (списка файлов)
 * Данная реализация поддерживает два типа сортировки, реализация которых требуется по условию задания
 */
public abstract class PathSorter {
	/**
	 * Enum, определяющий тип сортировки
	 * PathSortOption.LEX - сортировка по лексикографическому порядку имён файлов из списка
	 * PathSortOption.DEP - сортировка с учётом директив(зависимостей), указанных в файле
	 */
	public enum PathSortOption {LEX, DEP}
	
	/**
	 * Функция получает на вход enum, определяющий тип сортировки
	 * Функция возвращает instance класса PathSorter, реализующий указанный тип сортировки
	 */
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
	
	/**
	 * Список отсортированных файлов
	 */
	protected List<Path> sortedPathList;
	
	/**
	 * Конструктор
	 */
	protected PathSorter () {
		sortedPathList = null;
	}
	
	/**
	 * Функция, реализующая сортировку списка файлов, имплементируется в классах-потомках данного класса,
	 * в зависимости от реализуемого типа сортировки конкретным классом-потомком
	 */
	public abstract void build (List<Path> rawFileList, Path rootDir);
	
	/**
	 * Функция возвращает отсортированный список файлов
	 */
	public List<Path> getSortedPathList () {
		return sortedPathList;
	}
}
