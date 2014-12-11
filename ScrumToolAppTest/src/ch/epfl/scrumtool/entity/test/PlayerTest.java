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
    
    private final Player.Builder builder = new Player.Builder()
        .setKey(KEY)
        .setIsAdmin(ADMIN)
        .setIsInvited(INVITED)
        .setRole(ROLE)
        .setProject(PROJECT)
        .setUser(USER);
    
    private final Player player = builder.build();
    private final Player player2 = MockData.JOEY_DEV;
    
    public void testConstructor() {
        // It's impossible to have null value passed to constructor
        // all builder fields are initialized
        // all setter check for null
        
        // check invited and admin
        try {
            builder.setIsAdmin(true).setIsInvited(true).build();
            fail("expected an IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }
    }
    
    public void testGetKey() {
        assertEquals(KEY, player.getKey());
    }
    
    public void testGetAdminFlag() {
        assertTrue(player.isAdmin());
    }
    
    public void testGetInvitedFlag() {
        assertFalse(player.isInvited());
    }
    
    public void testGetUser() {
        assertEquals(USER, player.getUser());
    }
    
    public void testGetProject() {
        assertEquals(PROJECT, player.getProject());
    }
    
    public void testGetRole() {
        assertEquals(ROLE, player.getRole());
    }
    
    public void testGetBuilder() {
        Player.Builder builder = player.getBuilder();
        assertEquals(KEY, builder.getKey());
        assertTrue(builder.isAdmin());
        assertFalse(builder.isInvited());
        assertEquals(ROLE, builder.getRole());
        assertEquals(USER, builder.getUser());
        assertEquals(PROJECT, builder.getProject());
    }
    
    public void testBuilderSetGetKey() {
        builder.setKey(player2.getKey());
        builder.setKey(null);
        assertNotNull(builder.getKey());
        assertEquals(player2.getKey(), builder.getKey());
    }
    
    public void testBuilderSetGetRole() {
        builder.setRole(player2.getRole());
        builder.setRole(null);
        assertNotNull(builder.getRole());
        assertEquals(player2.getRole(), builder.getRole());
    }
    
    public void testBuilderSetGetAdmin() {
        builder.setIsAdmin(player2.isAdmin());
        assertEquals(player2.isAdmin(), builder.isAdmin());
    }
    
    public void testBuilderSetGetInvited() {
        builder.setIsInvited(player2.isInvited());
        assertEquals(player2.isInvited(), builder.isInvited());
    }
    
    public void testBuilderSetGeUser() {
        builder.setUser(player2.getUser());
        builder.setUser(null);
        assertNotNull(builder.getUser());
        assertEquals(player2.getUser(), builder.getUser());
    }
    
    public void testBuilderSetGetProject() {
        builder.setProject(null);
        assertNotNull(builder.getProject());
        assertEquals(PROJECT, builder.getProject());
    }
    
    public void testHashCode() {
        assertEquals(player.hashCode(), player.getBuilder().build().hashCode());
        assertNotSame(player.hashCode(), player2.hashCode());
    }
    
    public void testEquals() {
        assertTrue(player.equals(player));
        assertTrue(player.equals(player.getBuilder().build()));
        assertFalse(player.equals(player2));
    }
    
    public void testCompareTo() {
        assertTrue(player.compareTo(player) == 0);
        assertTrue(player.compareTo(null) == 1);
        assertTrue(player.compareTo(builder.setUser(USER2).build()) < 0);
        assertTrue(builder.setUser(USER2).build().compareTo(player) > 0);
    }
}
