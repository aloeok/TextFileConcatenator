package task.concatenator;

import task.concatenator.UI.SimpleUserInterface;
import task.concatenator.provider.utils.PathSorter;

public class TextFileConcatenatorRunner {
	public static void main (String[] args) throws Exception {
		String rootDirStr = getRootDirStr(args);
		PathSorter.PathSortOption option = getSortOption(args);
		
		TextFileConcatenator textFileConcatenator = new TextFileConcatenator(rootDirStr, option);
		textFileConcatenator.work();
		
		SimpleUserInterface.message("Done.");
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
	private static PathSorter.PathSortOption getSortOption (String[] args) {
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
			return PathSorter.PathSortOption.LEX;
		} else if (optionStr.equals("2")) {
			return PathSorter.PathSortOption.DEP;
		} else {
			SimpleUserInterface.handleInvalidUsage();
			return null;
		}
	}
}
