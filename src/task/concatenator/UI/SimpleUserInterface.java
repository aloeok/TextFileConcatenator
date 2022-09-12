package task.concatenator.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс содержит все функции для необходимого взаимодействия с пользователем
 */
public class SimpleUserInterface {
	/**
	 * BufferedReader для считывания данных, введённых пользователем
	 */
	private static final BufferedReader in;
	
	/**
	 * Инициализатор статической переменной in класса BufferedReader
	 */
	static {
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Функция получает на вход строку, которую нужно вывести на экран и выводит её на экран
	 */
	public static void message (String messageStr) {
		System.out.println(messageStr);
	}
	
	/**
	 * Функция получает на вход строку, запрашивающую у пользователя ввод строки, и
	 * считывает строку, введённую пользователем
	 * Функция возвращает считанную строку
	 */
	public static String prompt (String promptStr) {
		message(promptStr);
		try {
			return in.readLine();
		} catch (IOException e) {
			handleException("Your input somehow caused an IOException");
			return null;
		}
	}
	
	/**
	 * Функция получает на вход краткое сообщение об ошибке, уведомляет пользователя о том, что во время выполнения
	 * программы возникла ощибка, выводит полученное сообщение и завершает работу программы
	 */
	public static void handleException (String exceptionStr) {
		message("An exception occurred during execution.\nError text: " + exceptionStr);
		System.exit(0);
	}
	
	/**
	 * Функция уведомляет пользователя о неправильном вводе аргументов при запуске программы, выводит краткую
	 * инструкцию по использованию программы и завершает работу программы
	 */
	public static void handleInvalidUsage () {
		message("Invalid usage!\n" + getUsageStr());
		System.exit(0);
	}
	
	/**
	 * Функция возвращает краткую инструкцию по использованию программы в виде переменной String
	 */
	public static String getUsageStr () {
		return "Usage: " +
				"java TextFileConcatenatorRunner <path to directory> <path to future output file> <sort method>\n" +
				"For \"sort method\" type \"1\" for lexicographical sort or \"2\" for dependency-aware sort\n";
	}
}
