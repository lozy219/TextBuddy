package textbuddy;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextBuddyTest  {

	TextBuddy testBuddy = new TextBuddy("file.txt");
	
	@Test
	public void testSort() {
		testOneCommand("clear", "All content deleted from file.txt", "clear");
		testOneCommand("add xanedu", "Added to file.txt: \"Learn Xanedu\"", "add Learn Xanedu");
		testOneCommand("add java", "Added to file.txt: \"Learn Java\"", "add Learn Java");
		testOneCommand("add js", "Added to file.txt: \"Learn Javascript\"", "add Learn Javascript");
		testOneCommand("add 1 hour", "Added to file.txt: \"1-hour practice\"", "add 1-hour practice");
		testOneCommand("display non-sorted", "1:Learn Xanedu2:Learn Java3:Learn Javascript4:1-hour practice", "display");
		testOneCommand("sort", "Sorting completed", "sort");
		testOneCommand("display sorted", "1:1-hour practice2:Learn Java3:Learn Javascript4:Learn Xanedu", "display");
		testOneCommand("clear", "All content deleted from file.txt", "clear");
		System.out.println("all sort tests are passed");
	}
	
	@Test
	public void testSearch() {
		testOneCommand("clear", "All content deleted from file.txt", "clear");
		testOneCommand("add xanedu", "Added to file.txt: \"Learn Xanedu\"", "add Learn Xanedu");
		testOneCommand("add java", "Added to file.txt: \"Learn Java\"", "add Learn Java");
		testOneCommand("add js", "Added to file.txt: \"Learn Javascript\"", "add Learn Javascript");
		testOneCommand("add 1 hour", "Added to file.txt: \"1-hour practice\"", "add 1-hour practice");
		testOneCommand("display", "1:Learn Xanedu2:Learn Java3:Learn Javascript4:1-hour practice", "display");
		testOneCommand("search learn", "1:Learn Xanedu2:Learn Java3:Learn Javascript", "search Learn");
		testOneCommand("search java", "2:Learn Java3:Learn Javascript", "search java");
		testOneCommand("search for non-existing task", "No result found", "search acciaccatura");
		testOneCommand("clear", "All content deleted from file.txt", "clear");
		System.out.println("all search tests are passed");
	}
	

	private void testOneCommand(String description, String expected, String command) {
		try {
			assertEquals(description, expected, testBuddy.processCommand(command)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
