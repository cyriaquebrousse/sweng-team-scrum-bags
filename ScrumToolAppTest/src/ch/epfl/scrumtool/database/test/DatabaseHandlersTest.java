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

public class DatabaseHandlersTest extends TestCase {
    private static final UserHandler userHandler = new DSUserHandler();
    private static final MainTaskHandler mainTaskHandler = new DSMainTaskHandler();
    private static final PlayerHandler playerHandler = new DSPlayerHandler();
    private static final ProjectHandler projectHandler = new DSProjectHandler();
    private static final SprintHandler sprintHandler = new DSSprintHandler();
    private static final IssueHandler issueHandler = new DSIssueHandler();
    
    private static final DatabaseHandlers handlers = new DatabaseHandlers.Builder()
        .setIssueHandler(issueHandler)
        .setMaintaskHandler(mainTaskHandler)
        .setPlayerHandler(playerHandler)
        .setProjectHandler(projectHandler)
        .setSprintHandler(sprintHandler)
        .setUserHandler(userHandler)
        .build();
    
    @Test
    public void testBuilderSetGetUserHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setUserHandler(userHandler);
        assertEquals(builder.getUserHandler(), userHandler);
    }
    
    @Test
    public void testBuilderSetGetProjectHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setProjectHandler(projectHandler);
        assertEquals(builder.getProjectHandler(), projectHandler);
    }
    
    @Test
    public void testBuilderSetGetPlayerHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setPlayerHandler(playerHandler);
        assertEquals(builder.getPlayerHandler(), playerHandler);
    }
    
    @Test
    public void testBuilderSetGetMainTaskHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setMaintaskHandler(mainTaskHandler);
        assertEquals(builder.getMainTaskHandler(), mainTaskHandler);
    }

    @Test
    public void testBuilderSetGetIssueHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(issueHandler);
        assertEquals(builder.getIssueHandler(), issueHandler);
    }
    
    @Test
    public void testBuilderSetGetSprintHandler() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setSprintHandler(sprintHandler);
        assertEquals(builder.getSprintHandler(), sprintHandler);
    }
    
    @Test(expected = NullPointerException.class)
    public void testBuilderBuildNullIssue() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setMaintaskHandler(mainTaskHandler)
            .setPlayerHandler(playerHandler)
            .setProjectHandler(projectHandler)
            .setSprintHandler(sprintHandler)
            .setUserHandler(userHandler);
        try {
            builder.build();
            fail("NullPointerException expected for missing IssueHandler");
        } catch (NullPointerException n) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
        
    }
    
    @Test
    public void testBuilderBuildNullMainTask() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(issueHandler)
            .setPlayerHandler(playerHandler)
            .setProjectHandler(projectHandler)
            .setSprintHandler(sprintHandler)
            .setUserHandler(userHandler);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }

    }
    
    @Test(expected = NullPointerException.class)
    public void testBuilderBuildNullPlayer() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(issueHandler)
            .setMaintaskHandler(mainTaskHandler)
            .setProjectHandler(projectHandler)
            .setSprintHandler(sprintHandler)
            .setUserHandler(userHandler);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
            
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void testBuilderBuildNullProject() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(issueHandler)
            .setMaintaskHandler(mainTaskHandler)
            .setPlayerHandler(playerHandler)
            .setSprintHandler(sprintHandler)
            .setUserHandler(userHandler);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    
    @Test(expected = NullPointerException.class)
    public void testBuilderBuildNullSprint() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(issueHandler)
            .setMaintaskHandler(mainTaskHandler)
            .setPlayerHandler(playerHandler)
            .setProjectHandler(projectHandler)
            .setUserHandler(userHandler);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void testBuilderBuildNullUser() throws NullPointerException {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(issueHandler)
            .setMaintaskHandler(mainTaskHandler)
            .setPlayerHandler(playerHandler)
            .setProjectHandler(projectHandler)
            .setSprintHandler(sprintHandler);
        try {
            builder.build();
            fail("NullPointerException expected for missing MainTaskHandler");
        } catch (NullPointerException n) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    @Test
    public void testBuilderBuild() {
        DatabaseHandlers.Builder builder = new DatabaseHandlers.Builder();
        builder.setIssueHandler(issueHandler)
            .setMaintaskHandler(mainTaskHandler)
            .setPlayerHandler(playerHandler)
            .setProjectHandler(projectHandler)
            .setSprintHandler(sprintHandler)
            .setUserHandler(userHandler);
        try {
            builder.build();
        } catch (Exception e) {
            fail("Unexpected Exception in the DatabaseHandlers constructor");
        }
    }
    
    @Test
    public void testGetIssueHandler() {
        assertEquals(handlers.getIssueHandler(), issueHandler);
    }

    @Test
    public void testGetMainTaskHandler() {
        assertEquals(handlers.getMainTaskHandler(), mainTaskHandler);
    }

    @Test
    public void testGetPlayerHandler() {
        assertEquals(handlers.getPlayerHandler(), playerHandler);
    }

    @Test
    public void testGetProjectHandler() {
        assertEquals(handlers.getProjectHandler(), projectHandler);
    }

    @Test
    public void testGetSprintHandler() {
        assertEquals(handlers.getSprintHandler(), sprintHandler);
    }

    @Test
    public void testGetUserHandler() {
        assertEquals(handlers.getUserHandler(), userHandler);
    }
}
