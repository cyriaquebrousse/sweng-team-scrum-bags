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
    
    private final static Project.Builder BUILDER = new Project.Builder()
        .setKey(KEY)
        .setName(NAME)
        .setDescription(DESCRIPTION);
    private final static Project PROJECT = BUILDER.build();
    private final static Project PROJECT2 = MockData.MURCS; 
    
    
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
        assertEquals(NAME, PROJECT.getName());
    }

    public void testGetDescription() {
        assertEquals(DESCRIPTION, PROJECT.getDescription());
    }

    public void testGetKey() {
        assertEquals(KEY, PROJECT.getKey());
    }
    
    public void testBuilderSetGetKey() {
        BUILDER.setKey(PROJECT2.getKey());
        BUILDER.setKey(null);
        assertNotNull(BUILDER.getKey());
        assertEquals(PROJECT2.getKey(), BUILDER.getKey());
    }
    
    public void testBuilderSetGetName() {
        BUILDER.setName(PROJECT2.getName());
        BUILDER.setName(null);
        assertNotNull(BUILDER.getName());
        assertEquals(PROJECT2.getName(), BUILDER.getName());
    }
    
    public void testBuilderSetGetDescription() {
        BUILDER.setDescription(PROJECT2.getDescription());
        BUILDER.setDescription(null);
        assertNotNull(BUILDER.getDescription());
        assertEquals(PROJECT2.getDescription(), BUILDER.getDescription());
    }
    
    public void testBuilderBuild() {
        //Redundant with testGet<field> tests
    }

    public void testGetBuilder() {
        Project.Builder builder = PROJECT.getBuilder();
        assertEquals(KEY, builder.getKey());
        assertEquals(NAME, builder.getName());
        assertEquals(DESCRIPTION, builder.getDescription());
    }

    public void testEqualsObject() {
        assertTrue(PROJECT.equals(PROJECT));
        assertFalse(PROJECT.equals(PROJECT2));
        assertFalse(PROJECT.equals(null));
    }
    
    public void testCompareTo() {
        Project p = BUILDER.setName("a").build();
        assertTrue(PROJECT.compareTo(p) < 0);
        assertTrue(p.compareTo(PROJECT) > 0);
        p = BUILDER.setName(NAME).setDescription("a").build();
        assertTrue(PROJECT.compareTo(p) == 0);
        assertTrue(PROJECT.compareTo(PROJECT) == 0);
    }

    public void testHashCode() {
        assertEquals(PROJECT.hashCode(), PROJECT.getBuilder().build().hashCode());
        assertNotSame(PROJECT.hashCode(), PROJECT2.hashCode());
    }
}
