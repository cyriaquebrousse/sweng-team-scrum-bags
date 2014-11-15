package ch.epfl.scrumtool.entity;

import junit.framework.TestCase;

import org.junit.Test;

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
        MainTask mainTask = build.build();
        MainTask.Builder newBuilder = new MainTask.Builder(mainTask);
        assertNotNull(newBuilder);
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
        MainTask mainTask = build.build();
        assertNotNull(mainTask);
    }

}
