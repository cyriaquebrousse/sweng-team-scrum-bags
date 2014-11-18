package ch.epfl.scrumtool.entity;

import java.io.Serializable;

import ch.epfl.scrumtool.R;

/**
 * @author ketsio
 */
public enum Status implements Serializable {

    /*
     * The order of definition also specifies the natural 
     * order of the enum values, which means:
     * 
     * READY_FOR_SPRINT < IN_SPRINT < READY_FOR_ESTIMATION < FINISHED
     */
    READY_FOR_SPRINT(R.color.Chartreuse, "Ready for sprint"),
    IN_SPRINT(R.color.Salmon, "In sprint"),
    READY_FOR_ESTIMATION(R.color.Lime, "Ready for estimation"),
    FINISHED(R.color.Cyan, "Finished");

    private int colorRef;
    private String stringValue;

    Status(int colorRef, String stringValue) {
        this.colorRef = colorRef;
        this.stringValue = stringValue;
    }

    /**
     * @return the color. It is <b>NOT</b> a color, but rather a R reference to
     *         one!
     */
    public int getColorRef() {
        return colorRef;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }
}
