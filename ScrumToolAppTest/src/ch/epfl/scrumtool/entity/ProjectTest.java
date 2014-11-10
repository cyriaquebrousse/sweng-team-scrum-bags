package ch.epfl.scrumtool.entity;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author ketsio
 *
 */
public class ProjectTest extends TestCase {
    
    private final Project project1;
    private final Project project2;
    private final Project project3;

    /**
     * @param name
     */
    public ProjectTest(String name) {
        super(name);
        
        project1 = new Project(1l, "Super Cool Project", "description...", 
                new Player(1, Entity.MARIA_LINDA, Role.DEVELOPER),
                new HashSet<Player>(Arrays.asList(new Player(1, Entity.MARIA_LINDA, Role.DEVELOPER))), 
                new HashSet<MainTask>(Arrays.asList(Entity.TASK_A, Entity.TASK_B)), 
                new HashSet<Sprint>());
        
        project2 = new Project(2l, "Lame Project", "\n\t\n lame\"\"", 
                new Player(1, Entity.MARIA_LINDA, Role.DEVELOPER),
                new HashSet<Player>(Arrays.asList(new Player(2, Entity.ARJEN_LENSTRA, Role.PRODUCT_OWNER))), 
                new HashSet<MainTask>(), 
                new HashSet<Sprint>());
        
        project3 = new Project(3l, "Okay project", "description...", 
                new Player(1, Entity.CYRIAQUE_BROUSSE, Role.PRODUCT_OWNER),
                new HashSet<Player>(Arrays.asList(new Player(2, Entity.MARIA_LINDA, Role.DEVELOPER))), 
                new HashSet<MainTask>(Arrays.asList(Entity.TASK_B, Entity.TASK_B, Entity.TASK_C)), 
                new HashSet<Sprint>());
        
        System.out.println("constr");
    }
    
