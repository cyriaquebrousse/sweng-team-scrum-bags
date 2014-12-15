package ch.epfl.scrumtool.database.google.handlers;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.SprintHandler;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import ch.epfl.scrumtool.database.google.conversion.CollectionResponseConverters;
import ch.epfl.scrumtool.database.google.conversion.SprintConverters;
import ch.epfl.scrumtool.database.google.conversion.VoidConverter;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.OperationExecutor;
import ch.epfl.scrumtool.database.google.operations.SprintOperations;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;

/**
 * Implementation of the Sprint database operations for the AppEngine
 * 
 * @author aschneuw
 */
public class DSSprintHandler implements SprintHandler {

    /**
     * Inserts a Sprint on the server.
     */
    @Override
    public void insert(final Sprint sprint, final Project project,
            final Callback<Sprint> callback) {
        EntityKeyArg<Sprint> args = new EntityKeyArg<Sprint>(sprint, project.getKey());
        DSExecArgs.Factory<EntityKeyArg<Sprint>, InsertResponse<Sprint>, Sprint> factory = 
                new DSExecArgs.Factory<EntityKeyArg<Sprint>, InsertResponse<Sprint>, Sprint>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(SprintConverters.INSERTRESPONSE_TO_SPRINT);
        factory.setOperation(SprintOperations.INSERT_SPRINT);
        OperationExecutor.execute(args, factory.build());
    }

    /**
     * Updates the ref Sprint to be the modified Sprint.
     */
    @Override
    public void update(final Sprint modified, final Callback<Void> callback) {
        DSExecArgs.Factory<Sprint, Void, Void> builder =
                new DSExecArgs.Factory<Sprint, Void, Void>(MODE.AUTHENTICATED);
        builder.setCallback(callback);
        builder.setConverter(VoidConverter.VOID_TO_VOID);
        builder.setOperation(SprintOperations.UPDATE_SPRINT);
        OperationExecutor.execute(modified, builder.build());
    }

    /**
     * Removes a Sprint from the datastore.
     */
    @Override
    public void remove(final Sprint sprint, final Callback<Void> callback) {
        DSExecArgs.Factory<String, Void, Void> factory = 
                new DSExecArgs.Factory<String, Void, Void>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(VoidConverter.VOID_TO_VOID);
        factory.setOperation(SprintOperations.DELETE_SPRINT);
        OperationExecutor.execute(sprint.getKey(), factory.build());
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
        OperationExecutor.execute(project.getKey(), factory.build());
    }

    @Override
    public void insert(final Sprint object, final Callback<Sprint> cB) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Loads a Sprint from its key (String).
     */
    @Override
    public void load(final String key, final Callback<Sprint> cB) {
        throw new UnsupportedOperationException();
    }
}
