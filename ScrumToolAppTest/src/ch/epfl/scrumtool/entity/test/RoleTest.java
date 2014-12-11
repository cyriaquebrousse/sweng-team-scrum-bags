package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.entity.Role;
import junit.framework.TestCase;

/**
 * Test for Role enum
 * 
 * @author vincent
 *
 */
public class RoleTest extends TestCase {
    
    private final static Role DEVELOPER = Role.DEVELOPER;
    private final static Role PRODUCTOWENER = Role.PRODUCT_OWNER;
    private final static Role SCRUMMASTER = Role.SCRUM_MASTER;
    private final static Role STAKEHOLDER = Role.STAKEHOLDER;

    public void testToString() {
        assertEquals("Developer", DEVELOPER.toString());
        assertEquals("Product Owner", PRODUCTOWENER.toString());
        assertEquals("Scrum Master", SCRUMMASTER.toString());
        assertEquals("Stakeholder", STAKEHOLDER.toString());
    }
    
    public void testValueOf() {
        assertEquals(DEVELOPER, Role.valueOf("DEVELOPER"));
        assertEquals(PRODUCTOWENER, Role.valueOf("PRODUCT_OWNER"));
        assertEquals(SCRUMMASTER, Role.valueOf("SCRUM_MASTER"));
        assertEquals(STAKEHOLDER, Role.valueOf("STAKEHOLDER"));
    }
}
