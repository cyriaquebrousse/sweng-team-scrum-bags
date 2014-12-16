package ch.epfl.scrumtool.database.google.handlers.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.handlers.DSPlayerHandler;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * These test verify if the operation gets called and therefore if we get a callback.
 * The data integrity tests, operation tests etc can be found in the corresponding unit tests
 * @author aschneuw
 *
 */
public class DSPlayerHandlerTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    private static final DSPlayerHandler HANDLER = new DSPlayerHandler();
    
    public void setUp() {
        new GoogleSession(ServerClientEntities.generateBasicUser(), SCRUMTOOL);
    }
    
    public void testAddPlayerToProject() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Player> callback = new HandlerTestCallback<Player>(signal) {
            @Override
            public void interactionDone(Player object) {
                Player mustOutput = ServerClientEntities.generateBasicPlayer();
                User.Builder builder = ServerClientEntities.generateBasicUser().getBuilder();
                builder.setName(builder.getEmail());
                
                mustOutput = mustOutput.getBuilder().setIsInvited(true).setUser(builder.build()).build();
                setSuccess(object.equals(mustOutput));
                super.interactionDone(object);
            }
        };
        
        HANDLER.addPlayerToProject(ServerClientEntities.generateBasicProject(),
                ServerClientEntities.generateBasicUser().getEmail(), Role.DEVELOPER, callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testInsertPlayer() {
        try {
            HANDLER.insert(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testInsertPlayerToProject() {
        try {
            HANDLER.insert(null, null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testLoadInvitedPlayer() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<Player>> callback = new HandlerTestCallback<List<Player>>(signal) {
            @Override
            public void interactionDone(List<Player> v) {
                boolean success = true;
                
                for (Player i: v) {
                    if (!i.equals(ServerClientEntities.generateBasicPlayer())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadInvitedPlayers(callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testLoadPlayersForProject() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<Player>> callback = new HandlerTestCallback<List<Player>>(signal) {
            @Override
            public void interactionDone(List<Player> v) {
                boolean success = true;
                
                for (Player i: v) {
                    if (!i.equals(ServerClientEntities.generateBasicPlayer())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadPlayers(ServerClientEntities.generateBasicProject(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testLoadPlayers() {
        try {
            HANDLER.load(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testRemovePlayer() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.update(ServerClientEntities.generateBasicPlayer(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testUpdatePlayer() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.remove(ServerClientEntities.generateBasicPlayer(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testSetPlayerAsAdmin() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.setPlayerAsAdmin(ServerClientEntities.generateBasicPlayer(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void tearDown() {
        Session.destroySession();
    }
}
