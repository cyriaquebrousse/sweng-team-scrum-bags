package ch.epfl.scrumtool.entity;

import java.io.Serializable;

import ch.epfl.scrumtool.R;

/**
 * Represents the priority of a task or an issue
 * 
 * @author Cyriaque Brousse
 */
public enum Priority implements Serializable {
    
    /*
     * The order of definition also specifies the natural 
     * order of the enum values, which means:
     * 
     * LOW < NORMAL < HIGH < URGENT
     */
    URGENT(R.color.darkred, "URGENT"),
    HIGH(R.color.Orange, "HIGH"),
    NORMAL(R.color.darkgreen, "NORMAL"),
    LOW(R.color.blue, "LOW");
    
    private int colorRef;
    private String value;
    
    private Priority(int colorRef, String stringValue) {
        this.colorRef = colorRef;
        this.value = stringValue;
    }
    
    /**
     * @return the color. It is <b>NOT</b> a color, but rather a R reference to one!
     */
    public int getColorRef() {
        return colorRef;
    }
    
    @Override
    public String toString() {
        return value;
    }

}
