package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    private static final User USER_1 = new User.Builder()
            .setCompanyName("apple")
            .setDateOfBirth(0)
            .setEmail("email@email.com")
            .setGender(Gender.UNKNOWN)
            .setJobTitle("CEO")
            .setLastName("Smith")
            .setName("Steve").build();
    private final static String KEY_1 = "key";
    private final static Role ROLE_1 = Role.DEVELOPER;
    private final static Player player = new Player.Builder()
        .setIsAdmin(true)
        .setIsInvited(false)
        .setKey(KEY_1)
        .setRole(ROLE_1)
        .setUser(USER_1)
        .build();

    public void testGetUser() {
        User user = player.getUser();
        assertEquals(USER_1, user);
    }

    public void testGetRole() {
        Role role = player.getRole();
        assertEquals(ROLE_1, role);
    }

    public void testGetKey() {
        String key = player.getKey();
        assertEquals(KEY_1, key);
    }

    public void testIsAdmin() {
        assertTrue(player.isAdmin());
    }

    public void testIsInvited() {
        assertFalse(player.isInvited());
    }

    public void testEqualsObject() {
        Player player2 = player;
        assertTrue(player2.equals(player));
    }
    
    public void testTODO() {
        //TODO test null parameters and other stuff
        fail("Not implemented yet");
    }
}
