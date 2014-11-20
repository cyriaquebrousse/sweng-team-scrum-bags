package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertSprintArgs;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusEntity;
import ch.epfl.scrumtool.database.google.conversion.SprintConverters;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.InsertException;
import ch.epfl.scrumtool.exception.LoadException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * Operations for Sprint
 * 
 * @author vincent
 *
 */
public class SprintOperations {
    public static final ScrumToolOperation<Sprint, OperationStatus> UPDATE_SPRINT = 
            new ScrumToolOperation<Sprint, OperationStatus>() {
        @Override
        public OperationStatus execute(Sprint arg, Scrumtool service) throws ScrumToolException {
            ScrumSprint scrumSprint = SprintConverters.SPRINT_TO_SCRUMSPRINT.convert(arg);
            try {
                return service.updateScrumSprint(scrumSprint).execute();
            } catch (IOException e) {
                throw new UpdateException(e, "Sprint update failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, OperationStatus> DELETE_SPRINT = 
            new ScrumToolOperation<String, OperationStatus>() {
        @Override
        public OperationStatus execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.removeScrumSprint(arg).execute();
            } catch (IOException e) {
                throw new DeleteException(e, "Sprint deletion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<InsertSprintArgs, OperationStatusEntity<Sprint>> INSERT_SPRINT =
            new ScrumToolOperation<InsertSprintArgs, OperationStatusEntity<Sprint>>() {
                
        @Override
        public OperationStatusEntity<Sprint> execute(InsertSprintArgs arg,
                Scrumtool service) throws ScrumToolException {
            try {
                ScrumSprint scrumSprint = SprintConverters.SPRINT_TO_SCRUMSPRINT.convert(arg.getSprint());
                OperationStatus opStat = service.insertScrumSprint(arg.getProjectKey(), scrumSprint).execute();
                OperationStatusEntity<Sprint> response = new OperationStatusEntity<Sprint>(arg.getSprint(), opStat);
                return response;
            } catch (IOException e) {
                throw new InsertException(e, "Sprint Insertion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumSprint> LOAD_SPRINT = 
            new ScrumToolOperation<String, CollectionResponseScrumSprint>() {
        @Override
        public CollectionResponseScrumSprint execute(String arg, Scrumtool service)
                throws ScrumToolException {
            try {
                return service.loadSprints(arg).execute();
            } catch (IOException e) {
                throw new LoadException(e, "Loading sprints failed");
            }
        }
    };
    
}
