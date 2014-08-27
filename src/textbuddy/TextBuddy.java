package textbuddy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class TextBuddy {
	
	private File TextBuddyFile;
	private ArrayList<String> todoList;
	private static Scanner scanner = new Scanner(System.in);
	
	
	/**
	 * Constructor for TextBuddy
	 * @param filePath
	 * @throws IOException 
	 */
	public TextBuddy(String filePath) throws IOException {
		this.TextBuddyFile = new File(filePath);
		if (!this.TextBuddyFile.exists()) {
			throw new IOException("invalid file path");
		} else {
			try {
				this.readTaskFromFile();
			} catch (IOException e) {
				
			}
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	private void readTaskFromFile() throws IOException {
		String line;
		FileReader in = new FileReader(this.TextBuddyFile);
		BufferedReader br = new BufferedReader(in);
		while ((line = br.readLine()) != null) {
			this.todoList.add(line);
		}
		br.close();
	}
	
	public static void main(String[] args) {
		
		try {
			TextBuddy textBuddy = new TextBuddy(args[1]);
			textBuddy.showWelcome();
			while (true) {
				textBuddy.showPrompt();
				textBuddy.readAndProcessCommand();
			}
		} catch (IOException e) {
			
		}
	}
	
	private void readAndProcessCommand() {
		String command = this.readCommand();
		this.processCommand(command);
	}

	private void processCommand(String command) {
		String commandName = getFirstWord(command);
		String commandArgument = getRestCommand(command);
		
		if (commandName == null) {
			throw new Error("command type string cannot be null!");
		}
		
		if (commandName.equalsIgnoreCase("add")) {
			this.addTask(commandArgument);
		} else if (commandName.equalsIgnoreCase("display")) {
			this.displayTask();
		} else if (commandName.equalsIgnoreCase("delete")) {
		 	this.deleteTask(commandArgument);
		} else if (commandName.equalsIgnoreCase("clear")) {
			this.clearTask();
		} else if (commandName.equalsIgnoreCase("exit")) {
			this.exitTextBuddy();
		} else {
			throw new Error("invalid command type");
		}
		
	}
	

	private void exitTextBuddy() {
		// TODO Auto-generated method stub
		
	}

	private void clearTask() {
		// TODO Auto-generated method stub
		
	}

	private void deleteTask(String commandArgument) {
		// TODO Auto-generated method stub
		
	}

	private void displayTask() {
		// TODO Auto-generated method stub
		
	}

	private void addTask(String commandArgument) {
		// TODO Auto-generated method stub
		
	}

	private String readCommand() {
		String command = this.scanner.nextLine();
		return command;
	}

	private String getFirstWord(String command) {
		String[] commandArray = command.split(" ", 2);
		return commandArray[0];
	}
	
	private String getRestCommand(String command) {
		String[] commandArray = command.split(" ", 2);
		if (commandArray.length() > 1) {
			return commandArray[1];
		} else {
			return null;
		}
	}

	private void show(String message) {
		System.out.println(message);
	}

	private void showPrompt() {
		this.show("command: ");
	}

	private void showWelcome() {
		this.show("Welcome to TextBuddy. mytextfile.txt is ready for use"); // string format TODO
	}
}
