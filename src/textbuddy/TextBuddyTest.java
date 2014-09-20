package textbuddy;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest  {

	TextBuddy testBuddy = new TextBuddy("file.txt");
	
	@Test
	public void testExecuteCommand() {
		
		testOneCommand("display empty", "file.txt is empty", "display");
		
		testOneCommand("add", "added to file.txt: \"hi\"", "add hi");
		testOneCommand("delete", "deleted from file.txt: \"hi\"", "delete 1");
		testOneCommand("clear", "all content deleted from file.txt", "clear");

		testOneCommand("delete invalid", "Invalid task number: 28", "delete 28");
	
	}
	

	private void testOneCommand(String description, String expected, String command) {
		try {
			assertEquals(description, expected, testBuddy.processCommand(command)); 
		} catch (Exception e) {
			
		}
	}

	
}
