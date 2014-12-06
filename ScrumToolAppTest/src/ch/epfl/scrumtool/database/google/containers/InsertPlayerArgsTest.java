package ch.epfl.scrumtool.database.google.containers;

import junit.framework.TestCase;

import org.junit.Test;

import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.TestEntities;

public class InsertPlayerArgsTest extends TestCase {
    private static final String validEmail = "test@test.com";
    
    @Test
    public void testNullProject() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(null, validEmail, Role.INVITED);
        fail("Project must not be null");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullpointerException expected");
        }
        //
    }
    
    @Test
    public void testNullRole() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_W_KEY, validEmail, null);
        fail("Role must not be null");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullpointerException expected");
        }
    }
    
    @Test
    public void testInvalidRole() {
        for (Role r: Role.values()) {
            if (r != Role.INVITED) {
                try {
                    @SuppressWarnings("unused")
                    InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_W_KEY, validEmail, r);
                    fail("Role must be Invited");
                } catch (IllegalArgumentException e) {

                } catch (Exception e) {
                    fail("IllegalArgumentException expected");
                }
            }
        }
    }
    
    @Test
    public void testInvalidEmail() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_W_KEY, "", Role.INVITED);
        fail("Email must be valid");
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e) {
            fail("IllegalArgumentException expected");
        }
    }
    
    @Test
    public void testNullEmail() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_W_KEY, null, Role.INVITED);
        fail("Email must not be null");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    @Test
    public void testValidDataTest() {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_W_KEY, validEmail, Role.INVITED);
        //No Exceptions if arguments are ok
    }
    
    @Test
    public void testGetRoleTest() {
        InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_W_KEY, validEmail, Role.INVITED);
        assertEquals(args.getRole(), Role.INVITED.name());
    }
    
    @Test
    public void testGetProjectKeyTest() {
        InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_W_KEY, validEmail, Role.INVITED);
        assertEquals(args.getProjectKey(), TestEntities.TEST_PROJECT_W_KEY.getKey());
    }
    
    @Test
    public void testInvalidProjectKeyTest() {
        try {
        @SuppressWarnings("unused")
        InsertPlayerArgs args = new InsertPlayerArgs(TestEntities.TEST_PROJECT_WO_KEY, validEmail, Role.INVITED);
        fail("IllegalArgument Exception should be thrown for invalid Project key");
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e) {
            fail("IllegalArgumentException expected");
        }
    }
}
