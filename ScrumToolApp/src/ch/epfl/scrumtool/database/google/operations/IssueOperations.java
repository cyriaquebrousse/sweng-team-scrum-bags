package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertIssueArgs;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.IssueConverters;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.InsertException;
import ch.epfl.scrumtool.exception.LoadException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;

/**
 * Operations for Issue
 * 
 * @author vincent
 *
 */
public class IssueOperations {
    
    public static final ScrumToolOperation<InsertIssueArgs, InsertResponse<Issue>> INSERT_ISSUE_MAINTASK = 
            new ScrumToolOperation<InsertIssueArgs, InsertResponse<Issue>>() {
        @Override
        public InsertResponse<Issue> execute(InsertIssueArgs arg, Scrumtool service) throws ScrumToolException {
            try {
                final String playerKey;
                if (arg.getIssue().getPlayer() != null) {
                    playerKey = arg.getIssue().getPlayer().getKey();
                } else {
                    playerKey = null;
                }
                final String sprintKey;
                if (arg.getIssue().getSprint() != null) {
                    sprintKey = arg.getIssue().getSprint().getKey();
                } else {
                    sprintKey = null;
                }
                ScrumIssue insert = IssueConverters.ISSUE_TO_SCRUMISSUE_INSERT.convert(arg.getIssue());
                insert.setLastModUser(Session.getCurrentSession().getUser().getEmail());
                return new InsertResponse<Issue>(arg.getIssue(),
                        service.insertScrumIssue(arg.getKey(), insert)
                        .setPlayerKey(playerKey).setSprintKey(sprintKey).execute());
                
            } catch (IOException e) {
                throw new InsertException(e, "Issue insertion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<InsertIssueArgs, OperationStatus> INSERT_ISSUE_SPRINT = 
            new ScrumToolOperation<InsertIssueArgs, OperationStatus>() {
        @Override
        public OperationStatus execute(InsertIssueArgs arg, Scrumtool service) throws ScrumToolException {
            try {
                ScrumIssue insert = IssueConverters.ISSUE_TO_SCRUMISSUE_INSERT.convert(arg.getIssue());
                insert.setLastModUser(Session.getCurrentSession().getUser().getEmail());
                return service.insertIssueInSprint(arg.getIssue().getKey(), arg.getKey()).execute();
                
            } catch (IOException e) {
                throw new InsertException(e, "Issue insertion failed");
            }
        }
    };

//    public static final ScrumToolOperation<Issue, OperationStatus> UPDATE_ISSUE = 
//            new ScrumToolOperation<Issue, OperationStatus>() {
//        @Override
//        public OperationStatus execute(Issue arg, Scrumtool service) throws ScrumToolException {
//            ScrumIssue scrumIssue = IssueConverters.ISSUE_TO_SCRUMISSUE.convert(arg);
//            try {
//                return service.updateScrumIssue(scrumIssue).execute();
//            } catch (IOException e) {
//                throw new UpdateException(e, "Issue update failed");
//            }
//        }
//    };
    
    public static final ScrumToolOperation<Issue, OperationStatus> UPDATE_ISSUE = 
          new ScrumToolOperation<Issue, OperationStatus>() {
        @Override
        public OperationStatus execute(Issue arg, Scrumtool service) throws ScrumToolException {
         
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
                try {
                    return service.updateScrumIssue(scrumIssue)
                          .setPlayerKey(playerKey).setSprintKey(sprintKey).execute();
                } catch (IOException e) {
                    throw new UpdateException(e, "Issue update failed");
                }
        }
    };
    
    
    public static final ScrumToolOperation<String, OperationStatus> DELETE_ISSUE = 
            new ScrumToolOperation<String, OperationStatus>() {
        @Override
        public OperationStatus execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.removeScrumIssue(arg).execute();
            } catch (IOException e) {
                throw new DeleteException(e, "Issue deletion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_MAINTASK = 
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {

        @Override
        public CollectionResponseScrumIssue execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.loadIssuesByMainTask(arg).execute();
            } catch (IOException e) {
                throw new LoadException(e, "Loading issue list failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_SPRINT = 
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {

        @Override
        public CollectionResponseScrumIssue execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.loadIssuesBySprint(arg).execute();
            } catch (IOException e) {
                throw new LoadException(e, "Loading issue list failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_NO_SPRINT =
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {

            @Override
            public CollectionResponseScrumIssue execute(String arg,
                        Scrumtool service) throws ScrumToolException {
                try {
                    return service.loadUnsprintedIssuesForProject(arg).execute();
                } catch (IOException e) {
                    throw new LoadException(e, "Loading issue list failed");
                }
            }
        
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumIssue> LOAD_ISSUES_USER = 
            new ScrumToolOperation<String, CollectionResponseScrumIssue>() {
        @Override
        public CollectionResponseScrumIssue execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.loadIssuesForUser(arg).execute();
            } catch (IOException e) {
                throw new ScrumToolException(e, "Error loading issues for user");
            }
        }
    };
}