    @Test
    public void testEquals() {
        Project projectCopiedFromProject1 = new Project(project1);
        assertEquals(
                "A project copied from another project should be semantically equals", 
                project1, 
                projectCopiedFromProject1);
        assertFalse("Different projects should not be equals",
                project1.equals(project2));
        assertFalse("Different projects should not be equals",
                project1.equals(project3));
        assertFalse("Different projects should not be equals",
                project2.equals(project3));
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#Project(long, java.lang.String, java.lang.String, ch.epfl.scrumtool.entity.Player, java.util.Set, java.util.Set, java.util.Set)}.
     */
    @Test
    public void testProjectLongStringStringPlayerSetOfPlayerSetOfMainTaskSetOfSprint() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#Project(ch.epfl.scrumtool.entity.Project)}.
     */
    @Test
    public void testProjectProject() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals("Super Cool Project", project1.getName());
        assertEquals("Lame Project", project2.getName());
        assertEquals("Okay project", project3.getName());
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#setName(java.lang.String)}.
     */
    @Test
    public void testSetName() {
        
        // project 1
        project1.setName("");
        assertEquals("", project1.getName());
        
        // project 2
        project2.setName(null);
        assertNotNull("A name should never be null", project2.getName());
        assertEquals("Lame Project", project2.getName());
        
        // project 3
        project3.setName(new StringBuffer().append(true).toString());
        assertEquals("true", project3.getName());
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getDescription()}.
     */
    @Test
    public void testGetDescription() {
        assertEquals("description...", project1.getDescription());
        assertEquals("\n\t\n lame\"\"", project2.getDescription());
        assertEquals("description...", project3.getDescription());
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#setDescription(java.lang.String)}.
     */
    @Test
    public void testSetDescription() {
        
        // project 1
        project1.setDescription("");
        assertEquals("", project1.getDescription());
        
        // project 2
        project2.setDescription(null);
        assertNotNull("A description should never be null", project2.getDescription());
        assertEquals("\n\t\n lame\"\"", project2.getDescription());
        
        // project 3
        project3.setDescription(new StringBuffer().append(true).toString());
        assertEquals("true", project3.getDescription());
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getAdmin()}.
     */
    @Test
    public void testGetAdmin() {
        Player mariaLindaDeveloper = new Player(1, Entity.MARIA_LINDA, Role.DEVELOPER);
        Player cyriaqueBrousseProductOwner = new Player(1, Entity.CYRIAQUE_BROUSSE, Role.PRODUCT_OWNER);

        assertEquals(mariaLindaDeveloper, project1.getAdmin());
        assertEquals(mariaLindaDeveloper, project2.getAdmin());
        assertEquals(cyriaqueBrousseProductOwner, project3.getAdmin());
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#setAdmin(ch.epfl.scrumtool.entity.Player)}.
     */
    @Test
    public void testSetAdmin() {
        Player mariaLindaDeveloper = new Player(1, Entity.MARIA_LINDA, Role.DEVELOPER);
        Player arjenLenstraStakeholder = new Player(0, Entity.ARJEN_LENSTRA, Role.STAKEHOLDER);
        Player cyriaqueBrousseProductOwner = new Player(1, Entity.CYRIAQUE_BROUSSE, Role.PRODUCT_OWNER);
        
        // project 1
        project1.setAdmin(new Player(cyriaqueBrousseProductOwner));
        assertEquals(cyriaqueBrousseProductOwner, project1.getAdmin());
        
        // project 2
        project2.setAdmin(null);
        assertNotNull("The admin should never be null", project2.getAdmin());
        assertEquals(mariaLindaDeveloper, project2.getAdmin());
        
        // project 3
        project3.setAdmin(arjenLenstraStakeholder);
        assertEquals(arjenLenstraStakeholder, project3.getAdmin());
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getPlayers()}.
     */
    @Test
    public void testGetPlayers() {
        Player mariaLindaDeveloper = new Player(1, Entity.MARIA_LINDA, Role.DEVELOPER);
        Player arjenLenstraStakeholder = new Player(0, Entity.ARJEN_LENSTRA, Role.STAKEHOLDER);
        Player mariaLindaDeveloper2 = new Player(2, Entity.MARIA_LINDA, Role.DEVELOPER);

        Set<Player> players1 = new HashSet<Player>();
        players1.add(mariaLindaDeveloper);
        Set<Player> players2 = new HashSet<Player>();
        players2.add(arjenLenstraStakeholder);
        Set<Player> players3 = new HashSet<Player>();
        players3.add(mariaLindaDeveloper2);
        
        assertEquals(players1, project1.getPlayers());
        assertEquals(players2, project2.getPlayers());
        assertEquals(players3, project3.getPlayers());
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#addPlayer(ch.epfl.scrumtool.entity.Player)}.
     */
    @Test
    public void testAddPlayer() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#removePlayer(ch.epfl.scrumtool.entity.Player)}.
     */
    @Test
    public void testRemovePlayer() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getBacklog()}.
     */
    @Test
    public void testGetBacklog() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#addTask(ch.epfl.scrumtool.entity.MainTask)}.
     */
    @Test
    public void testAddTask() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#removeTask(ch.epfl.scrumtool.entity.MainTask)}.
     */
    @Test
    public void testRemoveTask() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getId()}.
     */
    @Test
    public void testGetId() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#setId(long)}.
     */
    @Test
    public void testSetId() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getSprints()}.
     */
    @Test
    public void testGetSprints() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#addSprint(ch.epfl.scrumtool.entity.Sprint)}.
     */
    @Test
    public void testAddSprint() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#removeSprint(ch.epfl.scrumtool.entity.Sprint)}.
     */
    @Test
    public void testRemoveSprint() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getChangesCount(ch.epfl.scrumtool.entity.User)}.
     */
    @Test
    public void testGetChangesCount() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link ch.epfl.scrumtool.entity.Project#getRoleFor(ch.epfl.scrumtool.entity.User)}.
     */
    @Test
    public void testGetRoleFor() {
        fail("Not yet implemented");
    }

}
