package textbuddy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * class TextBuddy
 * 
 * this classed is used to store and display the tasks from a particular file.
 * user can manage the tasks through these command:
 * 
 * add [taskName]: command add will append the task given to the end of the task list;
 * display: command display will show all the tasks stored in the file, and the tasks is ranked
 * by the creating date; the tasks will be numbered from 1 onwards;
 * delete [taskNumber]: command delete is used to remove the task from the list;
 * clear: command clear will delete every tasks from the list;
 * exit: command exit is used when the use want to quit the program.
 * 
 * the user is expected to provide the file path as the argument for this program.
 * any commands not stated above will be considered invalid.
 * the file is saved every time when it is modified.
 * 
 * Notice: when testing, use java TextBuddy file.txt <testinput.txt >output.txt
 * 
 * @author leimingyu
 *
 */
public class TextBuddy {
	
	private File TextBuddyFile;
	private ArrayList<String> todoList = new ArrayList<String>();
	private static Scanner scanner = new Scanner(System.in);
	
	// declaration of the constant for displayed messages and errors
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String PROMPT_MESSAGE = "command: ";
	private static final String ADD_TASK_MESSAGE = "added to %1$s: \"%2$s\"";
	private static final String DELETE_TASK_MESSAGE = "deleted from %1$s: \"%2$s\"";
	private static final String EMPTY_FILE_MESSAGE = "%1$s is empty";
	private static final String CLEAR_TASK_MESSAGE = "all content deleted from %1$s";
	private static final String INVALID_TASK_NO_MESSAGE = "Invalid task number: %1$d";
	private static final String INVALID_COMMAND_ERROR = "Invalid command type: %1$s";
	private static final String NULL_COMMAND_ERROR = "Command type string cannot be null";
	private static final String INVALID_FILE_PATH_ERROR = "Invalid file path";


	// These are the possible command types
	enum COMMAND_TYPE {
		ADD_TASK, DISPLAY_TASK, DELETE_TASK, CLEAR_TASK, EXIT, INVALID
	};
	
