package ch.epfl.scrumtool.database.google.operations.test;

import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import ch.epfl.scrumtool.database.google.operations.IssueOperations;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.gui.utils.test.MockScrumTool;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import junit.framework.TestCase;

/**
 * These are only very basic tests to verify the operation functionality.
 * They are only used with a mockScrumTool class which returns bogus data.
 * The actual server testing which tests the data integrity is done on the server side
 * @author aschneuw
 *
 */
public class IssueOperationsTest extends TestCase {
    private static final Scrumtool SCRUMTOOL = new MockScrumTool();
    
    
    public void testDeleteIssue() {
        try {
            assertNull(IssueOperations.DELETE_ISSUE.execute("", SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testInsertIssueMainTask() {
        try {
            new Session(ServerClientEntities.generateBasicUser()){};
            EntityKeyArg<Issue> arg =
                    new EntityKeyArg<Issue>(ServerClientEntities.generateBasicIssue(), ServerClientEntities.VALIDKEY);
            assertNotNull(IssueOperations.INSERT_ISSUE_MAINTASK.execute(arg, SCRUMTOOL));
        } catch (ScrumToolException e) {
            
        } finally {
            Session.destroySession();
        }
    }
    
    public void testInsertIssueMainTaskWithPlayerAndSprint() {
        try {
            new Session(ServerClientEntities.generateBasicUser()){};
            Issue issue = ServerClientEntities.generateBasicIssue();
            issue = issue.getBuilder()
                    .setPlayer(ServerClientEntities
                            .generateBasicPlayer())
                            .setSprint(ServerClientEntities.generateBasicSprint())
                            .build();
            EntityKeyArg<Issue> arg =
                    new EntityKeyArg<Issue>(issue, ServerClientEntities.VALIDKEY);
            assertNotNull(IssueOperations.INSERT_ISSUE_MAINTASK.execute(arg, SCRUMTOOL));
        } catch (ScrumToolException e) {
            
        } finally {
            Session.destroySession();
        }
    }
    
    public void testInsertIssueSprint() {
        try {
            EntityKeyArg<Issue> arg =
                    new EntityKeyArg<Issue>(ServerClientEntities.generateBasicIssue(), ServerClientEntities.VALIDKEY);
            assertNull(IssueOperations.INSERT_ISSUE_SPRINT.execute(arg, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testloadIssuesMainTask() {
        try {
            assertNotNull(IssueOperations.LOAD_ISSUES_MAINTASK.execute(ServerClientEntities.VALIDKEY, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testloadIssuesNoSprint() {
        try {
            assertNotNull(IssueOperations.LOAD_ISSUES_NO_SPRINT.execute(ServerClientEntities.VALIDKEY, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoadIssuesSprint() {
        try {
            assertNotNull(IssueOperations.LOAD_ISSUES_SPRINT.execute(ServerClientEntities.VALIDKEY, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testLoadIssuesUser() {
        try {
            assertNotNull(IssueOperations.LOAD_ISSUES_USER.execute(ServerClientEntities.VALIDKEY, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testUpdateIssue() {
        try {
            assertNull(IssueOperations.UPDATE_ISSUE.execute(ServerClientEntities.generateBasicIssue()
                    , SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
    
    public void testUpdateIssuePlayerSprint() {
        try {
            Issue issue = ServerClientEntities.generateBasicIssue();
            issue = issue.getBuilder()
                    .setPlayer(ServerClientEntities
                            .generateBasicPlayer())
                            .setSprint(ServerClientEntities.generateBasicSprint())
                            .build();
            assertNull(IssueOperations.UPDATE_ISSUE.execute(issue, SCRUMTOOL));
        } catch (ScrumToolException e) {
            fail();
        }
    }
}
