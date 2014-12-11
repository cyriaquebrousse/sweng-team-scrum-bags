package ch.epfl.scrumtool.database.test;

import junit.framework.TestCase;

import org.junit.Test;

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
    
    @Test
    public void testBuilderSetGetUserHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setUserHandler(USER_HANDLER);
        assertEquals(builder.getUserHandler(), USER_HANDLER);
    }
    
    @Test
    public void testBuilderSetGetProjectHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setProjectHandler(PROJECT_HANDLER);
        assertEquals(builder.getProjectHandler(), PROJECT_HANDLER);
    }
    
    @Test
    public void testBuilderSetGetPlayerHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setPlayerHandler(PLAYER_HANDLER);
        assertEquals(builder.getPlayerHandler(), PLAYER_HANDLER);
    }
    
    @Test
    public void testBuilderSetGetMainTaskHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setMaintaskHandler(MAINTASK_HANDLER);
        assertEquals(builder.getMainTaskHandler(), MAINTASK_HANDLER);
    }

    @Test
    public void testBuilderSetGetIssueHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(ISSUE_HANDLER);
        assertEquals(builder.getIssueHandler(), ISSUE_HANDLER);
    }
    
    @Test
    public void testBuilderSetGetSprintHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setSprintHandler(SPRINT_HANDLER);
        assertEquals(builder.getSprintHandler(), SPRINT_HANDLER);
    }
    
    @Test(expected = NullPointerException.class)
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
    
    @Test
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
    
    @Test(expected = NullPointerException.class)
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
    
    @Test(expected = NullPointerException.class)
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
    
    
    @Test(expected = NullPointerException.class)
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
    
    @Test(expected = NullPointerException.class)
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
    
    @Test
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
    
    @Test
    public void testGetIssueHandler() {
        assertEquals(HANDLERS.getIssueHandler(), ISSUE_HANDLER);
    }

    @Test
    public void testGetMainTaskHandler() {
        assertEquals(HANDLERS.getMainTaskHandler(), MAINTASK_HANDLER);
    }

    @Test
    public void testGetPlayerHandler() {
        assertEquals(HANDLERS.getPlayerHandler(), PLAYER_HANDLER);
    }

    @Test
    public void testGetProjectHandler() {
        assertEquals(HANDLERS.getProjectHandler(), PROJECT_HANDLER);
    }

    @Test
    public void testGetSprintHandler() {
        assertEquals(HANDLERS.getSprintHandler(), SPRINT_HANDLER);
    }

    @Test
    public void testGetUserHandler() {
        assertEquals(HANDLERS.getUserHandler(), USER_HANDLER);
    }
}
