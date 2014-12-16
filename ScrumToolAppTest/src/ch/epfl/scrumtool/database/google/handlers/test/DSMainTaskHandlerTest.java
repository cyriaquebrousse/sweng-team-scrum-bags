package ch.epfl.scrumtool.database.google.handlers.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.handlers.DSMainTaskHandler;
import ch.epfl.scrumtool.entity.MainTask;
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
public class DSMainTaskHandlerTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    private static final DSMainTaskHandler HANDLER = new DSMainTaskHandler();

    
    public void setUp() {
        new GoogleSession(ServerClientEntities.generateBasicUser(), SCRUMTOOL);
    }
    
    public void testLoad() {
        try {
            HANDLER.insert(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testInsert() {
        try {
            HANDLER.load(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testInsertMainTaskToProject() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<MainTask> callback = new HandlerTestCallback<MainTask>(signal) {
            @Override
            public void interactionDone(MainTask object) {
                setSuccess(object.equals(ServerClientEntities.generateBasicMainTask()));
                super.interactionDone(object);
            }
        };
        
        HANDLER.insert(ServerClientEntities.generateBasicMainTask(),
                ServerClientEntities.generateBasicProject(),
                callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testLoadMainTaskByProject() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<MainTask>> callback = new HandlerTestCallback<List<MainTask>>(signal) {
            @Override
            public void interactionDone(List<MainTask> v) {
                boolean success = true;
                
                for (MainTask i: v) {
                    if (!i.equals(ServerClientEntities.generateBasicMainTask())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadMainTasks(ServerClientEntities.generateBasicProject(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testRemoveMainTask() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        HANDLER.remove(ServerClientEntities.generateBasicMainTask(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testUpdateMainTask() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.update(ServerClientEntities.generateBasicMainTask(), callback);
        
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
