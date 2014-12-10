package ch.epfl.scrumtool.database.google.converters;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.PlayerConverters;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.utils.TestConstants;

public class PlayerConvertersTest extends TestCase {
    public void testNullKey() {
        try {
            ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
            player.setKey(null);

            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullUser(){
        try {
            ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
            player.setUser(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullAdminFlag() {
        try {
            ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
            player.setAdminFlag(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullInvitedFlag() {
        try {
            ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
            player.setInvitedFlag(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullRole() {
        try {
            ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
            player.setRole(null);
            PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            fail("NullPointerException for invalid ScrumPlayer expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testValidPlayer() {
            ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
            Player result = PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
            assertEquals(TestConstants.generateBasicPlayer(), result);
    }
    
    public void testValidPlayerWithProject() {
        ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
        player.setProject(TestConstants.generateBasicScrumProject());
        Player mustResult = TestConstants.generateBasicPlayer()
                .getBuilder()
                .setProject(TestConstants.generateBasicProject())
                .build();
        Player result = PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(player);
        assertEquals(mustResult, result);
    }
    
    public void testToScrumPlayerEmptyKey() {
        Player player = TestConstants.generateBasicPlayer();
        player = player
                .getBuilder()
                .setKey("")
                .build();
        ScrumPlayer result = PlayerConverters.PLAYER_TO_SCRUMPLAYER.convert(player);
        ScrumPlayer mustResult = TestConstants.generateBasicScrumPlayer();
        mustResult.setKey(null);
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getRole(), result.getRole());
        assertEquals(mustResult.getAdminFlag(), result.getAdminFlag());
        assertEquals(mustResult.getProject(), result.getProject());
        assertEquals(mustResult.getUser(), result.getUser());
    }
    
    public void testToScrumPlayerWithKey() {
        Player player = TestConstants.generateBasicPlayer();
        ScrumPlayer result = PlayerConverters.PLAYER_TO_SCRUMPLAYER.convert(player);
        ScrumPlayer mustResult = TestConstants.generateBasicScrumPlayer();
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getRole(), result.getRole());
        assertEquals(mustResult.getAdminFlag(), result.getAdminFlag());
        assertEquals(mustResult.getProject(), result.getProject());
        assertEquals(mustResult.getUser(), result.getUser());
    }
    
    public void testInsertResponse() {
        KeyResponse response = new KeyResponse();
        response.setKey(TestConstants.VALIDKEY);

        Player player = TestConstants.generateBasicPlayer();
        player = player.getBuilder()
                .setKey("")
                .build();

        InsertResponse<Player> insresp = new InsertResponse<Player>(player, response);
        
        Player mustResult = TestConstants.generateBasicPlayer();

        Player keyPlayer = PlayerConverters.INSERTRESPONSE_TO_PLAYER.convert(insresp);
        assertEquals(mustResult, keyPlayer);
    }
}
