package task.concatenator;

import java.nio.file.Path;
import task.concatenator.utils.RootDirProvider;
import task.concatenator.utils.SimpleUserInterface;

public class TextFileConcatenator {
	public enum FileSortOption {LEXICOGRAPHICAL_SORT, DEPENDENCY_AWARE_SORT}
	
	private final SimpleUserInterface simpleUI;
	private final String rootDirStr;
	
	private final RootDirProvider rootDirProvider;
	
	public TextFileConcatenator (String rootDirArg, FileSortOption sortOpt) {
		simpleUI = new SimpleUserInterface();
		rootDirProvider = new RootDirProvider();
		rootDirStr = getRootDirStr(rootDirArg);
	}
	
	private String getRootDirStr (String rootDirArg) {
		if (rootDirArg == null) {
			return simpleUI.prompt("Enter the path to your directory:");
		}
		return rootDirArg;
	}
	
	public void work () {
		Path rootDir = rootDirProvider.getPath(rootDirStr);
		
	}
}
