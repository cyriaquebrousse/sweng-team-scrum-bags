package ch.epfl.scrumtool.entity;

/**
 * Describes a role a User can have in a project
 * 
 * @author ketsio
 */
public enum Role {
    /*
     * The order of definition also specifies the natural 
     * order of the enum values, which means:
     * 
     * PRODUCT_OWNER < STAKEHOLDER < ... < INVITED
     */
    PRODUCT_OWNER("Product Owner"),
    STAKEHOLDER("Stakeholder"),
    SCRUM_MASTER("Scrum Master"),
    DEVELOPER("Developer");

    private final String stringValue;

    /**
     * @param stringValue
     * @param accessIssues
     */
    Role(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }
}