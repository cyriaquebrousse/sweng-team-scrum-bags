package ch.epfl.scrumtool.entity;

import java.io.Serializable;

import ch.epfl.scrumtool.R;

/**
 * Reprensents a Priority of a Maintask or an Issue
 * 
 * @author Cyriaque Brousse
 */
public enum Priority implements Serializable {
    
    LOW(R.color.blue, "LOW"),
    NORMAL(R.color.darkgreen, "NORMAL"),
    HIGH(R.color.Orange, "HIGH"),
    URGENT(R.color.darkred, "URGENT");
    
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
