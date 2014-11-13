package ch.epfl.scrumtool.entity;

/**
 * Describes a role a User can have in a project
 * 
 * @author ketsio
 */
public enum Role {
    
    PRODUCT_OWNER("Product Owner", true),
    STAKEHOLDER("Stakeholder", false),
    SCRUM_MASTER("Scrum Master", true),
    DEVELOPER("Developer", true),
    INVITED("Invited", true);

    private final String value;
    private final boolean canAccessIssues;

    /**
     * @param stringValue
     * @param accessIssues
     */
    Role(String stringValue, boolean accessIssues) {
        this.value = stringValue;
        this.canAccessIssues = accessIssues;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * @return true if it has the rights to see issues, false else
     */
    public boolean canAccessIssues() {
        return canAccessIssues;
    }

}
