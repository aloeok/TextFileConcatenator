package task.concatenator;

import task.concatenator.UI.SimpleUserInterface;
import task.concatenator.utils.PathSorter;

/**
 * Класс содержит все необходимые функции для запуска программы и определения её работы.
 */
public class TextFileConcatenatorRunner {
	/**
	 * Функция запускает программу. Опцилнально принимает аргументы, задающие работу программы:
	 * путь к директории с целевыми файлами, путь к будущему склеенному файлу, тип сортировки
	 * файлов перед склеиванием (1 - лексикографическая, 2 - с учётом зависимостей).
	 * Если аргументы не указаны, они будут запрошены программой при её запуске.
	 */
	public static void main (String[] args) {
		checkArgNumber(args);
		
		String rootDirStr = getRootDirStr(args);
		String concatFilePathStr = getConcatFilePathStr(args);
		PathSorter.PathSortOption option = getSortOption(args);
		
		TextFileConcatenator textFileConcatenator = new TextFileConcatenator(rootDirStr, concatFilePathStr, option);
		textFileConcatenator.work();
		
		SimpleUserInterface.message("Done.");
	}
	
	/**
	 * Функция принимает массив переданных программе аргументов при её запуске и проверяет количество переданных
	 * аргументов.
	 * В случае неверного количества переданных аргументов, программа уведомляет об этом пользователя и
	 * завершает работу.
	 */
	private static void checkArgNumber (String[] args) {
		if (args.length != 0 && args.length != 3)
			SimpleUserInterface.handleInvalidUsage();
	}
	
	/**
	 * Функция принимает массив переданных программе аргументов при её запуске. Если он не пустой, возвращает
	 * его первый элемент (с индексом 0) - путь к директории с целевыми файлами. Если массив аргументов пустой -
	 * программа запрашивает путь к директории с целевыми файлами у пользователя.
	 */
	private static String getRootDirStr (String[] args) {
		if (args.length == 3) {
			return args[0];
		}
		return SimpleUserInterface.prompt("Enter your directory:");
	}
	
	/**
	 * Функция принимает массив переданных программе аргументов при её запуске. Если он не пустой, возвращает
	 * его второй элемент (с индексом 1) - путь к будущему склеенному файлу. Если массив аргументов пустой -
	 * программа запрашивает путь к будущему склеенному файлу у пользователя.
	 */
	private static String getConcatFilePathStr (String[] args) {
		if (args.length == 3) {
			return args[1];
		}
		return SimpleUserInterface.prompt("Enter full path for the future output file:");
	}
	
	/**
	 * Функция принимает массив переданных программе аргументов при её запуске. Если он не пустой, по его третьему
	 * элементу (с индексом 2) определяет enum, соответствующий типу сортировки. Если массив аргументов пустой -
	 * программа запрашивает тип сортировки файлов перед склеиванием у пользователя и также определяет enum.
	 * Если тип сортировки введён неверно, программа уведомляет об этом пользователя и завершает работу.
	 */
	private static PathSorter.PathSortOption getSortOption (String[] args) {
		String optionStr;
		
		if (args.length == 3) {
			optionStr = args[2];
		} else {
			optionStr = SimpleUserInterface.prompt("Enter sort option (\"1\" for lex. or \"2\" for dep.-aware)");
		}
		
		if (optionStr.equals("1")) {
			return PathSorter.PathSortOption.LEX;
		} else if (optionStr.equals("2")) {
			return PathSorter.PathSortOption.DEP;
		} else {
			SimpleUserInterface.handleInvalidUsage();
			return null;
		}
	}
}
