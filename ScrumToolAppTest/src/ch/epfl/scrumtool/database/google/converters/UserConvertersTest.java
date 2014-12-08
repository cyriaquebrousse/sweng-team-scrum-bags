package ch.epfl.scrumtool.database.google.converters;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.conversion.UserConverters;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;
import ch.epfl.scrumtool.utils.TestConstants;

public class UserConvertersTest extends TestCase {
    private static String NAME = "name";
    private static String FAMILYNAME = "familyname";
    private static String JOB_TITLE = "jobtitle";
    private static String COMPANY_NAME = "companyName";
    private static Gender GENDER = Gender.MALE;
    private static long DATEOFBIRTH = TestConstants.getDay(2001, 07, 05);
    
    private static User generateExtendedUser() {
        User user = TestConstants.generateBasicUser();
        user = user.getBuilder()
                .setCompanyName(COMPANY_NAME)
                .setDateOfBirth(DATEOFBIRTH)
                .setGender(GENDER)
                .setJobTitle(JOB_TITLE)
                .setLastName(FAMILYNAME)
                .setName(NAME).build();
        return user;
    }
    
    private static ScrumUser generateExtendedScrumUser() {
        ScrumUser user = TestConstants.generateBasicScrumUser();
        user.setCompanyName(COMPANY_NAME);
        user.setDateOfBirth(DATEOFBIRTH);
        user.setGender(GENDER.name());
        user.setJobTitle(JOB_TITLE);
        user.setLastName(FAMILYNAME);
        user.setName(NAME);
        return user;
    }
    
    public void testToUserNullEmail() {
        try {
            ScrumUser user = generateExtendedScrumUser();
            user.setEmail(null);            
            UserConverters.SCRUMUSER_TO_USER.convert(user);
            fail("NullPointerException for invalid User expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
        
    }
    
    public void testToUserInvalidEmail() {
        try {
            ScrumUser user = generateExtendedScrumUser();
            user.setEmail("bla");     
            UserConverters.SCRUMUSER_TO_USER.convert(user);
            fail("IllegalArgumentException for invalid User E-Mail expected");
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e) {
            fail("IllegalArgumentException expected");
        }
    }
    
    public void testToUserNullName() {
        ScrumUser user = generateExtendedScrumUser();
        user.setName(null);
        User result = UserConverters.SCRUMUSER_TO_USER.convert(user);
        User mustResult = generateExtendedUser()
                .getBuilder()
                .setName("")
                .build();
        assertEquals(mustResult, result);
    }
    
    public void testToUserNullLastName() {
        ScrumUser user = generateExtendedScrumUser();
        user.setLastName(null);
        User result = UserConverters.SCRUMUSER_TO_USER.convert(user);
        User mustResult = generateExtendedUser()
                .getBuilder()
                .setLastName("")
                .build();
        assertEquals(mustResult, result);
    }
    
    public void testToUserNullCompanyName() {
        ScrumUser user = generateExtendedScrumUser();
        user.setCompanyName(null);
        User result = UserConverters.SCRUMUSER_TO_USER.convert(user);
        User mustResult = generateExtendedUser()
                .getBuilder()
                .setCompanyName("")
                .build();
        assertEquals(mustResult, result);
    }
    
    public void testToUserNullJobTitle() {
        ScrumUser user = generateExtendedScrumUser();
        user.setJobTitle(null);
        User result = UserConverters.SCRUMUSER_TO_USER.convert(user);
        User mustResult = generateExtendedUser()
                .getBuilder()
                .setJobTitle("")
                .build();
        assertEquals(mustResult, result);
    }
    
    public void testToUserNullDateOfBirth() {
        ScrumUser user = generateExtendedScrumUser();
        user.setDateOfBirth(null);
        User result = UserConverters.SCRUMUSER_TO_USER.convert(user);
        User mustResult = generateExtendedUser()
                .getBuilder()
                .setDateOfBirth(0L)
                .build();
        assertEquals(mustResult, result);
    }
    
    public void testToUserNullGender() {
        ScrumUser user = generateExtendedScrumUser();
        user.setGender(null);
        User result = UserConverters.SCRUMUSER_TO_USER.convert(user);
        User mustResult = generateExtendedUser()
                .getBuilder()
                .setGender(Gender.UNKNOWN)
                .build();
        assertEquals(mustResult, result);
    }
    
    public void testToUserFull(){
        ScrumUser user = generateExtendedScrumUser();
        User result = UserConverters.SCRUMUSER_TO_USER.convert(user);
        User mustResult = generateExtendedUser();
        assertEquals(mustResult, result);
    }
    
    public void testToScrumUserFull() {
        User user = generateExtendedUser();
        ScrumUser result = UserConverters.USER_TO_SCRUMUSER.convert(user);
        ScrumUser mustResult = generateExtendedScrumUser();
        assertEquals(mustResult.getCompanyName(), result.getCompanyName());
        assertEquals(mustResult.getEmail(), result.getEmail());
        assertEquals(mustResult.getGender(), result.getGender());
        assertEquals(mustResult.getJobTitle(), result.getJobTitle());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDateOfBirth(), result.getDateOfBirth());
    }
    
    public void testToScrumUserJobTitleEmpty() {
        User user = generateExtendedUser();
        user = user.getBuilder()
                .setJobTitle("")
                .build();
        ScrumUser result = UserConverters.USER_TO_SCRUMUSER.convert(user);
        ScrumUser mustResult = generateExtendedScrumUser();
        mustResult.setJobTitle(null);
        assertEquals(mustResult.getCompanyName(), result.getCompanyName());
        assertEquals(mustResult.getEmail(), result.getEmail());
        assertEquals(mustResult.getGender(), result.getGender());
        assertEquals(mustResult.getJobTitle(), result.getJobTitle());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDateOfBirth(), result.getDateOfBirth());
    }
    
