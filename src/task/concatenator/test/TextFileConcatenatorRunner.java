package task.concatenator.test;

import task.concatenator.TextFileConcatenator;

public class TextFileConcatenatorRunner {
	public static void main (String[] args) throws Exception {
		TextFileConcatenator textFileConcatenator;
		String rootDirArg = null;
		TextFileConcatenator.FileSortOption option = null;
		
		if (args.length == 0) {
			rootDirArg = null;
			option = TextFileConcatenator.FileSortOption.LEXICOGRAPHICAL_SORT;
		} else if (args.length == 1) {
			rootDirArg = args[0];
			option = TextFileConcatenator.FileSortOption.LEXICOGRAPHICAL_SORT;
		} else {
			System.out.println("Invalid number of arguments, see usage");
			System.exit(0);
		}
		
		textFileConcatenator = new TextFileConcatenator(rootDirArg, option);
		textFileConcatenator.work();
	}
}
