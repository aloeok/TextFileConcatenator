package task.concatenator.test;

import task.concatenator.TextFileConcatenator;

public class TextFileConcatenatorRunner {
	public static void main (String[] args) throws Exception {
		TextFileConcatenator textFileConcatenator;
		String rootDirArg;
		TextFileConcatenator.FileSortOption option;
		
		if (args.length == 0) {
			rootDirArg = null;
			option = TextFileConcatenator.FileSortOption.LEXICOGRAPHICAL_SORT;
		} else if (args.length == 1) {
			rootDirArg = args[0];
			option = TextFileConcatenator.FileSortOption.LEXICOGRAPHICAL_SORT;
		} else {
			throw new Exception("Invalid number of arguments");
		}
		
		textFileConcatenator = new TextFileConcatenator(rootDirArg, option);
		textFileConcatenator.work();
	}
}
