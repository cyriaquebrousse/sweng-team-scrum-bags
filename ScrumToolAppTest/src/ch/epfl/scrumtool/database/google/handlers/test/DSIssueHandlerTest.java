package ch.epfl.scrumtool.database.google.handlers.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.database.google.handlers.DSIssueHandler;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
/**
 *
 * These test verify if the operation gets called and therefore if we get a callback.
 * The data integrity tests, operation tests etc can be found in the corresponding unit tests
 * 
 * @author aschneuw
 *
 */
public class DSIssueHandlerTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    private static final DSIssueHandler HANDLER = new DSIssueHandler();

    
    public void setUp() {
        new GoogleSession(ServerClientEntities.generateBasicUser(), SCRUMTOOL);
    }
    
    public void testInsertIssueMainTask() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Issue> callback = new HandlerTestCallback<Issue>(signal) {
            @Override
            public void interactionDone(Issue object) {
                setSuccess(object.equals(ServerClientEntities.generateBasicIssue()));
                super.interactionDone(object);
            }
        };
        
        HANDLER.insert(ServerClientEntities.generateBasicIssue(),
                ServerClientEntities.generateBasicMainTask(),
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
    
    public void testLoadIssueByKey() {
        try {
            HANDLER.load(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testAssignIssueTosprint() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.assignIssueToSprint(ServerClientEntities.generateBasicIssue(),
                ServerClientEntities.generateBasicSprint(),
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
    
    public void testInsertIssue() {
        try {
            HANDLER.insert(null, null);
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    public void testLoadIssuesMainTask() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<Issue>> callback = new HandlerTestCallback<List<Issue>>(signal) {
            @Override
            public void interactionDone(List<Issue> v) {
                boolean success = true;
                
                for (Issue i: v) {
                    if (!i.equals(ServerClientEntities.generateBasicIssue())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadIssues(ServerClientEntities.generateBasicMainTask(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testLoadIssuesForUser() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<TaskIssueProject>> callback =
                new HandlerTestCallback<List<TaskIssueProject>>(signal) {
            @Override
            public void interactionDone(List<TaskIssueProject> v) {
                boolean success = true;
                
                for (TaskIssueProject i: v) {
                    if (!(i.getIssue().equals(ServerClientEntities.generateBasicIssue()))
                            && i.getMainTask().equals(ServerClientEntities.generateBasicMainTask())
                            && i.getProject().equals(ServerClientEntities.generateBasicScrumProject())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadIssuesForUser(ServerClientEntities.generateBasicUser(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testUnsprintedIssues() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<Issue>> callback = new HandlerTestCallback<List<Issue>>(signal) {
            @Override
            public void interactionDone(List<Issue> v) {
                boolean success = true;
                
                for (Issue i: v) {
                    if (!i.equals(ServerClientEntities.generateBasicIssue())) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadUnsprintedIssues(ServerClientEntities.generateBasicProject(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testRemoveIssue() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        HANDLER.remove(ServerClientEntities.generateBasicIssue(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testUpdateIssue() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<Void> callback = new HandlerTestCallback<Void>(signal) {
            @Override
            public void interactionDone(Void v) {
                setSuccess(true);
                super.interactionDone(v);
            }
        };
        
        HANDLER.update(ServerClientEntities.generateBasicIssue(), callback);
        
        try {
            signal.await();
            if (!callback.hasSuccess()) {
                fail("");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    public void testLoadIssuesForSprint() {
        final CountDownLatch signal = new CountDownLatch(1);
        final HandlerTestCallback<List<Issue>> callback =
                new HandlerTestCallback<List<Issue>>(signal) {
            @Override
            public void interactionDone(List<Issue> v) {
                boolean success = true;
                
                for (Issue i: v) {
                    if (!(i.equals(ServerClientEntities.generateBasicIssue()))) {
                        success = false;
                        break;
                    }
                }
                setSuccess(success);
                super.interactionDone(v);
            }
        };
        
        HANDLER.loadIssues(ServerClientEntities.generateBasicSprint(), callback);
        
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
