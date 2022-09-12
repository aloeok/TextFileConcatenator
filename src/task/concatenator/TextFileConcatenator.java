package task.concatenator;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import task.concatenator.UI.SimpleUserInterface;
import task.concatenator.utils.PathSorter;

/**
 * Класс содержит все функции, реализующие логику решения задачи, заданной условием задания и тремя параметрами
 */
public class TextFileConcatenator {
	/**
	 * Строковое представление пути к директории с целевыми файлами
	 */
	private final String rootDirStr;
	
	/**
	 * Строковое представление пути к будущему склеенному файлу
	 */
	private final String concatFilePathStr;
	
	/**
	 * Enum, определяющий тип сортировки файлов перед склеиванием
	 */
	private final PathSorter.PathSortOption sortOpt;
	
	/**
	 * Инициализирует объект класса с заданными параметрами задачи
	 */
	public TextFileConcatenator (String rootDirStr, String concatFilePathStr, PathSorter.PathSortOption sortOpt) {
		this.rootDirStr = rootDirStr;
		this.concatFilePathStr = concatFilePathStr;
		this.sortOpt = sortOpt;
	}
	
	/**
	 * Основной алгоритм выполнения задачи:
	 * 1. Найти директорию с целевыми файлами
	 * 2. Получить список всех целевых файлов
	 * 3. Отсортировать файлы указанным способом
	 * 4. Склеить все файлы в один файл с заданным путём
	 */
	public void work () {
		Path rootDir = getDirPath(rootDirStr);
		List<Path> rawFileList = getFileList(rootDir);
		List<Path> fileList = sortPaths(sortOpt, rawFileList, rootDir);
		concatenateFileListIntoFile(concatFilePathStr, fileList);
	}
	
	/**
	 * Функция получает на вход строковое представление директории с целевыми файлами
	 * Функция возвращает объект класса java.nio.Path, представляющий существующую директорию с целевыми файлами
	 */
	private Path getDirPath (String dirPathStr) {
		Path rootDirPath = FileSystems.getDefault().getPath(dirPathStr);
		if (Files.exists(rootDirPath)) {
			return rootDirPath.toAbsolutePath();
		} else {
			SimpleUserInterface.handleException("Directory not found: " + rootDirPath);
			return null;
		}
	}
	
	/**
	 * Функция получает на вход директорию с целевыми файлами
	 * Функция возвращает список объектов класса java.nio.Path, представляющий список всех целевых файлов
	 */
	private List<Path> getFileList (Path rootDir) {
		List<Path> fileList = new ArrayList<>();
		try {
			Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile (Path file, BasicFileAttributes attr) {
					if (attr.isRegularFile()) {
						fileList.add(file.toAbsolutePath());
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException while walking a file tree");
		}
		return fileList;
	}
	
	/**
	 * Функция получает на вход enum, определяющий тип сортировки, список целевых файлов и директорию, в которой они лежат
	 * Функция возвращает список объектов класса java.nio.Path, представляющий отсортированный список всех целевых файлов
	 * в соответствии с заданным способом сортировки
	 */
	private List<Path> sortPaths (PathSorter.PathSortOption option, List<Path> rawFileList, Path rootDir) {
		PathSorter pathSorter = PathSorter.getPathSorter(option);
		pathSorter.build(rawFileList, rootDir);
		return pathSorter.getSortedPathList();
	}
	
	/**
	 * Функция получает на вход несуществующий путь к файлу и список директорий файлов
	 * Функция ничего не возвращает; по указанному пути функция создаёт новый файл, который является результатом
	 * склеивания всех файлов из списка в том же порядке, в котором они представлены в списке
	 * Если файл по пути concatFilePathStr уже существует, программа завершается с ошибкой!
	 */
	private void concatenateFileListIntoFile (String concatFilePathStr, List<Path> files) {
		Path concatFilePath = FileSystems.getDefault().getPath(concatFilePathStr);
		try {
			FileChannel toChannel = FileChannel.open(concatFilePath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			FileChannel fromChannel;
			for (Path file : files) {
				fromChannel = FileChannel.open(file, StandardOpenOption.READ);
				fromChannel.transferTo(0, fromChannel.size(), toChannel);
				fromChannel.close();
			}
			toChannel.close();
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException during concatenation");
		}
	}
}
