package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.ProjectConverters;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

/**
 * @author aschneuw
 */
public final class ProjectOperations {
    /**
     * operation to update a project
     */
    public static final ScrumToolOperation<Project, Void> UPDATE_PROJECT = 
            new ScrumToolOperation<Project, Void>() {
        @Override
        public Void operation(Project arg, Scrumtool service) throws IOException, ScrumToolException {
                ScrumProject update = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(arg);
                return service.updateScrumProject(update).execute();
        }
        
    };
    
    /**
     * operation to insert a project to the database
     */
    public static final ScrumToolOperation<Project, InsertResponse<Project>> INSERT_PROJECT = 
            new ScrumToolOperation<Project, InsertResponse<Project>>() {
        @Override
        public InsertResponse<Project> operation(Project arg, Scrumtool service)
                throws IOException, ScrumToolException {
                ScrumProject insert = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(arg);
                return new InsertResponse<Project>(arg, service.insertScrumProject(insert).execute());
        }
    };
    
    /**
     * operation to delete a project on the database
     */
    public static final ScrumToolOperation<String, Void> DELETE_PROJECT = 
            new ScrumToolOperation<String, Void>() {
                
        @Override
        public Void operation(String arg, Scrumtool service) throws IOException, ScrumToolException {
                return service.removeScrumProject(arg).execute();
        }
    };
    
    /**
     * operation to load the projects for the current user (with its associated players)
     */
    public static final ScrumToolOperation<Void, CollectionResponseScrumProject> LOAD_PROJECTS = 
            new ScrumToolOperation<Void, CollectionResponseScrumProject>() {

        @Override
        public CollectionResponseScrumProject operation(Void arg, Scrumtool service)
                throws ScrumToolException, IOException {
                return service.loadProjects(Session.getCurrentSession().getUser().getEmail()).execute();
        }
    };

}
