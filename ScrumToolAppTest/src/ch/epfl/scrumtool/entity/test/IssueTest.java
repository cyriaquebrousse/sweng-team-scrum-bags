package ch.epfl.scrumtool.entity.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Status;

public class IssueTest {
    private static final String ISSUE_KEY_1 = "Key 1";
    private static final String ISSUE_NAME_1 = "Issue 1";
    private static final String ISSUE_DESCRIPTION_1 = "Description 1";
    private static final Status ISSUE_STATUS_1 = Status.FINISHED;
    private static final String ISSUE_KEY_2 = "Key 2";
    private static final String ISSUE_NAME_2 = "Issue 2";
    private static final String ISSUE_DESCRIPTION_2 = "Description 2";
    private static final Status ISSUE_STATUS_2 = Status.IN_SPRINT;

    private static final Issue ISSUE_1 = new Issue.Builder()
        .setDescription(ISSUE_DESCRIPTION_1)
        .setKey(ISSUE_KEY_1)
        .setName(ISSUE_NAME_1)
        .setStatus(ISSUE_STATUS_1).build();

    private static final Issue ISSUE_2 = new Issue.Builder()
        .setDescription(ISSUE_DESCRIPTION_2)
        .setKey(ISSUE_KEY_2)
        .setName(ISSUE_NAME_2)
        .setStatus(ISSUE_STATUS_2).build();
    
    private static final Issue ISSUE_1_CLONE = new Issue.Builder(ISSUE_1).build();

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
        assertEquals("ISSUE_1 should be equal to ISSUE_1_CLONE", ISSUE_1.equals(ISSUE_1_CLONE), true);
        assertEquals("ISSUE_1 should not be equal to ISSUE_2", ISSUE_1.equals(ISSUE_2), false);
    }

    @Test
    public void testGetPlayer() {
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
