package ch.epfl.scrumtool.entity;


/**
 * @author Cyriaque Brousse
 */
public enum Priority {
    LOW("LOW"),
    NORMAL("NORMAL"),
    HIGH("HIGH"),
    URGENT("URGENT");
    
    private String stringValue;
    
    private Priority(String stringValue) {
        this.stringValue = stringValue;
    }
    
    @Override
    public String toString() {
        return stringValue;
    }
}
