package ch.epfl.scrumtool.database.google.handlers.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.handlers.DSProjectHandler;
import ch.epfl.scrumtool.entity.Project;
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
public class DSProjectHandlerTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    private static final DSProjectHandler HANDLER = new DSProjectHandler();
    
    
    public void setUp() {
        new GoogleSession(ServerClientEntities.generateBasicUser(), SCRUMTOOL);
    }

    public void testInsert() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Project> callback = new HandlerTestCallback<Project>(signal) {
            @Override
            public void interactionDone(Project object) {
                setSuccess(object.equals(ServerClientEntities.generateBasicProject()));
                super.interactionDone(object);
            }
        };
        
        HANDLER.insert(ServerClientEntities.generateBasicProject(), callback);
        
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

    public void testUpdate() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.update(ServerClientEntities.generateBasicProject(), callback);
        
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
        
        HANDLER.remove(ServerClientEntities.generateBasicProject(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }

    public void testLoadProjects() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<Project>> callback = new HandlerTestCallback<List<Project>>(signal) {
            @Override
            public void interactionDone(List<Project> v) {
                boolean success = true;
                
                for (Project i: v) {
                    if (!i.equals(ServerClientEntities.generateBasicProject())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadProjects(callback);
        
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
