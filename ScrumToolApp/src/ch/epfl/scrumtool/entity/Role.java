package ch.epfl.scrumtool.entity;

/**
 * @author ketsio
 */
public enum Role {
    PRODUCT_OWNER("Product Owner", true), STAKEHOLDER("Stakeholder", false), SCRUM_MASTER(
            "Scrum Master", true), DEVELOPER("Developer", true);

    private final String mStringValue;
    private final boolean mCanAccessIssues;

    Role(String stringValue, boolean accessIssues) {
        this.mStringValue = stringValue;
        this.mCanAccessIssues = accessIssues;
    }

    @Override
    public String toString() {
        return this.mStringValue;
    }

    /**
     * @return true if it has the rights to see issues, false else
     */
    public boolean canAccessIssues() {
        return mCanAccessIssues;
    }

}
