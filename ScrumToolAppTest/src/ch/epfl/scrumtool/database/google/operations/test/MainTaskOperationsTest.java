package ch.epfl.scrumtool.database.google.operations.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import ch.epfl.scrumtool.database.google.operations.MainTaskOperations;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 *
 */
public class MainTaskOperationsTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    
    public void testDeleteMainTask() {
        try {
            assertNull(MainTaskOperations.DELETE_MAINTASK.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testInsertMainTask() {
        try {
            EntityKeyArg<MainTask> arg = new EntityKeyArg<MainTask>(
                    ServerClientEntities.generateBasicMainTask(),
                    ServerClientEntities.VALIDKEY);
            assertNotNull(MainTaskOperations.INSERT_MAINTASK.execute(arg, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoadMainTask() {
        try {
            assertNotNull(MainTaskOperations.LOAD_MAINTASKS.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testUpdateMainTask() {
        try {
            assertNull(MainTaskOperations.UPDATE_MAINTASK.execute(
                    ServerClientEntities.generateBasicMainTask(), SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
}
