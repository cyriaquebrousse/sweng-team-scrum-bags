package ch.epfl.scrumtool.entity;

import java.io.Serializable;

import ch.epfl.scrumtool.R;

/**
 * @author Cyriaque Brousse
 */
public enum Priority implements Serializable {
    
    LOW(R.color.blue, "LOW"),
    NORMAL(R.color.darkgreen, "NORMAL"),
    HIGH(R.color.Orange, "HIGH"),
    URGENT(R.color.darkred, "URGENT");
    
    private int colorRef;
    private String stringValue;
    
    private Priority(int colorRef, String stringValue) {
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
        return stringValue;
    }

}
