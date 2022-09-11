package task.concatenator.test;

import task.concatenator.utils.ComparablePathWrapper;
import task.concatenator.utils.FileListProvider;
import task.concatenator.utils.RootDirProvider;
import task.concatenator.utils.SimpleUserInterface;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Tester {
	public static void main (String[] args) {
		//rdpTest();
		flpTest();
	}
	
	public static void flpTest () {
		SimpleUserInterface simpleUI = new SimpleUserInterface();
		RootDirProvider rdp = new RootDirProvider();
		FileListProvider flp = null;
		String pathStr;
		
		pathStr = simpleUI.prompt("path: ");
		Path path = rdp.getPath(pathStr);
		
		try {
			flp = new FileListProvider(path);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		List<ComparablePathWrapper> paths = flp.getPathList();
		Collections.sort(paths);
		
		for (ComparablePathWrapper p : paths) {
			simpleUI.message(p.getFileName());
		}
	}
	
	public static void rdpTest () {
		SimpleUserInterface simpleUI = new SimpleUserInterface();
		RootDirProvider rdp = new RootDirProvider();
		String pathStr;
		
		while (true) {
			pathStr = simpleUI.prompt("path: ");
			Path path = rdp.getPath(pathStr);
			if (path == null) {
				simpleUI.message("Path \"" + pathStr + "\" does not exist\n");
			} else {
				simpleUI.message("Path \"" + path + "\" exists!\nHere is an absolute path for it: " + path.toAbsolutePath() + "\n");
			}
		}
	}
}
