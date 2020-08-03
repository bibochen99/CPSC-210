package model.persistence;


import model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//A reader that can read Task data from a file
//Reference: TellerAPP
public class Reader {
    public static final String SPACE = " ";

    // EFFECTS: returns a list of tasks parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Task> readTasks(File file) throws IOException {
        List<String> fileContent = Files.readAllLines(file.toPath());
        return parseContent(fileContent);
    }

    // EFFECTS: returns a list of tasks parsed from list of strings
    // where each string contains data for one task
    private static List<Task> parseContent(List<String> fileContent) {
        List<Task> tasks = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            tasks.add(parseTask(lineComponents));
        }

        return tasks;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on SPACE
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(SPACE);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 3 where element 0 represents the
    // description of the task to be constructed, element 1 represents
    // the estimated time, elements 2 represents the completed status;
    // EFFECTS: returns an task constructed from components
    private static Task parseTask(List<String> components) {
        String description = components.get(0);
        int time = Integer.parseInt(components.get(1));
        boolean isCompleted = components.get(2).equals("completed");
        return new Task(description,time,isCompleted);
    }

}
