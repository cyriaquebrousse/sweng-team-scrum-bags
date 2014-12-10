package ch.epfl.scrumtool.entity;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Status;

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

    @Test(expected=UnsupportedOperationException.class)
    public void testGetEstimatedTime() {
    }

    @Test
    public void testEqualsObject() {
        MainTask mainTask2 = mainTask;
        assertEquals(mainTask, mainTask2);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testGetIssuesFinishedCount() {
    }

    @Test
    public void testGetName() {
        String name = mainTask.getName();
        assertEquals(name, NAME_1);
    }

    @Test
    public void testGetDescription() {
        String description = mainTask.getDescription();
        assertEquals(description, DESCRIPTION_1);
    }

    @Test
    public void testGetStatus() {
        Status status = mainTask.getStatus();
        assertEquals(status, STATUS_1);
    }

    @Test
    public void testGetKey() {
        String key = mainTask.getKey();
        assertEquals(key, KEY_1);
    }

    @Test
    public void testGetPriority() {
        Priority priority = mainTask.getPriority();
        assertEquals(priority, PRIORITY_1);
    }

    public void testTODO() {
        //TODO test null parameters and other stuff
        fail("Not implemented yet");
    }
}
