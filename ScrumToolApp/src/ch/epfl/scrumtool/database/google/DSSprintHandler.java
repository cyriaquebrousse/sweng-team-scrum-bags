package ch.epfl.scrumtool.database.google;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.SprintHandler;
import ch.epfl.scrumtool.database.google.containers.InsertSprintArgs;
import ch.epfl.scrumtool.database.google.conversion.CollectionResponseConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusEntity;
import ch.epfl.scrumtool.database.google.conversion.SprintConverters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.DSOperationExecutor;
import ch.epfl.scrumtool.database.google.operations.SprintOperations;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSSprintHandler implements SprintHandler {

    @Override
    /**
     * Inserts a Sprint on the server.
     */
    public void insert(final Sprint sprint, final Project project,
            final Callback<Sprint> callback) {
        InsertSprintArgs args = new InsertSprintArgs(project.getKey(), sprint);
        DSExecArgs.Factory<InsertSprintArgs, OperationStatusEntity<Sprint>, Sprint> factory = 
                new DSExecArgs.Factory<InsertSprintArgs, OperationStatusEntity<Sprint>, Sprint>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(SprintConverters.OPSTATSPRINT_TO_SPRINT);
        factory.setOperation(SprintOperations.INSERT_SPRINT);
        DSOperationExecutor.execute(args, factory.build());
    }

    @Override
    /**
     * Updates the ref Sprint to be the modified Sprint.
     */
    public void update(final Sprint modified, final Sprint ref,
            final Callback<Boolean> callback) {
        DSExecArgs.Factory<Sprint, OperationStatus, Boolean> builder =
                new DSExecArgs.Factory<Sprint, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        builder.setCallback(callback);
        builder.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        builder.setOperation(SprintOperations.UPDATE_SPRINT);
        DSOperationExecutor.execute(modified, builder.build());
    }

    @Override
    /**
     * Removes a Sprint from the datastore.
     */
    public void remove(final Sprint sprint, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, OperationStatus, Boolean> factory = 
                new DSExecArgs.Factory<String, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(SprintOperations.DELETE_SPRINT);
        DSOperationExecutor.execute(sprint.getKey(), factory.build());
    }

    /**
     * Loads all the Sprints in the datastore.
     * 
     * @param projectKey
     * @param dbC
     */
    @Override
    public void loadSprints(final Project project,
            final Callback<List<Sprint>> callback) {
        DSExecArgs.Factory<String, CollectionResponseScrumSprint, List<Sprint>> factory = 
                new DSExecArgs.Factory<String, CollectionResponseScrumSprint, List<Sprint>>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(CollectionResponseConverters.SPRINTS);
        factory.setOperation(SprintOperations.LOAD_SPRINT);
        DSOperationExecutor.execute(project.getKey(), factory.build());
    }

    @Override
    public void insert(final Sprint object, final Callback<Sprint> cB) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    /**
     * Loads a Sprint from its key (String).
     */
    public void load(final String key, final Callback<Sprint> cB) {
        throw new UnsupportedOperationException();
    }
}
