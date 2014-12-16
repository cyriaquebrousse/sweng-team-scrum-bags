package ch.epfl.scrumtool.database.google.operations.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import ch.epfl.scrumtool.database.google.operations.SprintOperations;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 *
 */
public class SprintOperationsTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    
    public void testDeleteSprint() {
        try {
            assertNull(SprintOperations.DELETE_SPRINT.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testInsertSprint() {
        try {
            EntityKeyArg<Sprint> args = new EntityKeyArg<Sprint>(
                    ServerClientEntities.generateBasicSprint(),
                    ServerClientEntities.VALIDKEY);
            assertNotNull(SprintOperations.INSERT_SPRINT.execute(args, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoadSprint() {
        try {
            assertNotNull(SprintOperations.LOAD_SPRINT.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testUpdateSprint() {
        try {
            assertNull(SprintOperations.UPDATE_SPRINT.execute(
                    ServerClientEntities.generateBasicSprint(),
                    SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
}
