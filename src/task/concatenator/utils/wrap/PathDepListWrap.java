package task.concatenator.utils.wrap;

import task.concatenator.UI.SimpleUserInterface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathDepListWrap {
	private static final String DEPENDENCY_MARKER = "require";
	
	private final Path path;
	private final List<Path> dependencies;
	
	public PathDepListWrap (Path path, Path rootDir) {
		this.path = path;
		dependencies = new ArrayList<>();
		buildDependencies(rootDir);
	}
	
	/*
	Assumed that only lines with dependencies main contain the word "require"
	Assumed that lines with dependencies contain nothing but what is shown in the example ("require 'path'" and nothing more)
	 */
	private void buildDependencies (Path rootDir) {
		List<String> depLines = null;
		try {
			depLines = loadDepLines();
		} catch (IOException e) {
			SimpleUserInterface.handleException("IOException while reading a file to search dependencies");
		}
		
		for (String depLine : depLines) {
			dependencies.add(extractDependency(depLine, rootDir));
		}
	}
	private List<String> loadDepLines () throws IOException {
		FileInputStream fis = new FileInputStream(path.toFile());
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		Stream<String> fileLines = in.lines();
		return fileLines.filter(new Predicate<String>() {
			@Override
			public boolean test (String s) {
				return s.contains(DEPENDENCY_MARKER);
			}
		}).collect(Collectors.toList());
	}
	private Path extractDependency (String depLine, Path rootDir) {
		String dependencyStr = depLine.substring(depLine.indexOf("'") + 1, depLine.lastIndexOf("'"));
		String dependencyPathStr = FileSystems.getDefault().getPath(rootDir.toString() + FileSystems.getDefault().getSeparator() + dependencyStr).normalize().toString();
		return FileSystems.getDefault().getPath(dependencyPathStr);
	}
	
	public Path getPath () {
		return path;
	}
	public List<Path> getDependencies () {
		return dependencies;
	}
}
