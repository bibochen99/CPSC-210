package model;

import exception.NotInTheListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList toDoList;

    @BeforeEach
    public void setup() {
        toDoList = new ToDoList();
    }


    @Test
    public void testAddTask() {
        Task task = new Task("task1", 50);
        Task task1 = new Task("task1", 50);
        toDoList.addTask(task);
        toDoList.addTask(task);
        assertEquals(1, toDoList.getTotalTask());
        toDoList.addTask(task1);
        assertEquals(2, toDoList.getTotalTask());
    }

    @Test
    public void testAddTaskWithComplemented() {
        Task task = new Task("task1", 50);
        Task task1 = new Task("task1", 50,true);
        toDoList.addTask(task);
        assertEquals(1,toDoList.getTotalTask());
        assertEquals(1,toDoList.getUncompletedTasks());
        toDoList.addTask(task1);
        assertEquals(2,toDoList.getTotalTask());
        assertEquals(1,toDoList.getUncompletedTasks());
    }

    @Test
    public void testDeleteTaskCompleted() {
        try {
            toDoList.deleteTask("task");
        } catch (NotInTheListException notInTheListException) {
        }
        toDoList.addTask(new Task("task", 25));
        toDoList.addTask(new Task("task1",25));
        assertEquals(2, toDoList.getTotalTask());
        try {
            toDoList.completed("task1");
        } catch (NotInTheListException notInTheListException) {
            fail("Throw an unexpected exception");
        }
        assertEquals(2, toDoList.getTotalTask());
        assertEquals(1, toDoList.getUncompletedTasks());
        try {
            toDoList.deleteTask("task1");
        } catch (NotInTheListException notInTheListException) {
            fail("Throw an unexpected exception");
        }
        assertEquals(1, toDoList.getTotalTask());
        assertEquals(1, toDoList.getUncompletedTasks());
    }

    @Test
    public void testDeleteTaskUnCompleted() {
        toDoList.addTask(new Task("task", 25));
        assertEquals(1, toDoList.getTotalTask());
        try {
            toDoList.deleteTask("task");
        } catch (NotInTheListException notInTheListException) {
            fail("Throw an unexpected exception");
        }
        assertEquals(0, toDoList.getTotalTask());
        assertEquals(0, toDoList.getUncompletedTasks());
    }

    @Test
    public void testDeleteTaskWithException() {
        toDoList.addTask(new Task("task", 25));
        assertEquals(1, toDoList.getTotalTask());
        try {
            toDoList.deleteTask("task1");
            fail("Do not throw a expected exception");
        } catch (NotInTheListException notInTheListException) {
        }
    }

    @Test
    public void testCompleted() {
        try {
            toDoList.completed("task");
        } catch (NotInTheListException notInTheListException) {
        }
        toDoList.addTask(new Task("task", 25));
        assertFalse(toDoList.getTasks().get(0).getCompleted());
        try {
            toDoList.completed("task");
        } catch (NotInTheListException notInTheListException) {
            fail("Throw an unexpected exception");
        }
        assertTrue(toDoList.getTasks().get(0).getCompleted());
    }

    @Test
    public void testCompletedWithException() {
        toDoList.addTask(new Task("task", 25));
        assertFalse(toDoList.getTasks().get(0).getCompleted());
        try {
            toDoList.completed("task1");
            fail("Do not throw a expected exception");
        } catch (NotInTheListException notInTheListException) {
        }
    }

    @Test
    public void testGetNextUncompletedTask() {
        toDoList.addTask(new Task("task1", 50));
        toDoList.addTask(new Task("task2", 35));
        assertEquals("task1", toDoList.getNextUncompletedTask());
        try {
            toDoList.completed("task1");
        } catch (NotInTheListException notInTheListException) {
            fail("Throw an unexpected exception");
        }
        assertEquals("task2", toDoList.getNextUncompletedTask());
        try {
            toDoList.completed("task2");
        } catch (NotInTheListException notInTheListException) {
            fail("Throw an unexpected exception");
        }
        try {
            toDoList.completed("task");
            fail("Do not throw expected exception");
        } catch (NotInTheListException notInTheListException) {
        }
        assertEquals("Null", toDoList.getNextUncompletedTask());
    }

    @Test
    public void testChangeTime() {
        toDoList.addTask(new Task("task1", 50));
        toDoList.addTask(new Task("task2", 35));
        assertEquals(85, toDoList.getTotalTime());
        toDoList.changeTime("task1", 35);
        assertEquals(70, toDoList.getTotalTime());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(toDoList.isEmpty());
        toDoList.addTask(new Task("task1", 50));
        assertFalse(toDoList.isEmpty());
    }

}
