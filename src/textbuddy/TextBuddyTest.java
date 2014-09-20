package textbuddy;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest  {

	TextBuddy testBuddy = new TextBuddy("file.txt");
	
	@Test
	public void testExecuteCommand() {
		
		testOneCommand("clear", "all content deleted from file.txt", "clear");
		testOneCommand("display empty", "file.txt is empty", "display");
		
		testOneCommand("add", "added to file.txt: \"hi\"", "add hi");
		testOneCommand("delete", "deleted from file.txt: \"hi\"", "delete 1");
		testOneCommand("clear", "all content deleted from file.txt", "clear");
		testOneCommand("invalid command", "Invalid command type: sflkhih23fdsa", "sflkhih23fdsa");
		testOneCommand("delete invalid", "Invalid task number: 28", "delete 28");
		testOneCommand("search", "No result found", "search achusve");
		testOneCommand("search", "No searched keyword found", "search");
		testOneCommand("add", "added to file.txt: \"have a nice recess week\"", "add have a nice recess week");
		testOneCommand("display", "1:have a nice recess week", "display");
		testOneCommand("add", "added to file.txt: \"what\"", "add what");
		testOneCommand("display", "1:have a nice recess week2:what", "display");
		testOneCommand("clear", "all content deleted from file.txt", "clear");
		testOneCommand("sort", "tasks sorted", "sort");
		testOneCommand("add", "added to file.txt: \"abc\"", "add abc");
		testOneCommand("display", "1:have a nice recess week2:what3:abc", "display");
		testOneCommand("sort", "tasks sorted", "sort");
		testOneCommand("display", "1:abc2:have a nice recess week3:what", "display");
		testOneCommand("search", "1:have a nice recess week", "recess");
	}
	

	private void testOneCommand(String description, String expected, String command) {
		try {
			assertEquals(description, expected, testBuddy.processCommand(command)); 
		} catch (Exception e) {
			
		}
	}

	
}
