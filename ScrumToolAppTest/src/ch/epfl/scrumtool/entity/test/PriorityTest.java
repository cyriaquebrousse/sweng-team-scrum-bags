package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Priority;
import junit.framework.TestCase;

/**
 * Test for Priority enum
 * 
 * @author vincent
 *
 */
public class PriorityTest extends TestCase {
    
    private final static Priority LOW = Priority.LOW;
    private final static Priority NORMAL = Priority.NORMAL;
    private final static Priority HIGH = Priority.HIGH;
    private final static Priority URGENT = Priority.URGENT;

    public void testGetColorRef() {
        assertEquals(R.color.blue, LOW.getColorRef());
        assertEquals(R.color.darkgreen, NORMAL.getColorRef());
        assertEquals(R.color.Orange, HIGH.getColorRef());
        assertEquals(R.color.darkred, URGENT.getColorRef());
    }

    public void testToString() {
        assertEquals("LOW", LOW.toString());
        assertEquals("NORMAL", NORMAL.toString());
        assertEquals("HIGH", HIGH.toString());
        assertEquals("URGENT", URGENT.toString());
    }
    
    public void testValueOf() {
        assertEquals(LOW, Priority.valueOf("LOW"));
        assertEquals(NORMAL, Priority.valueOf("NORMAL"));
        assertEquals(HIGH, Priority.valueOf("HIGH"));
        assertEquals(URGENT, Priority.valueOf("URGENT"));
    }

}
