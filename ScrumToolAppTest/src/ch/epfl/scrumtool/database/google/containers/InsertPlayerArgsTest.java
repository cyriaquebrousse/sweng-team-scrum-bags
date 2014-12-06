package ch.epfl.scrumtool.database.google.containers;

import junit.framework.TestCase;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.utils.TestConstants;

public class InsertPlayerArgsTest extends TestCase {
    private static final String validEmail = "test@test.com";
    
    public void testNullProject() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(null, validEmail, Role.STAKEHOLDER);
        fail("Project must not be null");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullpointerException expected");
        }
        //
    }
    
    public void testNullRole() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_W_KEY, validEmail, null);
        fail("Role must not be null");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullpointerException expected");
        }
    }
    
    public void testInvalidRole() {
        for (Role r: Role.values()) {
            if (r != Role.STAKEHOLDER) {
                try {
                    @SuppressWarnings("unused")
                    InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_W_KEY, validEmail, r);
                    fail("Role must be STAKEHOLDER");
                } catch (IllegalArgumentException e) {

                } catch (Exception e) {
                    fail("IllegalArgumentException expected");
                }
            }
        }
    }
    
    public void testInvalidEmail() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_W_KEY, "", Role.STAKEHOLDER);
        fail("Email must be valid");
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e) {
            fail("IllegalArgumentException expected");
        }
    }
    
    public void testNullEmail() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_W_KEY, null, Role.STAKEHOLDER);
        fail("Email must not be null");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testValidDataTest() {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_W_KEY, validEmail, Role.STAKEHOLDER);
        //No Exceptions if arguments are ok
    }
    
    public void testGetRoleTest() {
        InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_W_KEY, validEmail, Role.STAKEHOLDER);
        assertEquals(args.getRole(), Role.STAKEHOLDER.name());
    }
    
    public void testGetProjectKeyTest() {
        InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_W_KEY, validEmail, Role.STAKEHOLDER);
        assertEquals(args.getProjectKey(), TestConstants.TEST_PROJECT_W_KEY.getKey());
    }
    
    public void testInvalidProjectKeyTest() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestConstants.TEST_PROJECT_WO_KEY, validEmail, Role.STAKEHOLDER);
        fail("IllegalArgument Exception should be thrown for invalid Project key");
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e) {
            fail("IllegalArgumentException expected");
        }
    }
}
