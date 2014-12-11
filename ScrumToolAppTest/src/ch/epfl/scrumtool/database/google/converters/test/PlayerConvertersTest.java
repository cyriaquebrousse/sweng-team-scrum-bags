package ch.epfl.scrumtool.database.google.converters.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.PlayerConverters;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * 
 * @author aschneuw
 *
 */
public class PlayerConvertersTest extends TestCase {
    public void testNullKey() {
        try {
            ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
            player.setKey(null);

            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullUser() {
        try {
            ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
            player.setUser(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullAdminFlag() {
        try {
            ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
            player.setAdminFlag(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullInvitedFlag() {
        try {
            ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
            player.setInvitedFlag(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullRole() {
        try {
            ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
            player.setRole(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testValidPlayer() {
        ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
        Player result = PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
        assertEquals(ServerClientEntities.generateBasicPlayer(), result);
    }
    
    public void testValidPlayerWithProject() {
        ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
        player.setProject(ServerClientEntities.generateBasicScrumProject());
        Player mustResult = ServerClientEntities.generateBasicPlayer()
                .getBuilder()
                .setProject(ServerClientEntities.generateBasicProject())
                .build();
        Player result = PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
        assertEquals(mustResult, result);
    }
    
    public void testToScrumPlayerEmptyKey() {
        Player player = ServerClientEntities.generateBasicPlayer();
        player = player
                .getBuilder()
                .setKey("")
                .build();
        ScrumPlayer result = PlayerConverters.PLAYER_TO_SCRUMPLAYER.convert(player);
        ScrumPlayer mustResult = ServerClientEntities.generateBasicScrumPlayer();
        mustResult.setKey(null);
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getRole(), result.getRole());
        assertEquals(mustResult.getAdminFlag(), result.getAdminFlag());
        assertEquals(mustResult.getProject(), result.getProject());
        assertEquals(mustResult.getUser(), result.getUser());
    }
    
    public void testToScrumPlayerWithKey() {
        Player player = ServerClientEntities.generateBasicPlayer();
        ScrumPlayer result = PlayerConverters.PLAYER_TO_SCRUMPLAYER.convert(player);
        ScrumPlayer mustResult = ServerClientEntities.generateBasicScrumPlayer();
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getRole(), result.getRole());
        assertEquals(mustResult.getAdminFlag(), result.getAdminFlag());
        assertEquals(mustResult.getProject(), result.getProject());
        assertEquals(mustResult.getUser(), result.getUser());
    }
    
    public void testInsertResponse() {
        KeyResponse response = new KeyResponse();
        response.setKey(ServerClientEntities.VALIDKEY);

        Player player = ServerClientEntities.generateBasicPlayer();
        player = player.getBuilder()
                .setKey("")
                .build();

        InsertResponse<Player> insresp = new InsertResponse<Player>(player, response);
        
        Player mustResult = ServerClientEntities.generateBasicPlayer();

        Player keyPlayer = PlayerConverters.INSERTRESPONSE_TO_PLAYER.convert(insresp);
        assertEquals(mustResult, keyPlayer);
    }
}
