package model;

import exception.NotInTheListException;

import java.util.ArrayList;

// Represents a list of tasks in the to-do-list
// with the estimated total time and total uncompleted tasks
public class ToDoList {
    private ArrayList<Task> tasks;
    private int totalTime;
    private int totalTask;
    private int uncompletedTasks;

    //EFFECTS: initializes each newly created ToDoList as an empty, totalTime is zero, uncompleted tasks is 0.
    public ToDoList() {
        tasks = new ArrayList<>();
        totalTime = 0;
        totalTask = 0;
        uncompletedTasks = 0;
    }

    //MODIFIES: this
    //EFFECTS: add one task on to-do-list
    public void addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
            totalTime += task.getTime();
            if (!task.getCompleted()) {
                uncompletedTasks++;
            }
            totalTask++;
        }
    }

    //REQUIRES: task in the list
    //MODIFIES: this
    //EFFECTS: delete one task on to-do-list; if it is uncompleted, reduce the total estimated time, uncompleted task
    // number minus 1, otherwise just remove it;
    public  void deleteTask(String description) throws NotInTheListException {
        for (Task task:tasks) {
            if (task.getDescription().equals(description)) {
                if (!task.getCompleted()) {
                    totalTime -= task.getTime();
                    uncompletedTasks--;
                }
                tasks.remove(task);
                totalTask--;
                break;
            } else if (task.equals(tasks.get(tasks.size() - 1))) {
                throw new NotInTheListException();
            }
        }
    }

    //REQUIRES: description should in the to-do-list
    //MODIFIES: this
    //EFFECTS: mark the task completed
    public void completed(String description) throws NotInTheListException {
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                task.setCompleted(true);
                totalTime -= task.getTime();
                uncompletedTasks--;
                break;
            } else if (task.equals(tasks.get(tasks.size() - 1))) {
                throw new NotInTheListException();
            }
        }
    }

    //EFFECTS: shows the next uncompleted task
    public String getNextUncompletedTask() {
        for (Task task : tasks) {
            if (!task.getCompleted()) {
                return task.getDescription();
            }
        }
        return "Null";
    }

    //REQUIRES: isCompleted == false; and task is in the list
    //MODIFIES: this
    //EFFECTS: change estimated time of one task
    public void changeTime(String description, int newTime) {
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                totalTime -= task.getTime();
                totalTime += newTime;
                task.setTime(newTime);
            }
        }
    }

    //EFFECTS: if the to-do-list is empty, return true; otherwise return false;
    public boolean isEmpty() {
        return tasks.size() == 0;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getUncompletedTasks() {
        return uncompletedTasks;
    }

    public int getTotalTask() {
        return totalTask;
    }
}
