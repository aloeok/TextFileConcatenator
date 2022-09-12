package task.concatenator.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** DONE, TESTED */
public class SimpleUserInterface {
	private static final BufferedReader in;
	
	static {
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static void message (String messageStr) {
		System.out.println(messageStr);
	}
	public static String prompt (String promptStr) {
		message(promptStr);
		try {
			return in.readLine();
		} catch (IOException e) {
			handleException("Your input somehow caused an IOException");
			return null;
		}
	}
	
	public static void handleException (String exceptionStr) {
		message("An exception occurred during execution.\nError text: " + exceptionStr);
		System.exit(0);
	}
	public static void handleInvalidUsage () {
		message("Invalid usage!\n" + getUsageStr());
		System.exit(0);
	}
	public static String getUsageStr () {
		return "Usage: " +
				"java TextFileConcatenatorRunner <path to directory> <path to future output file> <sort method>\n" +
				"For \"sort method\" type \"1\" for lexicographical sort or \"2\" for dependency-aware sort\n";
	}
}
