package ui;


import exception.NotInTheListException;
import model.Task;
import model.ToDoList;

import java.util.Scanner;

public class ListApp {
    private ToDoList toDoList = new ToDoList();

    public ListApp() {
        runList();
    }

    //EFFECTS: display the menu
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta-> add task");
        System.out.println("\td-> delete task");
        System.out.println("\ts-> show next uncompleted project");
        System.out.println("\tc -> mark it completed");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: process user input
    public void runList() {
        System.out.println("Welcome to the to-do-list application: ");
        boolean isOver = false;
        while (!isOver) {
            displayMenu();
            Scanner input = new Scanner(System.in);
            String button = input.next();
            if (button.equals("q")) {
                isOver = true;
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
    public void process(String button) throws NotInTheListException {
        if (button.equals("a")) {
            processAdd();
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
    public void processAdd() {
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
    public void processDelete() throws NotInTheListException {
        System.out.println("Delete one task, please enter the description of:");
        Scanner input = new Scanner(System.in);
        String d = input.next();
        toDoList.deleteTask(d);
        System.out.println("Successfully delete one task in you list.\nChoose you next step:");
    }

    //EFFECTS: process the next uncompleted one
    public void processNext() {
        System.out.println("Your next uncompleted task is: " + toDoList.getNextUncompletedTask());
        System.out.println("Choose your next step:");
    }

    //MODIFIES: this
    //EFFECTS: process the completed
    public void processCompleted() throws NotInTheListException {
        System.out.println("Great! Enter your completed task:");
        Scanner input = new Scanner(System.in);
        String d = input.next();
        toDoList.completed(d);
        System.out.println("Successfully mark it as completed!\nChoose your next step:");
    }
}
