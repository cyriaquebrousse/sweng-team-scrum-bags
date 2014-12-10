package ch.epfl.scrumtool.entity.test;

import java.util.Calendar;

import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import junit.framework.TestCase;

/**
 * Tests the Sprint entity
 * 
 * @author vincent
 *
 */
public class SprintTest extends TestCase {
    
    private static final int MAGIC2 = 20000;
    private static final int MAGIC1 = 500;
    private static final String KEY = "key";
    private static final String TITLE = "title";
    private static final long DEADLINE = Calendar.getInstance().getTimeInMillis()-MAGIC2;
    
    private static final Sprint.Builder BUILDER = new Sprint.Builder()
        .setDeadline(DEADLINE)
        .setTitle(TITLE)
        .setKey(KEY);
    private static final Sprint SPRINT = BUILDER.build();
    private static final Sprint SPRINT2 = MockData.SPRINT2;
            
    public void testConstructor() {
        // It's impossible to have null value passed to constructor
        // all builder fields are initialized
        // all setter check for null
        
        // check for empty name
        try {
            new Sprint.Builder().build();
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    public void testGetTitle() {
        assertEquals(TITLE, SPRINT.getTitle());
    }
    
    public void testGetKey() {
        assertEquals(KEY, SPRINT.getKey());
    }
    
    public void testGetDeadLine() {
        assertEquals(DEADLINE, SPRINT.getDeadline());
    }
    
    public void testGetBuilder() {
        assertEquals(DEADLINE, SPRINT.getBuilder().getDeadline());
        assertEquals(TITLE, SPRINT.getBuilder().getTitle());
        assertEquals(KEY, SPRINT.getBuilder().getKey());
    }
    
    public void testBuilderSetGetKey() {
        BUILDER.setKey(SPRINT2.getKey());
        BUILDER.setKey(null);
        assertEquals(SPRINT2.getKey(), BUILDER.getKey());
    }
    
    public void testBuilderSetGetTitle() {
        BUILDER.setTitle(SPRINT2.getTitle());
        BUILDER.setTitle(null);
        assertEquals(SPRINT2.getTitle(), BUILDER.getTitle());
    }
    
    public void testBuilderSetGetDeadLine() {
        BUILDER.setDeadline(SPRINT2.getDeadline());
        assertEquals(SPRINT2.getDeadline(), BUILDER.getDeadline());
    }
    
    public void testBuilderBuild() {
        //Redundant with testGet<field> tests
    }
    
    public void testEqualsRef() {
        Sprint sprint = SPRINT;
        assertEquals(sprint, SPRINT);
    }
    
    public void testEqualsStruct() {
        Sprint s1 = BUILDER.setDeadline(MAGIC1).setKey(KEY).build();
        Sprint s2 = BUILDER.setKey(KEY).build();
        assertNotSame(s1, s2);
        s2 = BUILDER.setDeadline(MAGIC1).setKey(KEY).build();
        assertEquals(s1, s2);
    }
    
    public void testCopyConstructor() {
        assertEquals(SPRINT, SPRINT.getBuilder().build());
    }
    
    public void testHashCode() {
        assertEquals(SPRINT.hashCode(), SPRINT.getBuilder().build().hashCode());
        assertNotSame(SPRINT, SPRINT2.hashCode());
    }
    
    public void testCompareToSameDeadline() {
        assertTrue(SPRINT.compareTo(SPRINT.getBuilder().build()) == 0);
    }
    
    public void testCompareToBefore() {
        assertTrue(SPRINT.compareTo(SPRINT2) < 0);
    }
    
    public void testCompareToAfter() {
        assertTrue(SPRINT2.compareTo(SPRINT) > 0);
    }
}
