package task.concatenator.provider;

import com.sun.istack.internal.NotNull;
import task.concatenator.provider.utils.FilenameSorter;

import java.nio.file.Path;
import java.util.List;

public class AlgorithmProvider {
	private final FilenameSorter filenameSorter;
	
	public AlgorithmProvider () {
		filenameSorter = new FilenameSorter();
	}
	
	public List<Path> sortLexFilenames (@NotNull List<Path> rawFileList) {
		filenameSorter.build(rawFileList);
		return filenameSorter.getSortedPathList();
	}
	
	public List<Path> sortDepAware (@NotNull List<Path> rawFileList) {
		return null;
	}
}
