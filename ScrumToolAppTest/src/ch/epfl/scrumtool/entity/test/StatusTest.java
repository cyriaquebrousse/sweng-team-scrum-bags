package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Status;
import junit.framework.TestCase;

/**
 * Test for the Status enum
 * 
 * @author vincent
 *
 */
public class StatusTest extends TestCase {
    
    private final static Status READY_EST = Status.READY_FOR_ESTIMATION;
    private final static Status READY_SP = Status.READY_FOR_SPRINT;
    private final static Status IN_SPRINT = Status.IN_SPRINT;
    private final static Status FINISHED = Status.FINISHED;

    public void testGetColorRef() {
        assertEquals(android.R.color.holo_orange_light, READY_EST.getColorRef());
        assertEquals(R.color.shadeD, READY_SP.getColorRef());
        assertEquals(android.R.color.holo_blue_dark, IN_SPRINT.getColorRef());
        assertEquals(R.color.DarkGreen, FINISHED.getColorRef());
    }

    public void testToString() {
        assertEquals("Ready for estimation", READY_EST.toString());
        assertEquals("Ready for sprint", READY_SP.toString());
        assertEquals("In sprint", IN_SPRINT.toString());
        assertEquals("Finished", FINISHED.toString());
    }
    
    public void testValueOf() {
        assertEquals(READY_EST, Status.valueOf("READY_FOR_ESTIMATION"));
        assertEquals(READY_SP, Status.valueOf("READY_FOR_SPRINT"));
        assertEquals(IN_SPRINT, Status.valueOf("IN_SPRINT"));
        assertEquals(FINISHED, Status.valueOf("FINISHED"));
    }

}
