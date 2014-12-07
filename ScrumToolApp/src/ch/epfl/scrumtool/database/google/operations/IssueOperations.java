package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.IssueConverters;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;

/**
 * Operations for Issue
 * 
 * @author vincent
 */
public class IssueOperations {
    
    public static final ScrumToolOperation<EntityKeyArg<Issue>, InsertResponse<Issue>> INSERT_ISSUE_MAINTASK = 
            new ScrumToolOperation<EntityKeyArg<Issue>, InsertResponse<Issue>>() {
        @Override
        public InsertResponse<Issue> operation(EntityKeyArg<Issue> arg, Scrumtool service)
                throws IOException, ScrumToolException {
            
            ScrumIssue insert = IssueConverters.ISSUE_TO_SCRUMISSUE.convert(arg.getEntity());
            
            final String playerKey;
            if (insert.getAssignedPlayer() != null) {
                playerKey = insert.getAssignedPlayer().getKey();
                //Set player to null -> we don't need to change the object on the database again
                insert.setAssignedPlayer(null);
            } else {
                playerKey = null;
            }
            final String sprintKey;
            if (insert.getSprint() != null) {
                sprintKey = insert.getSprint().getKey();
              //Set sprint to null -> we don't need to change the object on the database again
                insert.setSprint(null);
            } else {
                sprintKey = null;
            }
            
            insert.setLastModUser(Session.getCurrentSession().getUser().getEmail());
            return new InsertResponse<Issue>(arg.getEntity(),
                    service.insertScrumIssue(arg.getKey(), insert)
                    .setPlayerKey(playerKey).setSprintKey(sprintKey).execute());
        }
    };
    
    public static final ScrumToolOperation<EntityKeyArg<Issue>, Void> INSERT_ISSUE_SPRINT = 
            new ScrumToolOperation<EntityKeyArg<Issue>, Void>() {
        @Override
        public Void operation(EntityKeyArg<Issue> arg, Scrumtool service) throws IOException, ScrumToolException {
                return service.insertIssueInSprint(arg.getEntity().getKey(), arg.getKey()).execute();
        }
    };

    
    public static final ScrumToolOperation<Issue, Void> UPDATE_ISSUE = 
          new ScrumToolOperation<Issue, Void>() {
        @Override
        public Void operation(Issue arg, Scrumtool service) throws ScrumToolException, IOException {
                ScrumIssue scrumIssue = IssueConverters.ISSUE_TO_SCRUMISSUE.convert(arg);
                
                final String playerKey;
                if (scrumIssue.getAssignedPlayer() != null) {
                    playerKey = scrumIssue.getAssignedPlayer().getKey();
                    scrumIssue.setAssignedPlayer(null);
                } else {
                    playerKey = null;
                }
                final String sprintKey;
                if (scrumIssue.getSprint() != null) {
                    sprintKey = arg.getSprint().getKey();
                    scrumIssue.setSprint(null);
                } else {
                    sprintKey = null;
                }
                return service.updateScrumIssue(scrumIssue)
                        .setPlayerKey(playerKey)
                        .setSprintKey(sprintKey)
                        .execute();
        }
    };
    
    
    public static final ScrumToolOperation<String, Void> DELETE_ISSUE = 
            new ScrumToolOperation<String, Void>() {
        @Override
        public Void operation(String arg, Scrumtool service) throws IOException {
                return service.removeScrumIssue(arg).execute();
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_MAINTASK = 
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {

        @Override
        public CollectionResponseScrumIssue operation(String arg, Scrumtool service) throws IOException {
                return service.loadIssuesByMainTask(arg).execute();
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_SPRINT = 
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {

        @Override
        public CollectionResponseScrumIssue operation(String arg, Scrumtool service) throws IOException {
                return service.loadIssuesBySprint(arg).execute();
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_NO_SPRINT =
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {

            @Override
            public CollectionResponseScrumIssue operation(String arg, Scrumtool service) throws IOException {
                return service.loadUnsprintedIssuesForProject(arg).execute();
            }
        
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_USER = 
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {
        @Override
        public CollectionResponseScrumIssue operation(String arg, Scrumtool service) throws IOException {
                return service.loadIssuesForUser(arg).execute();
        }
    };
}
