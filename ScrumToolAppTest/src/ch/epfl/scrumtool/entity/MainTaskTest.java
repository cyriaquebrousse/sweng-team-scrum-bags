package ch.epfl.scrumtool.entity;

import junit.framework.TestCase;

import org.junit.Test;

public class MainTaskTest extends TestCase{
    
    private static final String KEY_1 = "ID1";
    private static final String NAME_1 = "name1";
    private static final String DESCRIPTION_1 = "description";
    private static final Status STATUS_1 = Status.READY_FOR_ESTIMATION;
    private static final Priority PRIORITY_1 = Priority.NORMAL;
    
    private static final MainTask mainTask = new MainTask.Builder()
        .setKey(KEY_1)
        .setName(NAME_1)
        .setDescription(DESCRIPTION_1)
        .setStatus(STATUS_1)
        .setPriority(PRIORITY_1)
        .build();
    
    @Test
    public void testHashCode() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetEstimatedTime() {
        fail("Not yet implemented");
    }

    @Test
    public void testEqualsObject() {
        MainTask mainTask2 = mainTask;
        assertEquals(mainTask, mainTask2);
    }

    @Test
    public void testGetIssuesFinishedCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testAbstractTask() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetName() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDescription() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetStatus() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPriority() {
        fail("Not yet implemented");
    }

}
