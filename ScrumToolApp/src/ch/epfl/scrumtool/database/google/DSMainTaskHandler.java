package ch.epfl.scrumtool.database.google;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.MainTaskHandler;
import ch.epfl.scrumtool.database.google.containers.InsertMainTaskArgs;
import ch.epfl.scrumtool.database.google.conversion.CollectionResponseConverters;
import ch.epfl.scrumtool.database.google.conversion.MaintaskConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusEntity;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.DSOperationExecutor;
import ch.epfl.scrumtool.database.google.operations.MainTaskOperations;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;

/**
 * @author sylb, aschneuw, zenhaeus
 * 
 */
public class DSMainTaskHandler implements MainTaskHandler {
    @Override
    public void insert(final MainTask mainTask, final Project project,
            final Callback<MainTask> callback) {
        InsertMainTaskArgs args = new InsertMainTaskArgs(project.getKey(), mainTask);
        DSExecArgs.Factory<InsertMainTaskArgs, OperationStatusEntity<MainTask>, MainTask> factory = 
                new DSExecArgs.Factory<InsertMainTaskArgs, OperationStatusEntity<MainTask>, MainTask>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(MaintaskConverters.OPSTATMAINTASK_TO_MAINTASK);
        factory.setOperation(MainTaskOperations.INSERT_MAINTASK);
        DSOperationExecutor.execute(args, factory.build());
    }

    @Override
    public void loadMainTasks(final Project project,
            final Callback<List<MainTask>> callback) {
        DSExecArgs.Factory<String, CollectionResponseScrumMainTask, List<MainTask>> factory =
                new DSExecArgs.Factory<String, CollectionResponseScrumMainTask, List<MainTask>>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(CollectionResponseConverters.MAINTASKS);
        factory.setOperation(MainTaskOperations.LOAD_MAINTASKS);
        DSOperationExecutor.execute(project.getKey(), factory.build());
                
    }

    public void update(final MainTask modified, final MainTask ref, 
            final Callback<Boolean> callback) {
        DSExecArgs.Factory<MainTask, OperationStatus, Boolean> builder =
                new DSExecArgs.Factory<MainTask, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        builder.setCallback(callback);
        builder.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        builder.setOperation(MainTaskOperations.UPDATE_MAINTASK);
        DSOperationExecutor.execute(modified, builder.build());
    }

    @Override
    public void remove(final MainTask maintask, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, OperationStatus, Boolean> factory = 
                new DSExecArgs.Factory<String, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(MainTaskOperations.DELETE_MAINTASK);
        DSOperationExecutor.execute(maintask.getKey(), factory.build());
    }

    @Override
    public void load(final String maintaskKey, final Callback<MainTask> callback) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void insert(MainTask maintask, Callback<MainTask> cB) {
        throw new UnsupportedOperationException();
    }
}