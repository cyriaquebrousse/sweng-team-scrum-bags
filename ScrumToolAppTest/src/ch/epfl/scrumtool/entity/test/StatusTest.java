package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Status;
import junit.framework.TestCase;

public class StatusTest extends TestCase {
    
    private final static Status readyForEstimation = Status.READY_FOR_ESTIMATION;
    private final static Status readyForSprint = Status.READY_FOR_SPRINT;
    private final static Status inSprint = Status.IN_SPRINT;
    private final static Status finished = Status.FINISHED;

    public void testGetColorRef() {
        assertEquals(android.R.color.holo_orange_light, readyForEstimation.getColorRef());
        assertEquals(R.color.shadeD, readyForSprint.getColorRef());
        assertEquals(android.R.color.holo_blue_dark, inSprint.getColorRef());
        assertEquals(R.color.DarkGreen, finished.getColorRef());
    }

    public void testToString() {
        assertEquals("Ready for estimation", readyForEstimation.toString());
        assertEquals("Ready for sprint", readyForSprint.toString());
        assertEquals("In sprint", inSprint.toString());
        assertEquals("Finished", finished.toString());
    }

}
