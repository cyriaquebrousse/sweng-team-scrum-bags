package ch.epfl.scrumtool.entity.test;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Status;

public class MainTaskBuilderTest extends TestCase{
    
    private static final String id = "007";
    private static final String name = "main task name";
    private static final String description = "this is a description";
    private static final Status status = Status.READY_FOR_SPRINT;
    private static final Priority priority = Priority.URGENT;
    
    private static final MainTask.Builder build = new MainTask.Builder();

    @Test
    public void testBuilder() {
        assertNotNull(build);
    }

    @Test
    public void testBuilderMainTask() {
        build.setDescription(description);
        build.setName(name);
        build.setPriority(priority);
        build.setStatus(status);
        build.setKey(id);
        MainTask mainTask = build.build();
        MainTask.Builder newBuilder = new MainTask.Builder(mainTask);
        assertNotNull(newBuilder);
        assertEquals(description, newBuilder.getDescription());
        assertEquals(name, newBuilder.getName());
        assertEquals(priority, newBuilder.getPriority());
        assertEquals(id, newBuilder.getKey());
        assertEquals(status, newBuilder.getStatus());
    }

    @Test
    public void testGetKey() {
        build.setKey(id);
        assertEquals(build.getKey(), id);
    }

    @Test
    public void testSetKey() {
        build.setKey(id);
        assertEquals(build.getKey(), id);
    }

    @Test
    public void testGetName() {
        build.setName(name);
        assertEquals(build.getName(), name);
    }

    @Test
    public void testSetName() {
        build.setName(name);
        assertEquals(build.getName(), name);
    }

    @Test
    public void testGetDescription() {
        build.setDescription(description);
        assertEquals(build.getDescription(), description);
    }

    @Test
    public void testSetDescription() {
        build.setDescription(description);
        assertEquals(build.getDescription(), description);
    }

    @Test
    public void testGetStatus() {
        build.setStatus(status);
        assertEquals(build.getStatus(), status);
    }

    @Test
    public void testSetStatus() {
        build.setStatus(status);
        assertEquals(build.getStatus(), status);
    }

    @Test
    public void testGetPriority() {
        build.setPriority(priority);
        assertEquals(build.getPriority(), priority);
    }

    @Test
    public void testSetPriority() {
        build.setPriority(priority);
        assertEquals(build.getPriority(), priority);
    }

    @Test
    public void testBuild() {
        build.setDescription(description);
        build.setName(name);
        build.setPriority(priority);
        build.setStatus(status);
        build.setKey(id);
        MainTask mainTask = build.build();
        assertNotNull(mainTask);
        assertEquals(description, mainTask.getDescription());
        assertEquals(name, mainTask.getName());
        assertEquals(priority, mainTask.getPriority());
        assertEquals(id, mainTask.getKey());
        assertEquals(status, mainTask.getStatus());
    }

    public void testTODO() {
        //TODO test null parameters and other stuff
        fail("Not implemented yet");
    }
}
