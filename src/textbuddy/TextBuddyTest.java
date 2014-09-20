package textbuddy;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest  {

	TextBuddy testBuddy = new TextBuddy("file.txt");
	
	@Test
	public void testSort() {
		
		testOneCommand("clear", "all content deleted from file.txt", "clear");
		testOneCommand("add", "added to file.txt: \"Learn Xanedu\"", "add Learn Xanedu");
		testOneCommand("add", "added to file.txt: \"Learn Java\"", "add Learn Java");
		testOneCommand("add", "added to file.txt: \"Learn Javascript\"", "add Learn Javascript");
		testOneCommand("add", "added to file.txt: \"1-hour practice\"", "add 1-hour practice");
		testOneCommand("display", "1:Learn Xanedu2:Learn Java3:Learn Javascript4:1-hour practice", "display");
		testOneCommand("sort", "tasks sorted", "sort");
		testOneCommand("display", "1:Learn Java2:Learn Javascript3:Learn Xanedu4:1-hour practice", "display");
		testOneCommand("clear", "all content deleted from file.txt", "clear");
		
	}
	
	@Test
	public void testSearch() {
		
		testOneCommand("clear", "all content deleted from file.txt", "clear");
		testOneCommand("add", "added to file.txt: \"Learn Xanedu\"", "add Learn Xanedu");
		testOneCommand("add", "added to file.txt: \"Learn Java\"", "add Learn Java");
		testOneCommand("add", "added to file.txt: \"Learn Javascript\"", "add Learn Javascript");
		testOneCommand("add", "added to file.txt: \"1-hour practice\"", "add 1-hour practice");
		testOneCommand("display", "1:Learn Xanedu2:Learn Java3:Learn Javascript4:1-hour practice", "display");
		testOneCommand("sort", "1:Learn Xanedu2:Learn Java3:Learn Javascript", "search Learn");
		testOneCommand("sort", "1:Learn Java2:Learn Javascript", "search Java");
		testOneCommand("clear", "all content deleted from file.txt", "clear");
		
	}
	

	private void testOneCommand(String description, String expected, String command) {
		try {
			assertEquals(description, expected, testBuddy.processCommand(command)); 
		} catch (Exception e) {
			
		}
	}

	
}
