package task.concatenator.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** DONE, TESTED */
public class SimpleUserInterface {
	private final BufferedReader in;
	
	public SimpleUserInterface () {
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/** TESTED */
	public void message (String messageStr) {
		System.out.println(messageStr);
	}
	
	/** TESTED */
	public String prompt (String promptStr) {
		message(promptStr);
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
