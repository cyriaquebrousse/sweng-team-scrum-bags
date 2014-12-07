package ch.epfl.scrumtool.database.google.handlers;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.ProjectHandler;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.CollectionResponseConverters;
import ch.epfl.scrumtool.database.google.conversion.ProjectConverters;
import ch.epfl.scrumtool.database.google.conversion.VoidConverter;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.OperationExecutor;
import ch.epfl.scrumtool.database.google.operations.ProjectOperations;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;

/**
 * @author aschneuw
 */
public class DSProjectHandler implements ProjectHandler {
    @Override
    public void insert(final Project project, final Callback<Project> callback) {
        DSExecArgs.Factory<Project, InsertResponse<Project>, Project> factory = 
                new DSExecArgs.Factory<Project, InsertResponse<Project>, Project>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setOperation(ProjectOperations.INSERT_PROJECT);
        factory.setConverter(ProjectConverters.INSERTRESPONE_TO_PROJECT);
        OperationExecutor.execute(project, factory.build());
    }

    @Override
    public void load(final String projectKey, final Callback<Project> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Project modified, final Project ref, final Callback<Void> callback) {
        DSExecArgs.Factory<Project, Void, Void> factory = 
                new DSExecArgs.Factory<Project, Void, Void>(MODE.AUTHENTICATED);
        factory.setConverter(VoidConverter.VOID_TO_VOID);
        factory.setCallback(callback);
        factory.setOperation(ProjectOperations.UPDATE_PROJECT);
        OperationExecutor.execute(modified, factory.build());
    }

    @Override
    public void remove(final Project project, final Callback<Void> callback) {
        DSExecArgs.Factory<String, Void, Void> factory = 
                new Factory<String, Void, Void>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(VoidConverter.VOID_TO_VOID);
        factory.setOperation(ProjectOperations.DELETE_PROJECT);
        OperationExecutor.execute(project.getKey(), factory.build());
    }

    @Override
    public void loadProjects(final Callback<List<Project>> callback) {
        DSExecArgs.Factory<Void, CollectionResponseScrumProject, List<Project>> factory = 
                new Factory<Void, CollectionResponseScrumProject, List<Project>>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(CollectionResponseConverters.PROJECTS);
        factory.setOperation(ProjectOperations.LOAD_PROJECTS);
        OperationExecutor.execute(null, factory.build());
        
    }

}