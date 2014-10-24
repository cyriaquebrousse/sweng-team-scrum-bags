package ch.epfl.scrumtool.entity;

/**
 * @author ketsio
 */
public enum Role {
    PRODUCT_OWNER("Product Owner"), 
    STAKEHOLDER("Stakeholder"), 
    SCRUM_MASTER("Scrum Master"), 
    DEVELOPER("Developer");
    
    private final String stringValue;
    
    Role(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }
}
