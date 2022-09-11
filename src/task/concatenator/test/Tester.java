package task.concatenator.test;

import task.concatenator.TextFileConcatenator;
import task.concatenator.utils.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Tester {
	public static void main (String[] args) {
		tfcTest();
		//fcuTest();
		//rdpTest();
		//flpTest();
	}
	
	public static void tfcTest () {
		TextFileConcatenator tfc = new TextFileConcatenator(null);
		tfc.work();
	}
	
	public static void fcuTest () {
		Path rootPath = FileSystems.getDefault().getPath("somesht/jjjd");
		FileConcatenatorUtil fcu = new FileConcatenatorUtil(rootPath, "anothersht");
		System.out.println(fcu.getConcatFilePath());
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
		
		for (Path p : flp.getPathList()) {
			simpleUI.message(p.toString());
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
