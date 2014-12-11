package ch.epfl.scrumtool.database.google.operations.test;

import ch.epfl.scrumtool.database.google.containers.InsertPlayerArgs;
import ch.epfl.scrumtool.database.google.operations.PlayerOperations;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import junit.framework.TestCase;
/**
 * 
 * @author aschneuw
 *
 */
public class PlayerOperationsTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    
    public void testDeletePlayer() {
        try {
            assertNull(PlayerOperations.DELETE_PLAYER.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testInsertPlayerToProject() {
        try {
            InsertPlayerArgs args = new InsertPlayerArgs(
                    ServerClientEntities.generateBasicProject(),
                    ServerClientEntities.generateBasicUser().getEmail(),
                    Role.SCRUM_MASTER);
            assertNotNull(PlayerOperations.INSERT_PLAYER_TO_PROJECT.execute(args, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoadInvitedPlayers() {
        try {
            assertNotNull(PlayerOperations.LOAD_INVITED_PLAYERS.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoadPlayers() {
        try {
            assertNotNull(PlayerOperations.LOAD_PLAYERS.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testUpdatePlayer() {
        try {
            assertNull(PlayerOperations.UPDATE_PLAYER.execute(ServerClientEntities.generateBasicPlayer(), SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
}
