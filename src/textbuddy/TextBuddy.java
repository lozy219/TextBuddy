package textbuddy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	
	private void readAndProcessCommand() throws IOException {
		try {
			String command = this.readCommand();
			this.processCommand(command);
		} catch (IOException e) {
			
		}
	}

	private void processCommand(String command) throws IOException {
		String commandName = getFirstWord(command);
		String commandArgument = getRestCommand(command);
		
		if (commandName == null) {
			throw new Error("command type string cannot be null!");
		}
		try {
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
		} catch (IOException e) {
			
		}
		
	}
	

	private void exitTextBuddy() throws IOException {
		try {
			this.save();
		} catch (IOException e) {
			
		} finally {
			System.exit(0);
		}
	}

	private void save() throws IOException {
		try {
			FileWriter out = new FileWriter(this.TextBuddyFile, false);
			BufferedWriter bw = new BufferedWriter(out);
			for (String task : todoList) {
				bw.write(task);
				bw.newLine();
			}
			bw.flush();
			bw.close();
			
			// reload the task list
			this.TextBuddyFile = new File(this.TextBuddyFile.getAbsolutePath());
			this.readTaskFromFile();
		} catch (IOException e) {
			
		}
		
	}

	private void clearTask() {
		
		try {
			this.save();
		} catch (IOException e) {
			
		}
		
	}

	private void deleteTask(String taskNumberArgument) {
		int taskNumber = Integer.parseInt(taskNumberArgument);
		if ((taskNumber < 0) || (taskNumber > this.todoList.size())) {
			this.show("invalid task number");
		} else {
			this.todoList.remove(taskNumber - 1);
		}
		
		try {
			this.save();
		} catch (IOException e) {
			
		}
		
	}

	private void displayTask() {
		if (this.todoList.size() == 0) {
			this.show("mytextfile.txt is empty");
		} else {
			for (int i = 0; i < this.todoList.size(); i++) {
				this.show(i + 1 + ":" + this.todoList.get(i));
			}
		}
	}

	private void addTask(String task) {
		this.todoList.add(task);
		try {
			this.save();
		} catch (IOException e) {
			
		}
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
		if (commandArray.length > 1) {
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
