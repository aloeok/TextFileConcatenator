package task.concatenator;

import java.io.IOException;
import java.nio.file.Path;

import task.concatenator.utils.FileListProvider;
import task.concatenator.utils.RootDirProvider;
import task.concatenator.utils.SimpleUserInterface;

public class TextFileConcatenator {
	public enum FileSortOption {LEXICOGRAPHICAL_SORT, DEPENDENCY_AWARE_SORT}
	
	private final SimpleUserInterface simpleUI;
	private final String rootDirStr;
	FileSortOption sortOpt;
	
	public TextFileConcatenator (String rootDirArg, FileSortOption sortOpt) {
		simpleUI = new SimpleUserInterface();
		rootDirStr = getRootDirStr(rootDirArg);
		this.sortOpt = sortOpt;
	}
	
	private String getRootDirStr (String rootDirArg) {
		if (rootDirArg == null) {
			return simpleUI.prompt("Enter the path to your directory:");
		}
		return rootDirArg;
	}
	
	public void work () {
		RootDirProvider rootDirProvider = new RootDirProvider();
		Path rootDir = rootDirProvider.getPath(rootDirStr);				// EXCEPTION - MIGHT BE NULL
		
		try {															// BADLY HANDLED EXCEPTION
			FileListProvider fileListProvider = new FileListProvider(rootDir);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
}