    public void testToScrumUserFamilyNameEmpty() {
        User user = generateExtendedUser();
        user = user.getBuilder()
                .setLastName("")
                .build();
        ScrumUser result = UserConverters.USER_TO_SCRUMUSER.convert(user);
        ScrumUser mustResult = generateExtendedScrumUser();
        mustResult.setLastName(null);
        assertEquals(mustResult.getCompanyName(), result.getCompanyName());
        assertEquals(mustResult.getEmail(), result.getEmail());
        assertEquals(mustResult.getGender(), result.getGender());
        assertEquals(mustResult.getJobTitle(), result.getJobTitle());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDateOfBirth(), result.getDateOfBirth());
    }
    
    public void testToScrumUserNameEmpty() {
        User user = generateExtendedUser();
        user = user.getBuilder()
                .setName("")
                .build();
        ScrumUser result = UserConverters.USER_TO_SCRUMUSER.convert(user);
        ScrumUser mustResult = generateExtendedScrumUser();
        mustResult.setName(null);
        assertEquals(mustResult.getCompanyName(), result.getCompanyName());
        assertEquals(mustResult.getEmail(), result.getEmail());
        assertEquals(mustResult.getGender(), result.getGender());
        assertEquals(mustResult.getJobTitle(), result.getJobTitle());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDateOfBirth(), result.getDateOfBirth());
    }
    
    public void testToScrumUserNoBirthday() {
        User user = generateExtendedUser();
        user = user.getBuilder()
                .setDateOfBirth(0)
                .build();
        ScrumUser result = UserConverters.USER_TO_SCRUMUSER.convert(user);
        ScrumUser mustResult = generateExtendedScrumUser();
        mustResult.setDateOfBirth(null);
        assertEquals(mustResult.getCompanyName(), result.getCompanyName());
        assertEquals(mustResult.getEmail(), result.getEmail());
        assertEquals(mustResult.getGender(), result.getGender());
        assertEquals(mustResult.getJobTitle(), result.getJobTitle());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDateOfBirth(), result.getDateOfBirth());
    }
    
    public void testToScrumUserCompanyNameEmpty() {
        User user = generateExtendedUser();
        user = user.getBuilder()
                .setCompanyName("")
                .build();
        ScrumUser result = UserConverters.USER_TO_SCRUMUSER.convert(user);
        ScrumUser mustResult = generateExtendedScrumUser();
        mustResult.setCompanyName(null);
        assertEquals(mustResult.getCompanyName(), result.getCompanyName());
        assertEquals(mustResult.getEmail(), result.getEmail());
        assertEquals(mustResult.getGender(), result.getGender());
        assertEquals(mustResult.getJobTitle(), result.getJobTitle());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDateOfBirth(), result.getDateOfBirth());
    }
}