	/**
	 * Constructor for TextBuddy
	 * @param filePath
	 * @throws IOException 
	 */
	public TextBuddy(String filePath) {
		this.TextBuddyFile = new File(filePath);
		if (!this.TextBuddyFile.exists()) {
			try {
				this.createFile(filePath);
			} catch (Exception e) {
				
			}
		} else {
			try {
				this.readTaskFromFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {	
		try {
			TextBuddy textBuddy = new TextBuddy(args[0]);
			textBuddy.showWelcome();
			while (true) {
				textBuddy.showPrompt();
				textBuddy.readAndProcessCommand();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			show(INVALID_FILE_PATH_ERROR);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * file manipulation
	 */
	
	/**
	 * createFile
	 * 
	 * this method is called when the file given does not exist, a new file will be created
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private void createFile(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(filePath, "UTF-8");
		writer.close();
	}

	/**
	 * readTaskFromFile
	 * 
	 * all the tasks in the file will be read and stored in the todoList attribute
	 * @throws IOException
	 */
	private void readTaskFromFile() throws IOException {
		String line;
		FileReader in = new FileReader(this.TextBuddyFile);
		BufferedReader br = new BufferedReader(in);
		this.todoList.clear(); // clear the todoList before loading
		while ((line = br.readLine()) != null) {
			this.todoList.add(line);
		}
		br.close();
	}
	
	/**
	 * save
	 * 
	 * everything inside todoList will be written into the file
	 * @throws IOException
	 */
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
			e.printStackTrace();
		}
		
	}
	
	/*
	 * user I/O
	 */
	
	/**
	 * readAndProcessCommand
	 * 
	 * @throws IOException
	 */
	private void readAndProcessCommand() throws IOException {
		try {
			String command = this.readCommand();
			this.processCommand(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * readCommand
	 * 
	 * use scanner to read next line from the user input
	 * @return the command
	 */
	private String readCommand() {
		String command = TextBuddy.getScanner().nextLine();
		return command;
	}

	/**
	 * processCommand
	 * 
	 * this method will determine the command type first then call other methods accordingly
	 * @param command
	 * @throws IOException
	 */
	public String processCommand(String command) throws IOException {
		String commandName = getFirstWord(command);
		String commandArgument = getRestCommand(command);
		COMMAND_TYPE commandType = TextBuddy.determineCommandType(commandName);
		String returnValue = null;
	
		try {
			switch (commandType) {
			case ADD_TASK:
				returnValue = this.addTask(commandArgument);			
				break;
				
			case DISPLAY_TASK:
				returnValue = this.displayTask();
				break;
			
			case DELETE_TASK:
				// TODO: add confirm before deleting the task
				returnValue = this.deleteTask(commandArgument);
				break;
				
			case CLEAR_TASK:
				// TODO: add confirm before clearing the file
				returnValue = this.clearTask();
				break;
				
			case EXIT:
				returnValue = this.exitTextBuddy();
				break;

			default:
				returnValue = show(String.format(INVALID_COMMAND_ERROR, commandName));
				break;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	/**
	 * determineCommandType 
	 * 
	 * 
	 * this method determines which of the supported command types the user wants to perform
	 * @param commandTypeString first word of the user command
	 * @return command type
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		
		if (commandTypeString == null) {
			throw new Error(NULL_COMMAND_ERROR);
		}
		
		if (commandTypeString.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD_TASK;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY_TASK;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
		 	return COMMAND_TYPE.DELETE_TASK;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR_TASK;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}
	
	/**
	 * show
	 * 
	 * print out the message then create a new line
	 * @param message
	 * @return 
	 */
	private static String show(String message) {
		System.out.println(message);
		return message;
	}

	/**
	 * showWithoutLineBreak
	 * 
	 * print out the message without creating a new line
	 * @param message
	 */
	private void showWithoutLineBreak(String message) {
		System.out.print(message);
	}
	
	/**
	 * showPrompt
	 * 
	 * display the prompt message before a new command is inputed
	 */
	private void showPrompt() {
		showWithoutLineBreak(PROMPT_MESSAGE);
	}

	/**
	 * showWelcome
	 * 
	 * display the greeting message every time the program is run 
	 */
	private void showWelcome() {
		show(String.format(WELCOME_MESSAGE, this.TextBuddyFile.getName()));
	}

	/*
	 * Operations
	 */
	
	/**
	 * addTask
	 * 
	 * pushing a new task to the end of todoList then save the file
	 * @param task
	 * @return 
	 */
	private String addTask(String task) {
		this.todoList.add(task);
		try {
			this.save();
			return show(String.format(ADD_TASK_MESSAGE, this.TextBuddyFile.getName(), task));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * deleteTask
	 * 
	 * delete the task with given number from the list, then save the file
	 * @param taskNumberArgument
	 * @return 
	 */
	private String deleteTask(String taskNumberArgument) {
		int taskNumber = Integer.parseInt(taskNumberArgument);
		
		if ((taskNumber <= 0) || (taskNumber > this.todoList.size())) {
			// task number is not valid
			return show(String.format(INVALID_TASK_NO_MESSAGE, taskNumber));
		} else {
			String task = this.todoList.get(taskNumber - 1);
			this.todoList.remove(taskNumber - 1);
			try {
				this.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return show(String.format(DELETE_TASK_MESSAGE, this.TextBuddyFile.getName(), task));
		}
		
	}
	
	/**
	 * clearTask
	 * 
	 * delete everything from the todoList, then save the file
	 * @return 
	 */
	private String clearTask() {
		
		this.todoList.clear();
		try {
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return show(String.format(CLEAR_TASK_MESSAGE, this.TextBuddyFile.getName()));
		
		
	}

	/**
	 * displayTask
	 * 
	 * display the list of tasks
	 * @return 
	 */
	private String displayTask() {
		String tasks = "";
		if (this.todoList.size() == 0) {
			return show(String.format(EMPTY_FILE_MESSAGE, this.TextBuddyFile.getName()));
		} else {
			for (int i = 0; i < this.todoList.size(); i++) {
				String task = i + 1 + ":" + this.todoList.get(i);
				show(task);
				tasks = tasks + task;
			}
		}
		return tasks;
	}
	
	/**
	 * exitTextBuddy
	 * 
	 * save the file and then exit the program
	 * @return 
	 * @throws IOException
	 */
	private String exitTextBuddy() throws IOException {
		try {
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
		return null;
	}

	/*
	 * auxiliary method
	 */
	
	/**
	 * getFirstWord
	 * 
	 * retrieve the first word from a string
	 * @param command
	 * @return the first word
	 */
	private String getFirstWord(String command) {
		String[] commandArray = command.split(" ", 2);
		return commandArray[0];
	}
	
	/**
	 * getRestCommand
	 * 
	 * retrieve the command argument from a string
	 * @param command
	 * @return the first word
	 */
	private String getRestCommand(String command) {
		String[] commandArray = command.split(" ", 2);
		
		if (commandArray.length > 1) {
			return commandArray[1];
		} else {
			// command without argument
			return null;
		}
	}

	public static Scanner getScanner() {
		return scanner;
	}

	public static void setScanner(Scanner scanner) {
		TextBuddy.scanner = scanner;
	}
}
