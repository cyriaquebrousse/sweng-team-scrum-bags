package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.R;

/**
 * @author ketsio
 */
public enum Status {
    READY_FOR_SPRINT(R.color.BlanchedAlmond, "Ready for sprint"),
    IN_SPRINT(R.color.AliceBlue, "In sprint"),
    READY_FOR_ESTIMATION(R.color.Black, "Ready for estimation"),
    FINISHED(R.color.Pink, "Finished");

    private int colorRef;
    private String stringValue;

    Status(int colorRef, String stringValue) {
        this.colorRef = colorRef;
        this.stringValue = stringValue;
    }

    /**
     * @return the color. It is <b>NOT</b> a color, but rather a R reference to one!
     */
    public int getColorRef() {
        return colorRef;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }
}
