package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.R;
import junit.framework.TestCase;

public class StatusTest extends TestCase {
    
    private final static Status readyForEstimation = Status.READY_FOR_ESTIMATION;
    private final static Status readyForSprint = Status.READY_FOR_SPRINT;
    private final static Status inSprint = Status.IN_SPRINT;
    private final static Status finished = Status.FINISHED;

    public void testGetColorRef() {
        assertEquals(R.color.Lime, readyForEstimation.getColorRef());
        assertEquals(R.color.Chartreuse, readyForSprint.getColorRef());
        assertEquals(R.color.Salmon, inSprint.getColorRef());
        assertEquals(R.color.Cyan, finished.getColorRef());
    }

    public void testToString() {
        assertEquals("Ready for estimation", readyForEstimation.toString());
        assertEquals("Ready for sprint", readyForSprint.toString());
        assertEquals("In sprint", inSprint.toString());
        assertEquals("Finished", finished.toString());
    }

}
