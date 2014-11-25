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
    PRODUCT_OWNER("Product Owner", true),
    STAKEHOLDER("Stakeholder", false),
    SCRUM_MASTER("Scrum Master", true),
    DEVELOPER("Developer", true),
    INVITED("Invited", true);

    private final String stringValue;
    private final boolean canAccessIssues;

    /**
     * @param stringValue
     * @param accessIssues
     */
    Role(String stringValue, boolean accessIssues) {
        this.stringValue = stringValue;
        this.canAccessIssues = accessIssues;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }

    /**
     * @return true if the user has the rights to see issues, false otherwise
     */
    public boolean canAccessIssues() {
        return canAccessIssues;
    }

}
