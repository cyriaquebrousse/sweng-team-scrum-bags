package ch.epfl.scrumtool.database.google;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.ProjectHandler;
import ch.epfl.scrumtool.database.google.conversion.CollectionResponseConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusEntity;
import ch.epfl.scrumtool.database.google.conversion.ProjectConverters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.DSOperationExecutor;
import ch.epfl.scrumtool.database.google.operations.ProjectOperations;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSProjectHandler implements ProjectHandler {
    @Override
    public void insert(final Project project, final Callback<Project> callback) {
        DSExecArgs.Factory<Project, OperationStatusEntity<Project>, Project> factory = 
                new DSExecArgs.Factory<Project, OperationStatusEntity<Project>, Project>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setOperation(ProjectOperations.INSERT_PROJECT);
        factory.setConverter(ProjectConverters.OPSTATPROJECT_TO_PROJECT);
        DSOperationExecutor.execute(project, factory.build());
    }

    @Override
    public void load(final String projectKey, final Callback<Project> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Project modified, final Project ref, final Callback<Boolean> callback) {
        DSExecArgs.Factory<Project, OperationStatus, Boolean> factory = 
                new DSExecArgs.Factory<Project, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setCallback(callback);
        factory.setOperation(ProjectOperations.UPDATE_PROJECT);
        DSOperationExecutor.execute(modified, factory.build());
    }

    @Override
    public void remove(final Project project, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, OperationStatus, Boolean> factory = 
                new Factory<String, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(ProjectOperations.DELETE_PROJECT);
        DSOperationExecutor.execute(project.getKey(), factory.build());
    }

    @Override
    public void loadProjects(final Callback<List<Project>> callback) {
        DSExecArgs.Factory<Void, CollectionResponseScrumProject, List<Project>> factory = 
                new Factory<Void, CollectionResponseScrumProject, List<Project>>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(CollectionResponseConverters.PROJECTS);
        factory.setOperation(ProjectOperations.LOAD_PROJECTS);
        DSOperationExecutor.execute(null, factory.build());
        
    }

}