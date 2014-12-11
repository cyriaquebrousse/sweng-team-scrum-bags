package ch.epfl.scrumtool.database.google.operations.test;

import ch.epfl.scrumtool.database.google.operations.TaskResult;
import ch.epfl.scrumtool.exception.ScrumToolException;
import junit.framework.TestCase;

/**
 * 
 * @author aschneuw
 *
 */
public class TaskResultTest extends TestCase {

    public void testTaskResultNull() {
        try {
            Object test = null;
            new TaskResult<Object>(test);
            fail("NullPointerException expected for invalid result");
            
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testTaskResultNullException() {
        try {
            ScrumToolException test = null;
            new TaskResult<Object>(test);
            fail("NullPointerException expected for invalid exception");
        } catch (NullPointerException e) {
            
        }
    }

    public void testTaskResultScrumToolException() {
        ScrumToolException test = new ScrumToolException();
        new TaskResult<Object>(test);
    }
    
    public void testTaskResult() {
        Object test = new Object();
        new TaskResult<Object>(test);
    }

    public void testIsValidException() {
        ScrumToolException test = new ScrumToolException();
        TaskResult<Object> result = new TaskResult<Object>(test);
        assertFalse(result.isValid());
    }
    
    public void testIsValidResult() {
        Object test = new Object();
        TaskResult<Object> result = new TaskResult<Object>(test);
        assertTrue(result.isValid());
    }

    public void testGetException() {
        ScrumToolException test = new ScrumToolException();
        TaskResult<Object> result = new TaskResult<Object>(test);
        assertEquals(test, result.getException());
    }

    public void testGetResult() {
        Object test = new Object();
        TaskResult<Object> result = new TaskResult<Object>(test);
        assertEquals(test, result.getResult());
    }
}
