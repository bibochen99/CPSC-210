package model;

// Represents an task having a estimated time, description and completed/uncompleted status
public class Task {
    private String description;
    private boolean isCompleted;
    private int time;

    //EFFECTS: initialize the description of task and assign time to estimated time, isCompleted is always false;
    public Task(String description, int time) {
        this.description = description;
        this.isCompleted = false;
        this.time = time;
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



}
