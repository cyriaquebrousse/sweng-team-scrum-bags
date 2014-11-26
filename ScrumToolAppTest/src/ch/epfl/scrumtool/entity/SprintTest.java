package ch.epfl.scrumtool.entity;

import junit.framework.TestCase;

public class SprintTest extends TestCase {
    
    private final static String TITLE = "title";
    private final static String KEY = "key";
    private final static long DEADLINE = 0;
            
    private final static Sprint sprint = new Sprint.Builder()
    .setTitle(TITLE)
    .setKey(KEY)
    .setDeadline(DEADLINE)
    .build();

    public void testGetDeadline() {
        long deadLine = sprint.getDeadline();
        assertEquals(DEADLINE, deadLine);
    }

    public void testGetKey() {
        String key = sprint.getKey();
        assertEquals(KEY, key);
    }

    public void testEqualsObject() {
        Sprint sprint2 = sprint;
        assertTrue(sprint2.equals(sprint));
    }

}
