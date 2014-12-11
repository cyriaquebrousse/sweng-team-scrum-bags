package ch.epfl.scrumtool.entity.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.objenesis.tck.Main;

import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.entity.MainTask.Builder;
import ch.epfl.scrumtool.gui.utils.test.MockData;

/**
 * 
 * @author
 *
 */
public class MainTaskTest extends TestCase{
    
    private static final String KEY = "ID1";
    private static final String NAME = "name1";
    private static final String DESCRIPTION = "description";
    private static final Status STATUS = Status.READY_FOR_ESTIMATION;
    private static final Priority PRIORITY = Priority.NORMAL;
    private static final int TOTAL_ISSUE = 5;
    private static final int FINISHED_ISSUE = 2;
    private static final float TOTAL_ISSUE_TIME = 15f;
    private static final float FINISHED_ISSUE_TIME = 6f;
    
    private static final MainTask.Builder BUILDER = new MainTask.Builder()
        .setKey(KEY)
        .setName(NAME)
        .setDescription(DESCRIPTION)
        .setStatus(STATUS)
        .setPriority(PRIORITY)
        .setTotalIssues(TOTAL_ISSUE)
        .setTotalIssueTime(TOTAL_ISSUE_TIME)
        .setFinishedIssues(FINISHED_ISSUE)
        .setFinishedIssueTime(FINISHED_ISSUE_TIME);
    private static final MainTask MAINTASK = BUILDER.build();
    private static final MainTask MAINTASK2 = MockData.TASK1;
    
    public void testConstructor() {
        // It's impossible to have null value passed to constructor
        // all builder fields are initialized
        // all setter check for null
        
        // check totalIssue < finishedIssue
        try {
            BUILDER.setFinishedIssues(10).setTotalIssues(3).build();
            fail("expected an IllegalStateException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        // check totalIssueTime < finishedIssueTime
        try {
            BUILDER.setFinishedIssueTime(10).setTotalIssueTime(3).build();
            fail("expected an IllegalStateException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    public void testGetTotalIssues() {
        assertEquals(TOTAL_ISSUE, MAINTASK.getTotalIssues());
    }
    
    public void testGetTotalIssueTime() {
        assertEquals(TOTAL_ISSUE_TIME, MAINTASK.getTotalIssueTime());
    }
    
    public void testGetFinishedIssues() {
        assertEquals(FINISHED_ISSUE, MAINTASK.getFinishedIssues());
    }
    
    public void testGetFinishedIssueTime() {
        assertEquals(FINISHED_ISSUE_TIME, MAINTASK.getFinishedIssueTime());
    }
    
    public void testUnfinishedIssue() {
        assertEquals(TOTAL_ISSUE - FINISHED_ISSUE, MAINTASK.unfinishedIssues());
    }
    
    public void testUnfinishedIssueTime() {
        assertEquals(TOTAL_ISSUE_TIME - FINISHED_ISSUE_TIME, MAINTASK.unfinishedIssueTime());
    }
    
    public void testCompletedIssueRate() {
        assertEquals(FINISHED_ISSUE/(float) TOTAL_ISSUE, MAINTASK.completedIssueRate());
        MainTask task = BUILDER.setTotalIssues(20).setFinishedIssues(5).setFinishedIssues(0).setTotalIssues(0).build();
        assertTrue(0 == task.completedIssueRate());
    }
    
    public void testIssueTimeCompletionRate() {
        assertEquals(FINISHED_ISSUE_TIME/TOTAL_ISSUE_TIME, MAINTASK.issueTimeCompletionRate());
        MainTask task = BUILDER.setTotalIssues(20).setFinishedIssues(5).setFinishedIssueTime(0)
                .setTotalIssueTime(0).build();
        assertTrue(0 == task.issueTimeCompletionRate());
    }
    
    public void testGetBuilder() {
        MainTask.Builder builder = MAINTASK.getBuilder();
        assertEquals(KEY, builder.getKey());
        assertEquals(NAME, builder.getName());
        assertEquals(DESCRIPTION, builder.getDescription());
        assertEquals(STATUS, builder.getStatus());
        assertEquals(PRIORITY, builder.getPriority());
        assertEquals(TOTAL_ISSUE, builder.getTotalIssues());
        assertEquals(TOTAL_ISSUE_TIME, builder.getTotalIssueTime());
        assertEquals(FINISHED_ISSUE, builder.getFinishedIssues());
        assertEquals(FINISHED_ISSUE_TIME, builder.getFinishedIssueTime());
    }
    
    public void testBuilderGetSetTotalIssue() {
        BUILDER.setTotalIssues(-5);
        assertEquals(0, BUILDER.getTotalIssues());
        BUILDER.setTotalIssues(15);
        assertEquals(15, BUILDER.getTotalIssues());
    }
    
    public void testBuilderGetSetTotalIssueTime() {
        BUILDER.setTotalIssueTime(-5f);
        assertEquals(0f, BUILDER.getTotalIssueTime());
        BUILDER.setTotalIssueTime(15f);
        assertEquals(15f, BUILDER.getTotalIssueTime());
    }
    
    public void testBuilderGetSetFinishedIssue() {
        BUILDER.setFinishedIssues(-5);
        assertEquals(0, BUILDER.getFinishedIssues());
        BUILDER.setFinishedIssues(15);
        assertEquals(15, BUILDER.getFinishedIssues());
    }
    
    public void testBuilderGetSetFinishedIssueTime() {
        BUILDER.setFinishedIssueTime(-5f);
        assertEquals(0f, BUILDER.getFinishedIssueTime());
        BUILDER.setFinishedIssueTime(15f);
        assertEquals(15f, BUILDER.getFinishedIssueTime());
    }
    
    public void testCompareTo() {
        assertTrue(MAINTASK.compareTo(MAINTASK) == 0);
        assertTrue(MAINTASK.compareTo(MAINTASK2) < 0);
        assertTrue(MAINTASK2.compareTo(MAINTASK) > 0);
    }
    
    public void testHashCode() {
        assertEquals(MAINTASK.hashCode(), MAINTASK.getBuilder().build().hashCode());
        assertNotSame(MAINTASK.hashCode(), MAINTASK2.hashCode());
    }
    
    public void testEquals() {
        assertTrue(MAINTASK.equals(MAINTASK));
        assertTrue(MAINTASK.getBuilder().build().equals(MAINTASK));
        assertFalse(MAINTASK2.equals(MAINTASK));
    }
}
