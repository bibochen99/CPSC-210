package model.persistence;

import model.Task;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Reference: tellerApp
public class ReaderTest {
    @Test
    public void readTestTaskFile1() {
        try {
            List<Task> tasks = Reader.readTasks(new File("./data/testTaskFile1.txt"));
            Task task = tasks.get(0);
            assertEquals("edit",task.getDescription());
            assertEquals(35,task.getTime());
            assertFalse(task.getCompleted());

            Task task1 = tasks.get(1);
            assertEquals("testProject",task1.getDescription());
            assertEquals(45,task1.getTime());
            assertTrue(task1.getCompleted());

        } catch (IOException e) {
            fail("Throw an unexpected exception");
        }
    }

    @Test
    public void readTaskFile2() {
        try {
            List<Task> tasks = Reader.readTasks(new File("./data/testTaskFile2.txt"));
            Task task = tasks.get(0);
            assertEquals("writeFile",task.getDescription());
            assertEquals(50,task.getTime());
            assertTrue(task.getCompleted());

            Task task1 = tasks.get(1);
            assertEquals("editFile",task1.getDescription());
            assertEquals(35,task1.getTime());
            assertFalse(task1.getCompleted());
        } catch(IOException e) {
            fail("Throw an unexpected exception");
        }
    }

    @Test
    public void testException() {
        try {
            List<Task> tasks = Reader.readTasks(new File("./data/testException.txt"));
            fail("Do not throw a expected exception");
        } catch (IOException e) {
        }
    }
}
