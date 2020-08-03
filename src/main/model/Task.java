package model;

import model.persistence.Reader;
import model.persistence.Saveable;

import java.io.PrintWriter;

// Represents an task having a estimated time, description and completed/uncompleted status
public class Task implements Saveable {
    private String description;
    private boolean isCompleted;
    private int time;

    //EFFECTS: initialize the description of task and assign time to estimated time, isCompleted is always false;
    public Task(String description, int time) {
        this.description = description;
        this.isCompleted = false;
        this.time = time;
    }

    //EFFECTS: initialize the description of task and assign time to estimated time,
    // assign isCompleted for completed status
    public Task(String description, int time, boolean isCompleted) {
        this.description = description;
        this.time = time;
        this.isCompleted = isCompleted;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setTime(int time) {
        this.time = time;
    }


    //EFFECTS: write the task to the file
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(description);
        printWriter.print(Reader.SPACE);
        printWriter.print(time);
        printWriter.print(Reader.SPACE);
        if (isCompleted) {
            printWriter.println("completed");
        } else {
            printWriter.println("uncompleted");
        }
    }
}
