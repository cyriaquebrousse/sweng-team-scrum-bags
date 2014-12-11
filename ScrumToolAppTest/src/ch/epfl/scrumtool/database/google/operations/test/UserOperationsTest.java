package ch.epfl.scrumtool.database.google.operations.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.operations.UserOperations;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
/**
 * 
 * @author aschneuw
 *
 */
public class UserOperationsTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    
    public void testDeleteUser() {
        try {
            assertNull(UserOperations.DELETE_USER.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoginUser() {
        try {
            assertNotNull(UserOperations.LOGIN_USER.execute(
                    ServerClientEntities.generateBasicUser().getEmail(),
                    SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testUpdateUser() {
        try {
            assertNull(UserOperations.UPDATE_USER.execute(
                    ServerClientEntities.generateBasicUser(),
                    SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
}
