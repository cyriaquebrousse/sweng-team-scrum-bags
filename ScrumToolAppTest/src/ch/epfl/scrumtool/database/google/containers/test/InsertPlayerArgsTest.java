package ch.epfl.scrumtool.database.google.containers.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertPlayerArgs;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;

/**
 * 
 * @author aschneuw
 *
 */
public class InsertPlayerArgsTest extends TestCase {
    private static final String VALID_EMAIL = "test@test.com";

    public void testNullProject() {
        try {
            new InsertPlayerArgs(null, VALID_EMAIL, Role.STAKEHOLDER);
            fail("Project must not be null");
        } catch (NullPointerException e) {

        }
    }

    public void testNullRole() {
        try {
            new InsertPlayerArgs(ServerClientEntities.TEST_PROJECT_W_KEY, VALID_EMAIL, null);
            fail("Role must not be null");
        } catch (NullPointerException e) {

        }
    }

    public void testInvalidEmail() {
        try {
            new InsertPlayerArgs(ServerClientEntities.TEST_PROJECT_W_KEY, "", Role.STAKEHOLDER);
            fail("Email must be valid");
        } catch (IllegalArgumentException e) {

        }
    }

    public void testNullEmail() {
        try {
            new InsertPlayerArgs(ServerClientEntities.TEST_PROJECT_W_KEY, null, Role.STAKEHOLDER);
            fail("Email must not be null");
        } catch (NullPointerException e) {

        }
    }

    public void testValidDataTest() {
        new InsertPlayerArgs(ServerClientEntities.TEST_PROJECT_W_KEY, VALID_EMAIL, Role.STAKEHOLDER);
        //No Exceptions if arguments are ok
    }

    public void testGetRoleTest() {
        InsertPlayerArgs args = new InsertPlayerArgs(ServerClientEntities.TEST_PROJECT_W_KEY, VALID_EMAIL, Role.STAKEHOLDER);
        assertEquals(args.getRole(), Role.STAKEHOLDER.name());
    }

    public void testGetProjectKeyTest() {
        InsertPlayerArgs args = new InsertPlayerArgs(ServerClientEntities.TEST_PROJECT_W_KEY, VALID_EMAIL, Role.STAKEHOLDER);
        assertEquals(args.getProjectKey(), ServerClientEntities.TEST_PROJECT_W_KEY.getKey());
    }

    public void testInvalidProjectKeyTest() {
        try {
            new InsertPlayerArgs(ServerClientEntities.TEST_PROJECT_WO_KEY, VALID_EMAIL, Role.STAKEHOLDER);
            fail("IllegalArgument Exception should be thrown for invalid Project key");
        } catch (IllegalArgumentException e) {

        }
    }
}
