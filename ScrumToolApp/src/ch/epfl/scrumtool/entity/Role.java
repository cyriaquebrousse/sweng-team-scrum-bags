package ch.epfl.scrumtool.entity;

/**
 * @author ketsio
 */
public enum Role {
    
    PRODUCT_OWNER("Product Owner", true),
    STAKEHOLDER("Stakeholder", false),
    SCRUM_MASTER("Scrum Master", true),
    DEVELOPER("Developer", true);

    private final String stringValue;
    private final boolean canAccessIssues;

    Role(String stringValue, boolean accessIssues) {
        this.stringValue = stringValue;
        this.canAccessIssues = accessIssues;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }

    /**
     * @return true if it has the rights to see issues, false else
     */
    public boolean canAccessIssues() {
        return canAccessIssues;
    }

}
