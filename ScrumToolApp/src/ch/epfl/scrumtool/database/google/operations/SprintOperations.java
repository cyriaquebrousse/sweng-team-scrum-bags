package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.containers.InsertSprintArgs;
import ch.epfl.scrumtool.database.google.conversion.SprintConverters;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * Operations for Sprint
 * 
 * @author vincent
 */
public class SprintOperations {
    public static final ScrumToolOperation<Sprint, Void> UPDATE_SPRINT = 
            new ScrumToolOperation<Sprint, Void>() {
        @Override
        public Void operation(Sprint arg, Scrumtool service) throws IOException {
            ScrumSprint scrumSprint = SprintConverters.SPRINT_TO_SCRUMSPRINT.convert(arg);
                return service.updateScrumSprint(scrumSprint).execute();
        }
    };
    
    public static final ScrumToolOperation<String, Void> DELETE_SPRINT = 
            new ScrumToolOperation<String, Void>() {
        @Override
        public Void operation(String arg, Scrumtool service) throws IOException {
                return service.removeScrumSprint(arg).execute();
        }
    };
    
    public static final ScrumToolOperation<InsertSprintArgs, InsertResponse<Sprint>> INSERT_SPRINT =
            new ScrumToolOperation<InsertSprintArgs, InsertResponse<Sprint>>() {
                
        @Override
        public InsertResponse<Sprint> operation(InsertSprintArgs arg,
                Scrumtool service) throws IOException {
                ScrumSprint scrumSprint = SprintConverters.SPRINT_TO_SCRUMSPRINT.convert(arg.getSprint());
                KeyResponse opStat = service.insertScrumSprint(arg.getProjectKey(), scrumSprint).execute();
                InsertResponse<Sprint> response = new InsertResponse<Sprint>(arg.getSprint(), opStat);
                return response;
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumSprint> LOAD_SPRINT = 
            new ScrumToolOperation<String, CollectionResponseScrumSprint>() {
        @Override
        public CollectionResponseScrumSprint operation(String arg, Scrumtool service)
                throws IOException {
                return service.loadSprints(arg).execute();
        }
    };
    
}
