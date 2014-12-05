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
            final String playerKey;
            if (arg.getEntity().getPlayer() != null) {
                playerKey = arg.getEntity().getPlayer().getKey();
            } else {
                playerKey = null;
            }
            final String sprintKey;
            if (arg.getEntity().getSprint() != null) {
                sprintKey = arg.getEntity().getSprint().getKey();
            } else {
                sprintKey = null;
            }
            ScrumIssue insert = IssueConverters.ISSUE_TO_SCRUMISSUE_INSERT.convert(arg.getEntity());
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
                ScrumIssue insert = IssueConverters.ISSUE_TO_SCRUMISSUE_INSERT.convert(arg.getEntity());
                insert.setLastModUser(Session.getCurrentSession().getUser().getEmail());
                return service.insertIssueInSprint(arg.getEntity().getKey(), arg.getKey()).execute();
        }
    };

    
    public static final ScrumToolOperation<Issue, Void> UPDATE_ISSUE = 
          new ScrumToolOperation<Issue, Void>() {
        @Override
        public Void operation(Issue arg, Scrumtool service) throws ScrumToolException, IOException {
         
                final String playerKey;
                if (arg.getPlayer() != null) {
                    playerKey = arg.getPlayer().getKey();
                } else {
                    playerKey = null;
                }
                final String sprintKey;
                if (arg.getSprint() != null) {
                    sprintKey = arg.getSprint().getKey();
                } else {
                    sprintKey = null;
                }
                ScrumIssue scrumIssue = IssueConverters.ISSUE_TO_SCRUMISSUE_UPDATE.convert(arg);
                scrumIssue.setLastModUser(Session.getCurrentSession().getUser().getEmail());
                return service.updateScrumIssue(scrumIssue)
                          .setPlayerKey(playerKey).setSprintKey(sprintKey).execute();
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
