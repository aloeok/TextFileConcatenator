package task.concatenator;

import task.concatenator.UI.SimpleUserInterface;

public class TextFileConcatenatorRunner {
	public static void main (String[] args) throws Exception {
		String rootDirStr = getRootDirStr(args);
		TextFileConcatenator.FileSortOption option = getSortOption(args);
		
		TextFileConcatenator textFileConcatenator = new TextFileConcatenator(rootDirStr, option);
		textFileConcatenator.work();
	}
	
	private static String getRootDirStr (String[] args) {
		if (args.length == 0) {
			return SimpleUserInterface.prompt("Enter your directory:");
		} else if (args.length == 2) {
			return args[0];
		} else {
			SimpleUserInterface.handleInvalidUsage();
		}
		return null;
	}
	private static TextFileConcatenator.FileSortOption getSortOption (String[] args) {
		String optionStr = null;
		
		if (args.length == 0) {
			optionStr = SimpleUserInterface.prompt("Enter sort option (\"1\" for lex. or \"2\" for dep.-aware)");
		} else if (args.length == 2) {
			optionStr = args[1];
		} else {
			SimpleUserInterface.handleInvalidUsage();
			return null;
		}
		
		if (optionStr.equals("1")) {
			return TextFileConcatenator.FileSortOption.LEX;
		} else if (optionStr.equals("2")) {
			return TextFileConcatenator.FileSortOption.DEP;
		} else {
			SimpleUserInterface.handleInvalidUsage();
			return null;
		}
	}
}
