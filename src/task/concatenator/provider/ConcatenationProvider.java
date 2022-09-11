package task.concatenator.provider;

import com.sun.istack.internal.NotNull;
import task.concatenator.UI.SimpleUserInterface;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ConcatenationProvider {
	private Path concatFilePath;
	
	public ConcatenationProvider () {
		concatFilePath = null;
	}
	
	public void init (@NotNull Path rootDir, @NotNull String concatFileName) {
		concatFilePath = rootDir.resolve(FileSystems.getDefault().getPath(concatFileName));
	}
	
	public void concatenateFileListIntoFile (@NotNull List<Path> files) {
		try {
			FileChannel toChannel = FileChannel.open(concatFilePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			FileChannel fromChannel;
			for (Path file : files) {
				fromChannel = FileChannel.open(file, StandardOpenOption.READ);
				fromChannel.transferTo(0, fromChannel.size(), toChannel);
				fromChannel.close();
			}
			toChannel.close();
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException during concatenation");
		}
	}
}
