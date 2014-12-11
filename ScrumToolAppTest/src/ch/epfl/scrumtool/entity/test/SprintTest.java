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
    
    private final Sprint.Builder builder = new Sprint.Builder()
        .setDeadline(DEADLINE)
        .setTitle(TITLE)
        .setKey(KEY);
    private final Sprint sprint = builder.build();
    private final Sprint sprint2 = MockData.SPRINT2;
            
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
        assertEquals(TITLE, sprint.getTitle());
    }
    
    public void testGetKey() {
        assertEquals(KEY, sprint.getKey());
    }
    
    public void testGetDeadLine() {
        assertEquals(DEADLINE, sprint.getDeadline());
    }
    
    public void testGetBuilder() {
        assertEquals(DEADLINE, sprint.getBuilder().getDeadline());
        assertEquals(TITLE, sprint.getBuilder().getTitle());
        assertEquals(KEY, sprint.getBuilder().getKey());
    }
    
    public void testBuilderSetGetKey() {
        builder.setKey(sprint2.getKey());
        builder.setKey(null);
        assertNotNull(builder.getKey());
        assertEquals(sprint2.getKey(), builder.getKey());
    }
    
    public void testBuilderSetGetTitle() {
        builder.setTitle(sprint2.getTitle());
        builder.setTitle(null);
        assertNotNull(builder.getTitle());
        assertEquals(sprint2.getTitle(), builder.getTitle());
    }
    
    public void testBuilderSetGetDeadLine() {
        builder.setDeadline(sprint2.getDeadline());
        assertEquals(sprint2.getDeadline(), builder.getDeadline());
    }
    
    public void testBuilderBuild() {
        //Redundant with testGet<field> tests
    }
    
    public void testEqualsRef() {
        Sprint s1 = sprint;
        assertEquals(s1, sprint);
    }
    
    public void testEqualsStruct() {
        Sprint s1 = builder.setDeadline(MAGIC1).setKey(KEY).build();
        Sprint s2 = builder.setKey(KEY).build();
        assertNotSame(s1, s2);
        s2 = builder.setDeadline(MAGIC1).setKey(KEY).build();
        assertEquals(s1, s2);
    }
    
    public void testCopyConstructor() {
        assertEquals(sprint, sprint.getBuilder().build());
    }
    
    public void testHashCode() {
        assertEquals(sprint.hashCode(), sprint.getBuilder().build().hashCode());
        assertNotSame(sprint, sprint2.hashCode());
    }
    
    public void testCompareToSameDeadline() {
        assertTrue(sprint.compareTo(sprint.getBuilder().build()) == 0);
    }
    
    public void testCompareToBefore() {
        assertTrue(sprint.compareTo(sprint2) < 0);
    }
    
    public void testCompareToAfter() {
        assertTrue(sprint2.compareTo(sprint) > 0);
    }
}
