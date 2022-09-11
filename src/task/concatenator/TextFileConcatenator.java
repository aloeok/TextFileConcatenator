package task.concatenator;

import java.io.IOException;
import java.nio.file.Path;

import task.concatenator.utils.FileConcatenatorUtil;
import task.concatenator.utils.FileListProvider;
import task.concatenator.utils.RootDirProvider;
import task.concatenator.utils.SimpleUserInterface;

public class TextFileConcatenator {
	public enum FileSortOption {LEXICOGRAPHICAL_SORT, DEPENDENCY_AWARE_SORT}
	
	private final SimpleUserInterface simpleUI;
	private final String rootDirStr;
	private final FileSortOption sortOpt;
	
	public TextFileConcatenator (String rootDirArg) {
		this(rootDirArg, FileSortOption.LEXICOGRAPHICAL_SORT);
	}
	
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
		
		FileListProvider fileListProvider = null;
		try {															// BADLY HANDLED EXCEPTION
			fileListProvider = new FileListProvider(rootDir);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		FileConcatenatorUtil fileConcatenatorUtil = new FileConcatenatorUtil(rootDir);
		
		try {
			fileConcatenatorUtil.concatenateFileList(fileListProvider.getPathList());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
