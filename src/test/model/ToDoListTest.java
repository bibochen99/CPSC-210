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
        for (int i=0; i < 5; i++) {
            toDoList.addTask(new Task("task" + i, i*10));
        }
        assertEquals(5,toDoList.getTotalTask());
        toDoList.deleteTask("task1");
        assertEquals(4,toDoList.getTotalTask());
        assertEquals(4,toDoList.getUncompletedTasks());
    }

    @Test
    public void testDeleteTaskCompleted() {
        for (int i=0; i < 5; i++) {
            toDoList.addTask(new Task("task" + i, i*10));
        }
        assertEquals(5,toDoList.getTotalTask());
        toDoList.completed("task4");
        assertEquals(5,toDoList.getTotalTask());
        assertEquals(4,toDoList.getUncompletedTasks());
        toDoList.deleteTask("task4");
        assertEquals(4,toDoList.getTotalTask());
        assertEquals(4,toDoList.getUncompletedTasks());
        toDoList.completed("task1");
        assertEquals(4,toDoList.getTotalTask());
        assertEquals(3,toDoList.getUncompletedTasks());
        toDoList.deleteTask("task1");
        assertEquals(3,toDoList.getTotalTask());
        assertEquals(3,toDoList.getUncompletedTasks());

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

    @Test
    public void testIsEmpty() {
        assertTrue(toDoList.isEmpty());
        toDoList.addTask(new Task("task1",50));
        assertFalse(toDoList.isEmpty());
    }

    }
