package task.concatenator.utils.wrap;

import task.concatenator.UI.SimpleUserInterface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс-"обёртка" для объекта класса Path, поддерживающий список Path'ов, указывающих на файлы,
 * от которых зависит обёрнутый файл
 * Класс содержит функции, которые строят список зависимостей для заданного файла
 */
public class PathDepListWrap {
	/**
	 * Строка, предшествующая пути к зависимому файлу в исходном файле
	 */
	private static final String DEPENDENCY_MARKER = "require";
	
	/**
	 * "Обёрнутый" объект типа Path
	 */
	private final Path path;
	
	/**
	 * Список зависимостей файла, на который указывает путь path
	 */
	private final List<Path> dependencies;
	
	/**
	 * Конструктор, инициализирующий поля класса и вызывающий функцию, которая строит список зависимостей
	 * для "обёрнутого" Path'а
	 */
	public PathDepListWrap (Path path, Path rootDir) {
		this.path = path;
		dependencies = new ArrayList<>();
		buildDependencies(rootDir);
	}
	
	/*
	Assumed that only lines with dependencies main contain the word "require"
	Assumed that lines with dependencies contain nothing but what is shown in the example ("require 'path'" and nothing more)
	Иначе - ошибка!!
	 */
	/**
	 * Функция реализует логику алгоритма построения списка зависимостей:
	 * 1. Найти в файле и сохранить в списке все строки, содержащие директивы(зависимости)
	 * 2. Извлечь из каждой строки Path файла-зависимости и поместить в список зависимостей
	 */
	private void buildDependencies (Path rootDir) {
		List<String> depLines = null;
		try {
			depLines = loadDepLines();
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException while reading a file to search dependencies");
		}
		
		for (String depLine : depLines) {
			dependencies.add(extractDependency(depLine, rootDir));
		}
	}
	
	/**
	 * Функция возвращает список всех строк файла, на который указывает path, содержащих директивы
	 */
	private List<String> loadDepLines () throws IOException {
		FileInputStream fis = new FileInputStream(path.toFile());
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		Stream<String> fileLines = in.lines();
		return fileLines.filter(new Predicate<String>() {
			@Override
			public boolean test (String s) {
				return s.contains(DEPENDENCY_MARKER);
			}
		}).collect(Collectors.toList());
	}
	
	/**
	 * Функция получает строку, содержащую директиву (в виде DEPENDENCY_MARKER 'path_to_required_file') и
	 * папку со всеми целевыми файлами
	 * Функция возвращает объект Path, указывающий на зависимый файл из директивы
	 */
	private Path extractDependency (String depLine, Path rootDir) {
		String dependencyStr = depLine.substring(depLine.indexOf("'") + 1, depLine.lastIndexOf("'"));
		String dependencyPathStr = FileSystems.getDefault().getPath(rootDir.toString() + FileSystems.getDefault().getSeparator() + dependencyStr).normalize().toString();
		return FileSystems.getDefault().getPath(dependencyPathStr);
	}
	
	/**
	 * Функция возвращает обёрнутый path
	 */
	public Path getPath () {
		return path;
	}
	
	/**
	 * Функция возвращает список зависимостей для обёрнутого path
	 */
	public List<Path> getDependencies () {
		return dependencies;
	}
}
