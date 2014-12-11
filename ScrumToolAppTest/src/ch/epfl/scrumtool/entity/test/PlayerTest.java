package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import junit.framework.TestCase;

/**
 * Tests the Player entity
 * 
 * @author vincent
 *
 */
public class PlayerTest extends TestCase {
    private final static User USER = MockData.VINCENT;
    private final static User USER2 = MockData.JOEY;
    private final static Project PROJECT = MockData.MURCS;
    private final static String KEY = "Key";
    private final static Boolean ADMIN = true;
    private final static Boolean INVITED = false;
    private final static Role ROLE = Role.PRODUCT_OWNER;
    
    private final static Player.Builder BUILDER = new Player.Builder()
        .setKey(KEY)
        .setIsAdmin(ADMIN)
        .setIsInvited(INVITED)
        .setRole(ROLE)
        .setProject(PROJECT)
        .setUser(USER);
    
    private final static Player PLAYER = BUILDER.build();
    private final static Player PLAYER2 = MockData.JOEY_DEV;
    
    public void testConstructor() {
     // It's impossible to have null value passed to constructor
        // all builder fields are initialized
        // all setter check for null
        
        // check invited and admin
        try {
            BUILDER.setIsAdmin(true).setIsInvited(true).build();
            fail("expected an IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }
    }
    
    public void testGetKey() {
        assertEquals(KEY, PLAYER.getKey());
    }
    
    public void testGetAdminFlag() {
        assertTrue(PLAYER.isAdmin());
    }
    
    public void testGetInvitedFlag() {
        assertFalse(PLAYER.isInvited());
    }
    
    public void testGetUser() {
        assertEquals(USER, PLAYER.getUser());
    }
    
    public void testGetProject() {
        assertEquals(PROJECT, PLAYER.getProject());
    }
    
    public void testGetRole() {
        assertEquals(ROLE, PLAYER.getRole());
    }
    
    public void testGetBuilder() {
        Player.Builder builder = PLAYER.getBuilder();
        assertEquals(KEY, builder.getKey());
        assertTrue(builder.isAdmin());
        assertFalse(builder.isInvited());
        assertEquals(ROLE, builder.getRole());
        assertEquals(USER, builder.getUser());
        assertEquals(PROJECT, builder.getProject());
    }
    
    public void testBuilderSetGetKey() {
        BUILDER.setKey(PLAYER2.getKey());
        BUILDER.setKey(null);
        assertNotNull(BUILDER.getKey());
        assertEquals(PLAYER2.getKey(), BUILDER.getKey());
    }
    
    public void testBuilderSetGetRole() {
        BUILDER.setRole(PLAYER2.getRole());
        BUILDER.setRole(null);
        assertNotNull(BUILDER.getRole());
        assertEquals(PLAYER2.getRole(), BUILDER.getRole());
    }
    
    public void testBuilderSetGetAdmin() {
        BUILDER.setIsAdmin(PLAYER2.isAdmin());
        assertEquals(PLAYER2.isAdmin(), BUILDER.isAdmin());
    }
    
    public void testBuilderSetGetInvited() {
        BUILDER.setIsInvited(PLAYER2.isInvited());
        assertEquals(PLAYER2.isInvited(), BUILDER.isInvited());
    }
    
    public void testBuilderSetGeUser() {
        BUILDER.setUser(PLAYER2.getUser());
        BUILDER.setUser(null);
        assertNotNull(BUILDER.getUser());
        assertEquals(PLAYER2.getUser(), BUILDER.getUser());
    }
    
    public void testBuilderSetGetProject() {
        BUILDER.setProject(null);
        assertNotNull(BUILDER.getProject());
        assertEquals(PROJECT, BUILDER.getProject());
    }
    
    public void testHashCode() {
        assertEquals(PLAYER.hashCode(), PLAYER.getBuilder().build().hashCode());
        assertNotSame(PLAYER.hashCode(), PLAYER2.hashCode());
    }
    
    public void testEquals() {
        assertTrue(PLAYER.equals(PLAYER));
        assertTrue(PLAYER.equals(PLAYER.getBuilder().build()));
        assertFalse(PLAYER.equals(PLAYER2));
    }
    
    public void testCompareTo() {
        assertTrue(PLAYER.compareTo(PLAYER) == 0);
        assertTrue(PLAYER.compareTo(null) == 1);
        assertTrue(PLAYER.compareTo(BUILDER.setUser(USER2).build()) < 0);
        assertTrue(BUILDER.setUser(USER2).build().compareTo(PLAYER) > 0);
    }
}
