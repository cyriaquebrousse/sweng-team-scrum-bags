package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import junit.framework.TestCase;

/**
 * Tests the project entity
 * @author vincent
 *
 */
public class ProjectTest extends TestCase {
    
    private final static String KEY = "key";
    private final static String NAME = "NAME";    
    private final static String DESCRIPTION = "Description";
    
    private final Project.Builder builder = new Project.Builder()
        .setKey(KEY)
        .setName(NAME)
        .setDescription(DESCRIPTION);
    private final Project project = builder.build();
    private final Project project2 = MockData.PROJECT; 
    
    
    public void testConstructor() {
        // It's impossible to have null value passed to constructor
        // all builder fields are initialized
        // all setter check for null
        
        // check for empty name
        try {
            new Project.Builder().setDescription(DESCRIPTION)
                .setKey(KEY).build();
            fail("expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        // check for empty description
        try {
            new Project.Builder().setName(NAME)
                .setKey(KEY).build();
            fail("expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testGetName() {
        assertEquals(NAME, project.getName());
    }

    public void testGetDescription() {
        assertEquals(DESCRIPTION, project.getDescription());
    }

    public void testGetKey() {
        assertEquals(KEY, project.getKey());
    }
    
    public void testBuilderSetGetKey() {
        builder.setKey(project2.getKey());
        builder.setKey(null);
        assertNotNull(builder.getKey());
        assertEquals(project2.getKey(), builder.getKey());
    }
    
    public void testBuilderSetGetName() {
        builder.setName(project2.getName());
        builder.setName(null);
        assertNotNull(builder.getName());
        assertEquals(project2.getName(), builder.getName());
    }
    
    public void testBuilderSetGetDescription() {
        builder.setDescription(project2.getDescription());
        builder.setDescription(null);
        assertNotNull(builder.getDescription());
        assertEquals(project2.getDescription(), builder.getDescription());
    }
    
    public void testBuilderBuild() {
        //Redundant with testGet<field> tests
    }

    public void testGetBuilder() {
        Project.Builder builder = project.getBuilder();
        assertEquals(KEY, builder.getKey());
        assertEquals(NAME, builder.getName());
        assertEquals(DESCRIPTION, builder.getDescription());
    }

    public void testEqualsObject() {
        assertTrue(project.equals(project));
        assertFalse(project.equals(project2));
        assertFalse(project.equals(null));
    }
    
    public void testCompareTo() {
        Project p = builder.setName("a").build();
        assertTrue(project.compareTo(p) < 0);
        assertTrue(p.compareTo(project) > 0);
        p = builder.setName(NAME).setDescription("a").build();
        assertTrue(project.compareTo(p) == 0);
        assertTrue(project.compareTo(project) == 0);
    }

    public void testHashCode() {
        assertEquals(project.hashCode(), project.getBuilder().build().hashCode());
        assertNotSame(project.hashCode(), project2.hashCode());
    }
}
