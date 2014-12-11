package ch.epfl.scrumtool.entity.test;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.utils.test.MockData;

/**
 * Tests the Issue entity
 * 
 * @author vincent
 *
 */
public class IssueTest extends TestCase {
    private static final String KEY = "Key";
    private static final String NAME = "Issue";
    private static final String DESCRIPTION = "Description";
    private static final Status STATUS = Status.READY_FOR_ESTIMATION;
    private static final Priority PRIORITY = Priority.URGENT;
    private static final float ESTIMATION = 5f;
    private static final Player PLAYER = MockData.JOEY_DEV;
    private static final Sprint SPRINT = MockData.SPRINT1;
    
    private final Issue.Builder builder = new Issue.Builder()
        .setDescription(DESCRIPTION)
        .setKey(KEY)
        .setName(NAME)
        .setStatus(STATUS)
        .setPriority(PRIORITY)
        .setEstimatedTime(ESTIMATION)
        .setSprint(SPRINT)
        .setPlayer(PLAYER);
    private final Issue issue = builder.build();
    private final Issue issue2 = MockData.ISSUE3;

    @Test
    public void testConstructor() {
        // It's impossible to have null value passed to constructor
        // all builder fields are initialized
        // all setter check for null
        
        assertEquals(issue, builder.build());

        // check for estimation greater than 99,9999
        try {
            builder.setEstimatedTime(1000f).build();
            fail("expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
    }
    
    @Test
    public void testGetEstimatedTime() {
        assertTrue(ESTIMATION == issue.getEstimatedTime());
    }

    @Test
    public void testGetPlayer() {
        Issue i = builder.setPlayer(PLAYER).build();
        assertEquals(PLAYER, i.getPlayer());
    }
    
    @Test
    public void testGetSprint() {
        Issue i = builder.setSprint(SPRINT).build();
        assertEquals(SPRINT, i.getSprint());
    }
    
    @Test
    public void testGetBuilder() {
        Issue.Builder b = issue.getBuilder();
        assertEquals(KEY, b.getKey());
        assertEquals(NAME, b.getName());
        assertEquals(DESCRIPTION, b.getDescription());
        assertEquals(STATUS, b.getStatus());
        assertEquals(PRIORITY, b.getPriority());
        assertEquals(SPRINT, b.getSprint());
        assertEquals(PLAYER, b.getPlayer());
        assertTrue(ESTIMATION == b.getEstimatedTime());
    }
    
    @Test
    public void testBuilderSetGetEstimatedTime(){
        builder.setEstimatedTime(99f);
        builder.setEstimatedTime(-99f);
        assertTrue(99f == builder.getEstimatedTime());
    }
    
    @Test
    public void testBuilderSetGetPlayer(){
        Player p = MockData.VINCENT_ADMIN;
        builder.setPlayer(p);
        assertEquals(p, builder.getPlayer());
    }
    
    @Test
    public void testBuilderSetGetSprint(){
        Sprint s = MockData.SPRINT2;
        builder.setSprint(s);
        assertEquals(s, builder.getSprint());
    }

    @Test
    public void testHashCode() {
        assertEquals(issue.hashCode(), builder.build().hashCode());
        assertNotSame(issue.hashCode(), issue2.hashCode());
    }

    @Test
    public void testEqualsObject() {
        assertTrue(issue.equals(builder.build()));
        assertTrue(issue.equals(issue));
        assertFalse(issue.equals(issue2));
    }

    @Test
    public void testCompareTo() {
        assertTrue(issue.compareTo(builder.build()) == 0);
        assertTrue(issue.compareTo(issue2) < 0);
        assertTrue(issue2.compareTo(issue) > 0);
    }
    
    @Test
    public void testSimulateNewStatusForEstimationAndSprint() {
        Issue i = builder.setEstimatedTime(0.f).setSprint(null).build();
        assertEquals(Status.READY_FOR_ESTIMATION, i.simulateNewStatusForEstimationAndSprint());
        i = builder.setEstimatedTime(0.f).setSprint(SPRINT).build();
        assertEquals(Status.READY_FOR_ESTIMATION, i.simulateNewStatusForEstimationAndSprint());
        i = builder.setEstimatedTime(10.f).setSprint(null).build();
        assertEquals(Status.READY_FOR_SPRINT, i.simulateNewStatusForEstimationAndSprint());
        i = builder.setEstimatedTime(10.f).setSprint(SPRINT).build();
        assertEquals(Status.IN_SPRINT, i.simulateNewStatusForEstimationAndSprint());
        i = builder.setStatus(Status.FINISHED).build();
        assertEquals(Status.FINISHED, i.simulateNewStatusForEstimationAndSprint());
    }
}
