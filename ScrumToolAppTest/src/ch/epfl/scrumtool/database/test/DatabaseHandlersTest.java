package ch.epfl.scrumtool.database.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.DatabaseHandlers;
import ch.epfl.scrumtool.database.IssueHandler;
import ch.epfl.scrumtool.database.MainTaskHandler;
import ch.epfl.scrumtool.database.PlayerHandler;
import ch.epfl.scrumtool.database.ProjectHandler;
import ch.epfl.scrumtool.database.SprintHandler;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.database.google.handlers.DSIssueHandler;
import ch.epfl.scrumtool.database.google.handlers.DSMainTaskHandler;
import ch.epfl.scrumtool.database.google.handlers.DSPlayerHandler;
import ch.epfl.scrumtool.database.google.handlers.DSProjectHandler;
import ch.epfl.scrumtool.database.google.handlers.DSSprintHandler;
import ch.epfl.scrumtool.database.google.handlers.DSUserHandler;
/**
 * 
 * @author aschneuw
 *
 */
public class DatabaseHandlersTest extends TestCase {
    private static final UserHandler USER_HANDLER = new DSUserHandler();
    private static final MainTaskHandler MAINTASK_HANDLER = new DSMainTaskHandler();
    private static final PlayerHandler PLAYER_HANDLER = new DSPlayerHandler();
    private static final ProjectHandler PROJECT_HANDLER = new DSProjectHandler();
    private static final SprintHandler SPRINT_HANDLER = new DSSprintHandler();
    private static final IssueHandler ISSUE_HANDLER = new DSIssueHandler();
    
    private static final DatabaseHandlers HANDLERS = new DatabaseHandlers.Builder()
        .setIssueHandler(ISSUE_HANDLER)
        .setMaintaskHandler(MAINTASK_HANDLER)
        .setPlayerHandler(PLAYER_HANDLER)
        .setProjectHandler(PROJECT_HANDLER)
        .setSprintHandler(SPRINT_HANDLER)
        .setUserHandler(USER_HANDLER)
        .build();
    
    public void testBuilderSetGetUserHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setUserHandler(USER_HANDLER);
        assertEquals(builder.getUserHandler(), USER_HANDLER);
    }
    
    public void testBuilderSetGetProjectHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setProjectHandler(PROJECT_HANDLER);
        assertEquals(builder.getProjectHandler(), PROJECT_HANDLER);
    }
    
    public void testBuilderSetGetPlayerHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setPlayerHandler(PLAYER_HANDLER);
        assertEquals(builder.getPlayerHandler(), PLAYER_HANDLER);
    }
    
    public void testBuilderSetGetMainTaskHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setMaintaskHandler(MAINTASK_HANDLER);
        assertEquals(builder.getMainTaskHandler(), MAINTASK_HANDLER);
    }

    public void testBuilderSetGetIssueHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER);
        assertEquals(builder.getIssueHandler(), ISSUE_HANDLER);
    }
    
    public void testBuilderSetGetSprintHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setSprintHandler(SPRINT_HANDLER);
        assertEquals(builder.getSprintHandler(), SPRINT_HANDLER);
    }
    
    public void testBuilderBuildNullIssue() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setMaintaskHandler(MAINTASK_HANDLER)
            .setPlayerHandler(PLAYER_HANDLER)
            .setProjectHandler(PROJECT_HANDLER)
            .setSprintHandler(SPRINT_HANDLER)
            .setUserHandler(USER_HANDLER);
        try {
            builder.build();
            fail("NullPointerException expected for missing IssueHandler");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testBuilderBuildNullMainTask() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER)
            .setPlayerHandler(PLAYER_HANDLER)
            .setProjectHandler(PROJECT_HANDLER)
            .setSprintHandler(SPRINT_HANDLER)
            .setUserHandler(USER_HANDLER);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testBuilderBuildNullPlayer() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER)
            .setMaintaskHandler(MAINTASK_HANDLER)
            .setProjectHandler(PROJECT_HANDLER)
            .setSprintHandler(SPRINT_HANDLER)
            .setUserHandler(USER_HANDLER);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testBuilderBuildNullProject() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER)
            .setMaintaskHandler(MAINTASK_HANDLER)
            .setPlayerHandler(PLAYER_HANDLER)
            .setSprintHandler(SPRINT_HANDLER)
            .setUserHandler(USER_HANDLER);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testBuilderBuildNullSprint() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER)
            .setMaintaskHandler(MAINTASK_HANDLER)
            .setPlayerHandler(PLAYER_HANDLER)
            .setProjectHandler(PROJECT_HANDLER)
            .setUserHandler(USER_HANDLER);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testBuilderBuildNullUser() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER)
            .setMaintaskHandler(MAINTASK_HANDLER)
            .setPlayerHandler(PLAYER_HANDLER)
            .setProjectHandler(PROJECT_HANDLER)
            .setSprintHandler(SPRINT_HANDLER);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testBuilderBuild() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER)
            .setMaintaskHandler(MAINTASK_HANDLER)
            .setPlayerHandler(PLAYER_HANDLER)
            .setProjectHandler(PROJECT_HANDLER)
            .setSprintHandler(SPRINT_HANDLER)
            .setUserHandler(USER_HANDLER);
        builder.build();
    }
    
    public void testGetIssueHandler() {
        assertEquals(HANDLERS.getIssueHandler(), ISSUE_HANDLER);
    }

    public void testGetMainTaskHandler() {
        assertEquals(HANDLERS.getMainTaskHandler(), MAINTASK_HANDLER);
    }

    public void testGetPlayerHandler() {
        assertEquals(HANDLERS.getPlayerHandler(), PLAYER_HANDLER);
    }

    public void testGetProjectHandler() {
        assertEquals(HANDLERS.getProjectHandler(), PROJECT_HANDLER);
    }

    public void testGetSprintHandler() {
        assertEquals(HANDLERS.getSprintHandler(), SPRINT_HANDLER);
    }

    public void testGetUserHandler() {
        assertEquals(HANDLERS.getUserHandler(), USER_HANDLER);
    }
}
