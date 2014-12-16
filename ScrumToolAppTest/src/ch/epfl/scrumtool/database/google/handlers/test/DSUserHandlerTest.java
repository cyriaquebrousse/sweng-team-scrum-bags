package ch.epfl.scrumtool.database.google.handlers.test;

import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.handlers.DSUserHandler;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * These test verify if the operation gets called and therefore if we get a callback.
 * The data integrity tests, operation tests etc can be found in the corresponding unit tests
 * 
 * @author aschneuw
 *
 */
public class DSUserHandlerTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    private static final DSUserHandler HANDLER = new DSUserHandler();

    protected void setUp() {
        new GoogleSession(ServerClientEntities.generateBasicUser(), SCRUMTOOL);
        
    }

    protected void tearDown() {
        Session.destroySession();
    }

    public void testLoginUser() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<User> callback = new HandlerTestCallback<User>(signal) {
            @Override
            public void interactionDone(User object) {
                setSuccess(object.equals(ServerClientEntities.generateBasicUser()));
                super.interactionDone(object);
            }
        };
        
        HANDLER.loginUser(ServerClientEntities.generateBasicUser().getEmail(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }

    public void testUpdate() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.update(ServerClientEntities.generateBasicUser(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }

    public void testRemove() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.remove(ServerClientEntities.generateBasicUser(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }

    public void testLoad() {
        try {
            HANDLER.load(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }

    public void testInsert() {
        try {
            HANDLER.insert(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
}
