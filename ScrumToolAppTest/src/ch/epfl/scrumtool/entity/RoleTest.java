package ch.epfl.scrumtool.entity;

import junit.framework.TestCase;

public class RoleTest extends TestCase {
    
    private final static Role developer = Role.DEVELOPER;
    private final static Role invited = Role.STAKEHOLDER;
    private final static Role productOwner = Role.PRODUCT_OWNER;
    private final static Role scrumMaster = Role.SCRUM_MASTER;
    private final static Role stakeHolder = Role.STAKEHOLDER;


    public void testToString() {
        assertEquals("Developer", developer.toString());
        assertEquals("Invited", invited.toString());
        assertEquals("Product Owner", productOwner.toString());
        assertEquals("Scrum Master", scrumMaster.toString());
        assertEquals("Stakeholder", stakeHolder.toString());
    }

    public void testCanAccessIssues() {
        assertTrue(developer.canAccessIssues());
        assertTrue(invited.canAccessIssues());
        assertTrue(productOwner.canAccessIssues());
        assertTrue(scrumMaster.canAccessIssues());
        assertFalse(stakeHolder.canAccessIssues());

    }

}
