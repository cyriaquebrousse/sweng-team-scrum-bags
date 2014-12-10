package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.entity.Role;
import junit.framework.TestCase;

public class RoleTest extends TestCase {
    
    private final static Role developer = Role.DEVELOPER;
    private final static Role productOwner = Role.PRODUCT_OWNER;
    private final static Role scrumMaster = Role.SCRUM_MASTER;
    private final static Role stakeHolder = Role.STAKEHOLDER;

    public void testToString() {
        assertEquals("Developer", developer.toString());
        assertEquals("Product Owner", productOwner.toString());
        assertEquals("Scrum Master", scrumMaster.toString());
        assertEquals("Stakeholder", stakeHolder.toString());
    }
}
