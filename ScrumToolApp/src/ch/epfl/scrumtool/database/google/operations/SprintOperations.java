package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.containers.InsertSprintArgs;
import ch.epfl.scrumtool.database.google.conversion.SprintConverters;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.InsertException;
import ch.epfl.scrumtool.exception.LoadException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * Operations for Sprint
 * 
 * @author vincent
 *
 */
public class SprintOperations {
    public static final ScrumToolOperation<Sprint, Void> UPDATE_SPRINT = 
            new ScrumToolOperation<Sprint, Void>() {
        @Override
        public Void execute(Sprint arg, Scrumtool service) throws ScrumToolException {
            ScrumSprint scrumSprint = SprintConverters.SPRINT_TO_SCRUMSPRINT.convert(arg);
            try {
                return service.updateScrumSprint(scrumSprint).execute();
            } catch (IOException e) {
                throw new UpdateException(e, "Sprint update failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, Void> DELETE_SPRINT = 
            new ScrumToolOperation<String, Void>() {
        @Override
        public Void execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.removeScrumSprint(arg).execute();
            } catch (IOException e) {
                throw new DeleteException(e, "Sprint deletion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<InsertSprintArgs, InsertResponse<Sprint>> INSERT_SPRINT =
            new ScrumToolOperation<InsertSprintArgs, InsertResponse<Sprint>>() {
                
        @Override
        public InsertResponse<Sprint> execute(InsertSprintArgs arg,
                Scrumtool service) throws ScrumToolException {
            try {
                ScrumSprint scrumSprint = SprintConverters.SPRINT_TO_SCRUMSPRINT.convert(arg.getSprint());
                KeyResponse opStat = service.insertScrumSprint(arg.getProjectKey(), scrumSprint).execute();
                InsertResponse<Sprint> response = new InsertResponse<Sprint>(arg.getSprint(), opStat);
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
