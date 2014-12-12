package ch.epfl.scrumtool.database.google.handlers.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.handlers.DSSprintHandler;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 *
 */
public class DSSprintHandlerTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    private static final DSSprintHandler HANDLER = new DSSprintHandler(); 

    public void setUp() {
        new GoogleSession(ServerClientEntities.generateBasicUser(), SCRUMTOOL);
    }
    
    public void tearDown() {
        Session.destroySession();
        
    }
    
    public void testInsert() {
        try {
            HANDLER.insert(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testInsertToSprint() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Sprint> callback = new HandlerTestCallback<Sprint>(signal) {
            @Override
            public void interactionDone(Sprint object) {
                setSuccess(object.equals(ServerClientEntities.generateBasicSprint()));
                super.interactionDone(object);
            }
        };
        
        HANDLER.insert(ServerClientEntities.generateBasicSprint(),
                ServerClientEntities.generateBasicProject(), callback);
        
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
    
    public void testLoadByProject() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<Sprint>> callback = new HandlerTestCallback<List<Sprint>>(signal) {
            @Override
            public void interactionDone(List<Sprint> v) {
                boolean success = true;
                
                for (Sprint i: v) {
                    if (!i.equals(ServerClientEntities.generateBasicSprint())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadSprints(ServerClientEntities.generateBasicProject(), callback);
        
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
        
        HANDLER.remove(ServerClientEntities.generateBasicSprint(), callback);
        
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
        
        HANDLER.update(ServerClientEntities.generateBasicSprint(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
}
