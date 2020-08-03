package model.persistence;

import model.Task;
import model.persistence.Reader;
import model.persistence.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    private static final String TEST_FILE = "./data/testTasks.txt";
    private Writer testWriter;
    private Task task1;
    private Task task2;

    @BeforeEach
    public void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        task1 = new Task("task1",35,true);
        task2 = new Task("task2",45,false);
    }

    @Test
    public void testWriteFile() {
        testWriter.write(task1);
        testWriter.write(task2);
        testWriter.close();

        try{
            List<Task> tasks = Reader.readTasks(new File(TEST_FILE));
            Task task = tasks.get(0);
            assertEquals("task1",task.getDescription());
            assertEquals(35,task.getTime());
            assertTrue(task.getCompleted());

            Task task1 = tasks.get(1);
            assertEquals("task2",task1.getDescription());
            assertEquals(45,task1.getTime());
            assertFalse(task1.getCompleted());

        } catch (IOException ioException) {
            fail("Throw an unexpected exception");
        }
    }


}
