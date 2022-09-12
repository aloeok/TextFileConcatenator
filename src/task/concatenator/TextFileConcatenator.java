package task.concatenator;

import java.nio.file.Path;
import java.util.List;

import task.concatenator.provider.DirProvider;
import task.concatenator.provider.FileListProvider;
import task.concatenator.provider.ConcatenationProvider;
import task.concatenator.provider.AlgorithmProvider;
import task.concatenator.provider.utils.PathSorter;

public class TextFileConcatenator {
	private static final String DEFAULT_CONCAT_FILE_NAME = "concat_result.txt";
	
	private final String rootDirStr;
	private final PathSorter.PathSortOption sortOpt;
	
	private final DirProvider dirProvider;
	private final FileListProvider fileListProvider;
	private final ConcatenationProvider concatenationProvider;
	private final AlgorithmProvider algoProvider;
	
	public TextFileConcatenator (String rootDirStr, PathSorter.PathSortOption sortOpt) {
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
		
		List<Path> fileList = algoProvider.sortPaths(sortOpt, rawFileList, rootDir);
		
		concatenationProvider.init(rootDir, DEFAULT_CONCAT_FILE_NAME);
		concatenationProvider.concatenateFileListIntoFile(fileList);
	}
}
