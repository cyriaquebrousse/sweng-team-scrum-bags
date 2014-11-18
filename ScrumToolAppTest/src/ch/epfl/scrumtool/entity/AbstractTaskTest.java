/**
 * 
 */
package ch.epfl.scrumtool.entity;

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
    private final static AbstractTask abstractTask = new AbstractTask(KEY, NAME, DESCRIPTION, STATUS, PRIORITY) {
        
        @Override
        public float getEstimatedTime() {
            return 0;
        }
    };
    
    public void testAbstractTask() {
        assertNotNull(abstractTask);
    }

    public void testGetEstimatedTime() {
        float time = abstractTask.getEstimatedTime();
        assertEquals(time, 0f);
    }

    public void testGetName() {
        String name = abstractTask.getName();
        assertEquals(name, NAME);
    }

    public void testGetDescription() {
        String description = abstractTask.getDescription();
        assertEquals(description, DESCRIPTION);
    }

    public void testGetStatus() {
        Status status = abstractTask.getStatus();
        assertEquals(status, STATUS);
    }

    public void testGetKey() {
        String key = abstractTask.getKey();
        assertEquals(key, KEY);
    }

    public void testGetPriority() {
        Priority priority = abstractTask.getPriority();
        assertEquals(priority, PRIORITY);
    }

    public void testEqualsObject() {
        AbstractTask abstractTask2 = abstractTask;
        assertTrue(abstractTask.equals(abstractTask2));
    }

}
