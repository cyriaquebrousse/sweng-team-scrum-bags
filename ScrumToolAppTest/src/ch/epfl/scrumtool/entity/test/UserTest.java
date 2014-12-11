package ch.epfl.scrumtool.entity.test;

import java.util.Calendar;

import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import junit.framework.TestCase;

/**
 * Tests the User entity
 * 
 * @author vincent
 *
 */
public class UserTest extends TestCase {
    
    private final static String COMPANY_NAME = "Apple";
    private final static long DATE = Calendar.getInstance().getTimeInMillis();
    private final static String EMAIL = "email@email.ch";
    private final static Gender GENDER = Gender.UNKNOWN;
    private final static String JOB = "CEO";
    private final static String LAST_NAME = "Smith";
    private final static String NAME = "Steve";
    
    private static final User USER2 = MockData.VINCENT;
    private static final User.Builder BUILDER = new User.Builder()
        .setCompanyName(COMPANY_NAME)
        .setDateOfBirth(DATE)
        .setEmail(EMAIL)
        .setGender(GENDER)
        .setJobTitle(JOB)
        .setLastName(LAST_NAME)
        .setName(NAME);
    private static final User USER = BUILDER.build();
    
    public void testConstructor() {
        // It's impossible to have null value passed to constructor
        // all builder fields are initialized
        // all setter check for null
        
        // check for invalid email
        try {
            new User.Builder().setName("Joe").build();
            fail("expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testGetEmail() {
        assertEquals(EMAIL, USER.getEmail());
    }

    public void testGetName() {
        assertEquals(NAME, USER.getName());
    }

    public void testGetCompanyName() {
        assertEquals(COMPANY_NAME, USER.getCompanyName());
    }
    
    public void testGetDateOfBirth() {
        assertEquals(DATE, USER.getDateOfBirth());
    }
    
    public void testGetLastName() {
        assertEquals(LAST_NAME, USER.getLastName());
    }
    
    public void testGetGender() {
        assertEquals(GENDER, USER.getGender());
    }
    
    public void testGetJobTitle() {
        assertEquals(JOB, USER.getJobTitle());
    }
    
    public void testFullName() {
        assertEquals(NAME + " " + LAST_NAME, USER.fullname());
    }
    
    public void testGetBuilder() {
        User.Builder builder = USER.getBuilder();
        assertEquals(builder.getCompanyName(), USER.getCompanyName());
        assertEquals(builder.getDateOfBirth(), USER.getDateOfBirth());
        assertEquals(builder.getEmail(), USER.getEmail());
        assertEquals(builder.getJobTitle(), USER.getJobTitle());
        assertEquals(builder.getLastName(), USER.getLastName());
        assertEquals(builder.getName(), USER.getName());
        assertEquals(builder.getGender(), USER.getGender());
    }
    
    public void testBuildetSetGetEmail() {
        BUILDER.setEmail(USER2.getEmail());
        BUILDER.setEmail(null);
        assertNotNull(BUILDER.getEmail());
        assertEquals(USER2.getEmail(), BUILDER.getEmail());
    }
    
    public void testBuildetSetGetName() {
        BUILDER.setName(USER2.getName());
        BUILDER.setName(null);
        assertNotNull(BUILDER.getName());
        assertEquals(USER2.getName(), BUILDER.getName());
    }
    
    public void testBuildetSetGetLastName() {
        BUILDER.setLastName(USER2.getLastName());
        BUILDER.setLastName(null);
        assertNotNull(BUILDER.getLastName());
        assertEquals(USER2.getLastName(), BUILDER.getLastName());
    }
    
    public void testBuildetSetGetGender() {
        BUILDER.setGender(USER2.getGender());
        BUILDER.setGender(null);
        assertNotNull(BUILDER.getGender());
        assertEquals(USER2.getGender(), BUILDER.getGender());
    }
    
    public void testBuildetSetGetDateOfBirth() {
        BUILDER.setDateOfBirth(USER2.getDateOfBirth());
        assertEquals(USER2.getDateOfBirth(), BUILDER.getDateOfBirth());
    }
    
    public void testBuildetSetGetJobTitle() {
        BUILDER.setJobTitle(USER2.getJobTitle());
        BUILDER.setJobTitle(null);
        assertNotNull(BUILDER.getJobTitle());
        assertEquals(USER2.getJobTitle(), BUILDER.getJobTitle());
    }
    
    public void testBuildetSetGetcompanyName() {
        BUILDER.setCompanyName(USER2.getCompanyName());
        BUILDER.setCompanyName(null);
        assertNotNull(BUILDER.getCompanyName());
        assertEquals(USER2.getCompanyName(), BUILDER.getCompanyName());
    }
    
    public void testBuilderBuild() {
        //Redundant with testGet<field> tests
    }
    
    public void testHashCode() {
        assertEquals(USER.hashCode(), USER.getBuilder().build().hashCode());
        assertNotSame(USER.hashCode(), USER2.hashCode());
    }
    
    public void testEqualsObject() {
        assertTrue(USER.equals(USER));
        User copy = USER.getBuilder().build();
        assertTrue(USER.equals(copy));
        assertFalse(USER.equals(USER2));
        assertFalse(USER.equals(null));
        assertFalse(USER.equals(USER.getBuilder().setName(USER2.getName()).build()));
    }
    
    public void testCompareTo() {
        assertTrue(USER.compareTo(USER) == 0);
        User u = BUILDER.setName("Zac").build();
        assertTrue(USER.compareTo(u) > 0);
        assertTrue(u.compareTo(USER) < 0);
    }

}
