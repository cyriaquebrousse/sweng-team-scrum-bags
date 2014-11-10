/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Vincent
 * 
 */
public class IssueTest extends TestCase {

    public IssueTest() {
    }

    /**
     * Test method for
     * {@link ch.epfl.scrumtool.entity.Issue#equals(java.lang.Object)} .
     */
    @Test
    public void testEquals() {
        Issue issue1 = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT,
                120, new Player(10, new User("username1", "email", "name", 100,
                        new HashSet<Project>()), Role.DEVELOPER));
        Issue issue2 = issue1;
        assertEquals("issue1 should be equal to issue2", issue1.equals(issue2),
                true);
        issue2 = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT, 120,
                new Player(10, new User("username1", "email", "name", 100,
                        new HashSet<Project>()), Role.DEVELOPER));
        assertEquals("issue1 should be equal to issue2", issue1.equals(issue2),
                true);
        issue2.setDescription("description2");
        assertEquals("issue1 should not be equal to issue2",
                issue1.equals(issue2), false);
        issue1.setDescription("description2");
        assertEquals("issue1 should be equal to issue2", issue1.equals(issue2),
                true);
    }

    /**
     * Test method for
     * {@link ch.epfl.scrumtool.entity.Issue#Issue(long, java.lang.String, java.lang.String, ch.epfl.scrumtool.entity.Status, float, ch.epfl.scrumtool.entity.Player)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testIssueLongStringStringStatusFloatPlayer() {
        Player player1 = new Player(10, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Issue issue1 = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT,
                0.5f, player1);
        assertEquals("Id shoulb be 1", issue1.getId(), 1);
        assertEquals("Name should be issue1", issue1.getName(), "issue1");
        assertEquals("Description should be 'description1'",
                issue1.getDescription(), "descritpion1");
        assertEquals("Status should be IN_SPRINT", issue1.getStatus(),
                Status.IN_SPRINT);
        assertEquals("Token should be 0.5f", issue1.getEstimatedTime(), 0.5f);
        assertEquals("Player should be player1", issue1.getPlayer(), player1);
    }

    /**
     * Test method for
     * {@link ch.epfl.scrumtool.entity.Issue#Issue(ch.epfl.scrumtool.entity.Issue)}
     * .
     */
    @Test
    public void testIssueIssue() {
        Player player1 = new Player(10, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Issue issue2 = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT,
                0.5f, player1);
        Issue issue1 = new Issue(issue2);
        assertEquals("Id shoulb be 1", issue1.getId(), 1);
        assertEquals("Name should be issue1", issue1.getName(), "issue1");
        assertEquals("Description should be 'description1'",
                issue1.getDescription(), "descritpion1");
        assertEquals("Status should be IN_SPRINT", issue1.getStatus(),
                Status.IN_SPRINT);
        assertEquals("Token should be 0.5f", issue1.getEstimatedTime(), 0.5f);
        assertEquals("Player should be player1", issue1.getPlayer(), player1);
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Issue#getEstimatedTime()}
     * .
     */
    @Test
    public void testGetEstimatedTime() {
        Issue issue1 = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT,
                0.5f, new Player(10, new User("username1", "email", "name",
                        100, new HashSet<Project>()), Role.DEVELOPER));
        assertEquals("Token should be 0.5f", issue1.getEstimatedTime(), 0.5f);
        issue1.setEstimatedTime(-1f);
        assertEquals("Token should be -1f", issue1.getEstimatedTime(), -1f);
        issue1.setEstimatedTime(120f);
        assertEquals("Token should be 120f", issue1.getEstimatedTime(), 120f);
        issue1.setEstimatedTime(0f);
        assertEquals("Token should be 0", issue1.getEstimatedTime(), 0f);

    }

    /**
     * Test method for
     * {@link ch.epfl.scrumtool.entity.Issue#setEstimatedTime(float)}.
     */
    @Test
    public void testSetEstimatedTime() {
        Issue issue1 = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT,
                0.5f, new Player(10, new User("username1", "email", "name",
                        100, new HashSet<Project>()), Role.DEVELOPER));
        assertEquals("Token should be 0.5f", issue1.getEstimatedTime(), 0.5f);
        issue1.setEstimatedTime(-1f);
        assertEquals("Token should be -1f", issue1.getEstimatedTime(), -1f);
        issue1.setEstimatedTime(120f);
        assertEquals("Token should be 120f", issue1.getEstimatedTime(), 120f);
        issue1.setEstimatedTime(0f);
        assertEquals("Token should be 0", issue1.getEstimatedTime(), 0f);
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Issue#getPlayer()}.
     */
    @Test
    public void testGetPlayer() {
        Player player1 = new Player(10, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player2 = new Player(20, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player3 = new Player(10, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.SCRUM_MASTER);
        Player player4 = new Player(10, new User("username2", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player5 = new Player(10, new User("username1", "email2", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player6 = new Player(10, new User("username1", "email", "name3",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player7 = new Player(10, new User("username1", "email", "name",
                200, new HashSet<Project>()), Role.DEVELOPER);
        Issue issue = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT,
                0.5f, player1);
        assertEquals("Player should be player1", issue.getPlayer(), player1);
        issue.setPlayer(player2);
        assertEquals("Player should be player2", issue.getPlayer(), player2);
        issue.setPlayer(player3);
        assertEquals("Player should be player3", issue.getPlayer(), player3);
        issue.setPlayer(player4);
        assertEquals("Player should be player4", issue.getPlayer(), player4);
        issue.setPlayer(player5);
        assertEquals("Player should be player5", issue.getPlayer(), player5);
        issue.setPlayer(player6);
        assertEquals("Player should be player6", issue.getPlayer(), player6);
        issue.setPlayer(player7);
        assertEquals("Player should be player7", issue.getPlayer(), player7);

    }

    /**
     * Test method for
     * {@link ch.epfl.scrumtool.entity.Issue#setPlayer(ch.epfl.scrumtool.entity.Player)}
     * .
     */
    @Test
    public void testSetPlayer() {
        Player player1 = new Player(10, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player2 = new Player(20, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player3 = new Player(10, new User("username1", "email", "name",
                100, new HashSet<Project>()), Role.SCRUM_MASTER);
        Player player4 = new Player(10, new User("username2", "email", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player5 = new Player(10, new User("username1", "email2", "name",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player6 = new Player(10, new User("username1", "email", "name3",
                100, new HashSet<Project>()), Role.DEVELOPER);
        Player player7 = new Player(10, new User("username1", "email", "name",
                200, new HashSet<Project>()), Role.DEVELOPER);
        Issue issue = new Issue(1, "issue1", "descritpion1", Status.IN_SPRINT,
                0.5f, player1);
        assertEquals("Player should be player1", issue.getPlayer(), player1);
        issue.setPlayer(player2);
        assertEquals("Player should be player2", issue.getPlayer(), player2);
        issue.setPlayer(player3);
        assertEquals("Player should be player3", issue.getPlayer(), player3);
        issue.setPlayer(player4);
        assertEquals("Player should be player4", issue.getPlayer(), player4);
        issue.setPlayer(player5);
        assertEquals("Player should be player5", issue.getPlayer(), player5);
        issue.setPlayer(player6);
        assertEquals("Player should be player6", issue.getPlayer(), player6);
        issue.setPlayer(player7);
        assertEquals("Player should be player7", issue.getPlayer(), player7);
    }

}
