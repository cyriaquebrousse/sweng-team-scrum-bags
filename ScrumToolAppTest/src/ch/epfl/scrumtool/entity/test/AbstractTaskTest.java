/**
 * 
 */
package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.entity.AbstractTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Status;
import junit.framework.TestCase;

/**
 * @author LeoWirz
 *
 */
public class AbstractTaskTest extends TestCase {
    
    private final static String KEY = "key1";
    private final static String NAME = "name1";
    private final static String DESCRIPTION = "description1";
    private final static Status STATUS = Status.READY_FOR_ESTIMATION;
    private final static Priority PRIORITY = Priority.NORMAL;

    @SuppressWarnings("serial")
    private final  AbstractTask task = new AbstractTask(KEY, NAME, DESCRIPTION, STATUS, PRIORITY) {
    };
    @SuppressWarnings("serial")
    private final  AbstractTask task2 = new AbstractTask("k", "n", "d", Status.IN_SPRINT, Priority.LOW) {
    };
    
    @SuppressWarnings("serial")
    public void testConstructor() {
        assertNotNull(task);
        // check for null arguments
        try {
            new AbstractTask(null, NAME, DESCRIPTION, STATUS, PRIORITY) {
            };
            fail("expected a NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            new AbstractTask(KEY, null, DESCRIPTION, STATUS, PRIORITY) {
            };
            fail("expected a NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            new AbstractTask(KEY, NAME, null, STATUS, PRIORITY) {
            };
            fail("expected a NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            new AbstractTask(KEY, NAME, DESCRIPTION, null, PRIORITY) {
            };
            fail("expected a NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            new AbstractTask(KEY, NAME, DESCRIPTION, STATUS, null) {
            };
            fail("expected a NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        
        // check for empty name / description
        try {
            new AbstractTask(KEY, "", DESCRIPTION, STATUS, PRIORITY) {
            };
            fail("expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            new AbstractTask(KEY, NAME, "", STATUS, PRIORITY) {
            };
            fail("expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testGetName() {
        assertEquals(NAME, task.getName());
    }

    public void testGetDescription() {
        assertEquals(DESCRIPTION, task.getDescription());
    }

    public void testGetStatus() {
        assertEquals(STATUS, task.getStatus());
    }

    public void testGetKey() {
        assertEquals(KEY, task.getKey());
    }

    public void testGetPriority() {
        assertEquals(PRIORITY, task.getPriority());
    }

    public void testEqualsObject() {
        AbstractTask task3 = task;
        assertTrue(task.equals(task3));
        assertFalse(task.equals(task2));
    }
    
    public void testHashCode() {
        AbstractTask task3 = task;
        assertEquals(task.hashCode(), task3.hashCode());
        assertNotSame(task.hashCode(), task2.hashCode());
    }

}
