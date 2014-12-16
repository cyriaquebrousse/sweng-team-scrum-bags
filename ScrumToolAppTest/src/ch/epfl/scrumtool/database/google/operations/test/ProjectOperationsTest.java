package ch.epfl.scrumtool.database.google.operations.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.operations.ProjectOperations;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 *
 */
public class ProjectOperationsTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    
    public void testDeleteProject() {
        try {
            assertNull(ProjectOperations.DELETE_PROJECT.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testInsertProject() {
        try {
            assertNotNull(ProjectOperations.INSERT_PROJECT.execute(
                    ServerClientEntities.generateBasicProject(),
                    SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoadProjects() {
        try {
            new Session(ServerClientEntities.generateBasicUser()){};
            assertNotNull(ProjectOperations.LOAD_PROJECTS.execute(null, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }  finally {
            Session.destroySession();
        }
    }
    
    public void testUpdateProject() {
        try {
            
            assertNull(ProjectOperations.UPDATE_PROJECT.execute(
                    ServerClientEntities.generateBasicProject(),
                    SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
}
