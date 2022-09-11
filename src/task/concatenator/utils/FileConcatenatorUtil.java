package task.concatenator.utils;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileConcatenatorUtil {
	private static final String DEFAULT_CONCAT_FILE_NAME = "concat_result.txt";
	
	private final Path concatFilePath;
	
	public FileConcatenatorUtil (Path rootDir) {
		this(rootDir, DEFAULT_CONCAT_FILE_NAME);
	}
	public FileConcatenatorUtil (Path rootDir, @NotNull String concatFileName) {
		concatFilePath = rootDir.resolve(FileSystems.getDefault().getPath(concatFileName));
	}
	
	public Path getConcatFilePath () {
		return concatFilePath;
	}
	
	public boolean concatenateFileList (List<Path> files) throws IOException {
		FileChannel toChannel = FileChannel.open(concatFilePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
		FileChannel fromChannel;
		for (int i = 0; i < files.size(); ++ i) {
			fromChannel = FileChannel.open(files.get(i), StandardOpenOption.READ);
			fromChannel.transferTo(0, fromChannel.size(), toChannel);
			fromChannel.close();
			System.out.format("[%10d/%10d] %s\n", (i+1), files.size(), files.get(i).toString());
		}
		toChannel.close();
		return true;
	}
}
