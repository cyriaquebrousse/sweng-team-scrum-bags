package ch.epfl.scrumtool.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Cyriaque Brousse
 */
public class SprintTest extends TestCase {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidDateShouldThrow() {
        Set<Issue> issues = new HashSet<Issue>();
        try {
            new Sprint(-1, issues);
        } catch (IllegalArgumentException e) {}
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullIssueSetShouldThrow() {
        Date d = new Date();
        try {
            new Sprint(d, null);
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void testConstructorWithValidArgsDoesNotThrow() {
        Date d = new Date();
        Set<Issue> issues = new HashSet<Issue>();
        new Sprint(d, issues);
    }
    
    @Test
    public void testGetDateMillisShouldYieldSpecifiedDateMillis() {
        Date d = new Date();
        Set<Issue> issues = new HashSet<Issue>();
        Sprint s = new Sprint(d, issues);
        assertEquals(d.getTime(), s.getDeadline());
    }
    
    @Test
    public void testCopyConstructor() {
        Sprint s1 = newSprint();
        Sprint s2 = new Sprint(s1);
        
        assertEquals(s1, s2);
    }
    
    @Test
    public void testModifySprintCreatedByCopyDoesNotAffectTheOther() {
        Sprint s1 = newSprint();
        s1.addIssue(Entity.ISSUE_D2);
        
        Sprint s2 = new Sprint(s1);
        s2.addIssue(Entity.ISSUE_A1);
        
        assertFalse(s1.equals(s2));
    }

    @Test
    public void testEquals() {
        Date d = new Date();
        List<Issue> issuesShuffled = new ArrayList<Issue>(Entity.ALL_A_ISSUES);
        Collections.shuffle(issuesShuffled);
        
        Sprint s1 = new Sprint(d, new HashSet<Issue>(Entity.ALL_A_ISSUES));
        Sprint s2 = new Sprint(d, new HashSet<Issue>(issuesShuffled));
        
        assertEquals(s1, s2);
    }
    
    @Test
    public void testTwoDifferentDeadlinesWithSameIssuesShouldFailEquality() {
        Sprint s1 = newSprint();
        s1.addIssue(Entity.ISSUE_A1);
        
        waitAMoment();
        Sprint s2 = newSprint();
        s2.addIssue(Entity.ISSUE_A1);
        
        assertFalse(s1.equals(s2));
    }
    
    @Test
    public void testAddIssue() {
        Sprint s = newSprint();
        s.addIssue(Entity.ISSUE_B1);
        assertTrue(s.getIssues().contains(Entity.ISSUE_B1));
    }
    
    @Test
    public void testAddTwiceSameIssueShouldNotDuplicate() {
        Sprint s = newSprint();
        s.addIssue(Entity.ISSUE_C1);
        s.addIssue(Entity.ISSUE_C1);
        assertTrue(s.getIssues().size() == 1);
    }
    
    @Test
    public void testRemoveIssue() {
        Sprint s = newSprint();
        for (Issue i : Entity.ALL_A_ISSUES) {
            s.addIssue(i);
        }
        s.removeIssue(Entity.ISSUE_A1);
        s.removeIssue(Entity.ISSUE_A2);
        
        assertTrue(s.getIssues().contains(Entity.ISSUE_A3) && s.getIssues().size() == 1);
    }
    
    @Test
    public void testSetDeadline() {
        Sprint s = newSprint();
        long dateBefore = s.getDeadline();
        
        waitAMoment();
        s.setDeadline(new Date());
        
        assertTrue(dateBefore < s.getDeadline());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetWrongDeadlineThrows() {
        Sprint s = newSprint();
        try {
            s.setDeadline(-1);
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void testAddIssueToReturnedValueFromGetterDoesNothing() {
        Sprint s = newSprint();
        s.addIssue(Entity.ISSUE_A1);
        
        Set<Issue> getterValue = s.getIssues();
        getterValue.add(Entity.ISSUE_A2);
        
        assertTrue(s.getIssues().contains(Entity.ISSUE_A1) && s.getIssues().size() == 1);
    }
    
    /**
     * @return a newly created Sprint, with current date and empty set of issues
     *         (but not null)
     */
    private Sprint newSprint() {
        return new Sprint(new Date(), new HashSet<Issue>());
    }
    
    /**
     * Waits a few milliseconds
     * 
     * @return false if InterruptedException, true otherwise
     **/
    private void waitAMoment() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
