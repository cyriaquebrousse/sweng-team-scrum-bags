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
    private final static AbstractTask ABSTRACT_TASK = new AbstractTask(KEY, NAME, DESCRIPTION, STATUS, PRIORITY) {
    };
    
    public void testAbstractTask() {
        assertNotNull(ABSTRACT_TASK);
    }

    public void testGetName() {
        String name = ABSTRACT_TASK.getName();
        assertEquals(name, NAME);
    }

    public void testGetDescription() {
        String description = ABSTRACT_TASK.getDescription();
        assertEquals(description, DESCRIPTION);
    }

    public void testGetStatus() {
        Status status = ABSTRACT_TASK.getStatus();
        assertEquals(status, STATUS);
    }

    public void testGetKey() {
        String key = ABSTRACT_TASK.getKey();
        assertEquals(key, KEY);
    }

    public void testGetPriority() {
        Priority priority = ABSTRACT_TASK.getPriority();
        assertEquals(priority, PRIORITY);
    }

    public void testEqualsObject() {
        AbstractTask abstractTask2 = ABSTRACT_TASK;
        assertTrue(ABSTRACT_TASK.equals(abstractTask2));
    }
    
    public void testTODO() {
        //TODO test null parameters and other stuff
        fail("Not implemented yet");
    }

}
