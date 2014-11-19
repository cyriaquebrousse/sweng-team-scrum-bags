package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.conversion.IssueConverters;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;

/**
 * Operations for Issue
 * 
 * @author vincent
 *
 */
public class IssueOperations {

    public static final ScrumToolOperation<Issue, OperationStatus> UPDATE_ISSUE = 
            new ScrumToolOperation<Issue, OperationStatus>() {
        @Override
        public OperationStatus execute(Issue arg, Scrumtool service) throws ScrumToolException {
            ScrumIssue scrumIssue = IssueConverters.ISSUE_TO_SCRUMISSUE.convert(arg);
            try {
                return service.updateScrumIssue(scrumIssue).execute();
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
}
