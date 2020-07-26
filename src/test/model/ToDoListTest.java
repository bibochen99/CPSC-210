package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList toDoList;

    @BeforeEach
    public void setup(){
        toDoList = new ToDoList();
    }

    @Test
    public void testAddTask() {
        Task task = new Task("task1",50);
        assertEquals(0,toDoList.getTotalTask());
        toDoList.addTask(task);
        assertEquals(1,toDoList.getTotalTask());
        toDoList.addTask(task);
        assertEquals(1,toDoList.getTotalTask());
    }

    @Test
    public void testDeleteTaskUncompleted() {
        toDoList.addTask(new Task("task1",50));
        assertEquals(1,toDoList.getTotalTask());
        toDoList.deleteTask("task1");
        assertEquals(0,toDoList.getTotalTask());
        assertEquals(0,toDoList.getUncompletedTasks());
    }

    @Test
    public void testDeleteTaskCompleted() {
        toDoList.addTask(new Task("task1",50));
        toDoList.addTask(new Task("task2",35));
        assertEquals(2,toDoList.getTotalTask());
        assertEquals(2,toDoList.getUncompletedTasks());
        toDoList.completed("task2");
        assertEquals(1,toDoList.getUncompletedTasks());
        assertEquals(2,toDoList.getTotalTask());
        toDoList.deleteTask("task2");
        assertEquals(1,toDoList.getTotalTask());
        assertEquals(1,toDoList.getUncompletedTasks());
    }

    @Test
    public void testCompleted() {
        toDoList.addTask(new Task("task1",50));
        assertFalse(toDoList.getTasks().get(0).getCompleted());
        toDoList.completed("task1");
        assertTrue(toDoList.getTasks().get(0).getCompleted());
    }

    @Test
    public void testGetNextUncompletedTask() {
        toDoList.addTask(new Task("task1",50));
        toDoList.addTask(new Task("task2",35));
        assertEquals("task1",toDoList.getNextUncompletedTask());
        toDoList.completed("task1");
        assertEquals("task2",toDoList.getNextUncompletedTask());
        toDoList.completed("task2");
        assertEquals("Null",toDoList.getNextUncompletedTask());
    }

    @Test
    public void testChangeTime() {
        toDoList.addTask(new Task("task1",50));
        toDoList.addTask(new Task("task2",35));
        assertEquals(85,toDoList.getTotalTime());
        toDoList.changeTime("task1",35);
        assertEquals(70,toDoList.getTotalTime());
    }

    }
