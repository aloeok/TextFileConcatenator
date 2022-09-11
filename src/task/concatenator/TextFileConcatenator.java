package task.concatenator;

import java.nio.file.Path;
import java.util.List;

import task.concatenator.UI.SimpleUserInterface;
import task.concatenator.provider.DirProvider;
import task.concatenator.provider.FileListProvider;
import task.concatenator.provider.ConcatenationProvider;
import task.concatenator.provider.AlgorithmProvider;

public class TextFileConcatenator {
	private static final String DEFAULT_CONCAT_FILE_NAME = "concat_result.txt";
	public enum FileSortOption {LEX, DEP}
	
	private final String rootDirStr;
	private final FileSortOption sortOpt;
	
	private final DirProvider dirProvider;
	private final FileListProvider fileListProvider;
	private final ConcatenationProvider concatenationProvider;
	private final AlgorithmProvider algoProvider;
	
	public TextFileConcatenator (String rootDirStr, FileSortOption sortOpt) {
		this.rootDirStr = rootDirStr;
		this.sortOpt = sortOpt;
		
		dirProvider = new DirProvider();
		fileListProvider = new FileListProvider();
		concatenationProvider = new ConcatenationProvider();
		algoProvider = new AlgorithmProvider();
	}
	
	public void work () {
		Path rootDir = dirProvider.getDirPath(rootDirStr);
		
		fileListProvider.loadFileList(rootDir);
		List<Path> rawFileList = fileListProvider.getPathList();
		
		List<Path> fileList = null;
		if (sortOpt == FileSortOption.LEX) {
			fileList = algoProvider.sortLexFilenames(rawFileList);
		} else if (sortOpt == FileSortOption.DEP) {
			fileList = algoProvider.sortDepAware(rawFileList);
		} else {
			SimpleUserInterface.handleException("Incorrect FileSortOption");
		}
		
		concatenationProvider.init(rootDir, DEFAULT_CONCAT_FILE_NAME);
		concatenationProvider.concatenateFileListIntoFile(fileList);
		
		SimpleUserInterface.message("Done.");
	}
}
