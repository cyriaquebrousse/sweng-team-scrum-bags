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
    
    private final User user2 = MockData.VINCENT;
    private final User.Builder builder = new User.Builder()
        .setCompanyName(COMPANY_NAME)
        .setDateOfBirth(DATE)
        .setEmail(EMAIL)
        .setGender(GENDER)
        .setJobTitle(JOB)
        .setLastName(LAST_NAME)
        .setName(NAME);
    private final User user = builder.build();
    
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
        assertEquals(EMAIL, user.getEmail());
    }

    public void testGetName() {
        assertEquals(NAME, user.getName());
    }

    public void testGetCompanyName() {
        assertEquals(COMPANY_NAME, user.getCompanyName());
    }
    
    public void testGetDateOfBirth() {
        assertEquals(DATE, user.getDateOfBirth());
    }
    
    public void testGetLastName() {
        assertEquals(LAST_NAME, user.getLastName());
    }
    
    public void testGetGender() {
        assertEquals(GENDER, user.getGender());
    }
    
    public void testGetJobTitle() {
        assertEquals(JOB, user.getJobTitle());
    }
    
    public void testFullName() {
        assertEquals(NAME + " " + LAST_NAME, user.fullname());
    }
    
    public void testGetBuilder() {
        User.Builder builder = user.getBuilder();
        assertEquals(builder.getCompanyName(), user.getCompanyName());
        assertEquals(builder.getDateOfBirth(), user.getDateOfBirth());
        assertEquals(builder.getEmail(), user.getEmail());
        assertEquals(builder.getJobTitle(), user.getJobTitle());
        assertEquals(builder.getLastName(), user.getLastName());
        assertEquals(builder.getName(), user.getName());
        assertEquals(builder.getGender(), user.getGender());
    }
    
    public void testBuildetSetGetEmail() {
        builder.setEmail(user2.getEmail());
        builder.setEmail(null);
        assertNotNull(builder.getEmail());
        assertEquals(user2.getEmail(), builder.getEmail());
    }
    
    public void testBuildetSetGetName() {
        builder.setName(user2.getName());
        builder.setName(null);
        assertNotNull(builder.getName());
        assertEquals(user2.getName(), builder.getName());
    }
    
    public void testBuildetSetGetLastName() {
        builder.setLastName(user2.getLastName());
        builder.setLastName(null);
        assertNotNull(builder.getLastName());
        assertEquals(user2.getLastName(), builder.getLastName());
    }
    
    public void testBuildetSetGetGender() {
        builder.setGender(user2.getGender());
        builder.setGender(null);
        assertNotNull(builder.getGender());
        assertEquals(user2.getGender(), builder.getGender());
    }
    
    public void testBuildetSetGetDateOfBirth() {
        builder.setDateOfBirth(user2.getDateOfBirth());
        assertEquals(user2.getDateOfBirth(), builder.getDateOfBirth());
    }
    
    public void testBuildetSetGetJobTitle() {
        builder.setJobTitle(user2.getJobTitle());
        builder.setJobTitle(null);
        assertNotNull(builder.getJobTitle());
        assertEquals(user2.getJobTitle(), builder.getJobTitle());
    }
    
    public void testBuildetSetGetcompanyName() {
        builder.setCompanyName(user2.getCompanyName());
        builder.setCompanyName(null);
        assertNotNull(builder.getCompanyName());
        assertEquals(user2.getCompanyName(), builder.getCompanyName());
    }
    
    public void testBuilderBuild() {
        //Redundant with testGet<field> tests
    }
    
    public void testHashCode() {
        assertEquals(user.hashCode(), user.getBuilder().build().hashCode());
        assertNotSame(user.hashCode(), user2.hashCode());
    }
    
    public void testEqualsObject() {
        assertTrue(user.equals(user));
        User copy = user.getBuilder().build();
        assertTrue(user.equals(copy));
        assertFalse(user.equals(user2));
        assertFalse(user.equals(null));
        assertFalse(user.equals(user.getBuilder().setName(user2.getName()).build()));
    }
    
    public void testCompareTo() {
        assertTrue(user.compareTo(user) == 0);
        User u = builder.setName("Zac").build();
        assertTrue(user.compareTo(u) < 0);
        assertTrue(u.compareTo(user) > 0);
    }

}
