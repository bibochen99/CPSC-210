package ui;


import exception.NotInTheListException;
import model.Task;
import model.ToDoList;
import model.persistence.Reader;
import model.persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

//Show the user interface in the console
public class ListApp {
    private ToDoList toDoList = new ToDoList();

    public ListApp() {
        runList();
    }

    //EFFECTS: display the menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta-> add task");
        System.out.println("\td-> delete task");
        System.out.println("\ts-> show next uncompleted project");
        System.out.println("\tc -> mark it completed");
        System.out.println("\tr-> read your tasks");
        System.out.println("\tp-> print all of your tasks");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: process user input
    private void runList() {
        System.out.println("Welcome to the to-do-list application: ");
        boolean isOver = false;
        while (!isOver) {
            displayMenu();
            Scanner input = new Scanner(System.in);
            String button = input.next();
            if (button.equals("q")) {
                isOver = true;
                processSave();
                System.out.println("Thank you bye!");
            } else {
                try {
                    process(button);
                } catch (NotInTheListException notInTheListException) {
                    System.out.println("Failure! The list does not have this task.");
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: process which steps using
    private void process(String button) throws NotInTheListException {
        if (button.equals("a")) {
            processAdd();
        } else if (button.equals("r")) {
            processRead();
        } else if (button.equals("p")) {
            processPrint();
        } else if (toDoList.isEmpty()) {
            System.out.println("Sorry, your list is empty, cannot do this step, please choose add one or quit:\n");
        } else if (button.equals("d")) {
            processDelete();
        } else if (button.equals("s")) {
            processNext();
        } else if (button.equals("c")) {
            processCompleted();
        } else {
            System.out.println("Invalid the input, please select correct one: ");
        }
    }

    //MODIFIES: this
    //EFFECTS: process add one task
    private void processAdd() {
        System.out.println("Add one task, please enter the description of:");
        Scanner input = new Scanner(System.in);
        String d = input.next();
        System.out.println("please enter the estimated time:");
        int t = input.nextInt();
        toDoList.addTask(new Task(d,t));
        System.out.println("Successfully add one task in you list.\n Choose you next step:");
    }

    //MODIFIES: this
    //EFFECTS: process delete task
    private void processDelete() throws NotInTheListException {
        System.out.println("Delete one task, please enter the description of:");
        Scanner input = new Scanner(System.in);
        String d = input.next();
        toDoList.deleteTask(d);
        System.out.println("Successfully delete one task in you list.\nChoose you next step:");
    }

    //EFFECTS: process the next uncompleted one
    private void processNext() {
        System.out.println("Your next uncompleted task is: " + toDoList.getNextUncompletedTask());
        System.out.println("Choose your next step:");
    }

    //MODIFIES: this
    //EFFECTS: process the completed
    private void processCompleted() throws NotInTheListException {
        System.out.println("Great! Enter your completed task:");
        Scanner input = new Scanner(System.in);
        String d = input.next();
        toDoList.completed(d);
        System.out.println("Successfully mark it as completed!\nChoose your next step:");
    }

    //EFFECTS: process save the tasks
    private void processSave() {
        System.out.println("Do you want to save your project");
        System.out.println("If you want enter yes, otherwise enter no");
        boolean isOver = false;
        while (!isOver) {
            Scanner input = new Scanner(System.in);
            String button = input.next();
            if (button.equals("yes")) {
                System.out.println("Please enter the name of the file");
                String fileName = input.next();
                saveTasks(fileName);
                isOver = true;
            } else if (button.equals("no")) {
                isOver = true;
            } else {
                System.out.println("Invalid input. Please enter yes or no");
            }
        }
    }

    //EFFECTS: save the tasks to the named file
    private void saveTasks(String name) {
        String fileName = "./data/" + name + ".txt";

        try {
            Writer writer = new Writer(new File(fileName));
            for (Task task:toDoList.getTasks()) {
                writer.write(task);
            }
            writer.close();
            System.out.println("Successfully save the tasks");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save tasks to" + fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES:this
    //EFFECTS: Read file and add tasks to the to to-do-list
    private void processRead() {
        System.out.println("Enter your file name to read");
        Scanner input = new Scanner(System.in);
        String name = input.next();
        String fileName = "./data/" + name + ".txt";
        try {
            List<Task> tasks = Reader.readTasks(new File(fileName));
            for (Task task : tasks) {
                toDoList.addTask(task);
            }
            System.out.println("Successfully add this file to your to-do-list!");
        } catch (IOException e) {
            System.out.println("Unable to open file: " + fileName);
        }
    }

    //EFFECTS: print all the tasks in the to-do-list
    private void processPrint() {
        if (toDoList.getTasks().size() == 0) {
            System.out.println("Null tasks");
        } else {
            for (Task task:toDoList.getTasks()) {
                System.out.print(task.getDescription() + " ");
                System.out.print(task.getTime() + " ");
                System.out.println(task.getCompleted() ? "completed" : "uncompleted" + " ");
            }
            System.out.println();
            System.out.print("There are total " + toDoList.getTotalTask() + " tasks \t");
            System.out.print("Uncompleted tasks: " + toDoList.getUncompletedTasks() + "\t");
            System.out.println("Estimated time: " + toDoList.getTotalTime());
            System.out.println();
            System.out.println("Good luck and take efforts!");
        }
    }
}
