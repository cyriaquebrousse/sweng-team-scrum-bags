package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Priority;
import junit.framework.TestCase;

public class PriorityTest extends TestCase {
    
    private final static Priority low = Priority.LOW;
    private final static Priority normal = Priority.NORMAL;
    private final static Priority high = Priority.HIGH;
    private final static Priority urgent = Priority.URGENT;

    public void testGetColorRef() {
        assertEquals(R.color.blue, low.getColorRef());
        assertEquals(R.color.darkgreen, normal.getColorRef());
        assertEquals(R.color.Orange, high.getColorRef());
        assertEquals(R.color.darkred, urgent.getColorRef());
    }

    public void testToString() {
        assertEquals("LOW", low.toString());
        assertEquals("NORMAL", normal.toString());
        assertEquals("HIGH", high.toString());
        assertEquals("URGENT", urgent.toString());
    }

}
