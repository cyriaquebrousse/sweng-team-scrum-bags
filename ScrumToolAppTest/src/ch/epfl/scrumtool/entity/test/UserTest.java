package ch.epfl.scrumtool.entity.test;

import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;
import junit.framework.TestCase;

public class UserTest extends TestCase {
    
    private final static String COMPANY_NAME = "Apple";
    private final static long DATE = 0;
    private final static String EMAIL = "email@email.ch";
    private final static Gender GENDRE = Gender.UNKNOWN;
    private final static String JOB = "CEO";
    private final static String LAST_NAME = "Smith";
    private final static String NAME = "Steve";
    
    
    private static final User user = new User.Builder()
    .setCompanyName(COMPANY_NAME)
    .setDateOfBirth(DATE)
    .setEmail(EMAIL)
    .setGender(GENDRE)
    .setJobTitle(JOB)
    .setLastName(LAST_NAME)
    .setName(NAME)
    .build();

    public void testGetEmail() {
        String email = user.getEmail();
        assertEquals(EMAIL, email);
    }

    public void testGetName() {
        String name = user.getName();
        assertEquals(NAME, name);    }

    public void testEqualsObject() {
        User user2 = user;
        assertTrue(user.equals(user2));
    }

}
