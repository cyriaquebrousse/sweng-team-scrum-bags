/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.ProjectConverters;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.InsertException;
import ch.epfl.scrumtool.exception.LoadException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

/**
 * @author aschneuw
 *
 */
public final class ProjectOperations {
    public static final ScrumToolOperation<Project, Void> UPDATE_PROJECT = 
            new ScrumToolOperation<Project, Void>() {
        @Override
        public Void execute(Project arg, Scrumtool service) throws ScrumToolException {
            try {
                ScrumProject update = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(arg);
                update.setLastModUser(Session.getCurrentSession().getUser().getEmail());
                return service.updateScrumProject(update).execute();
            } catch (IOException e) {
                throw new UpdateException(e, "Project update failed");
            }
        }
        
    };
    
    public static final ScrumToolOperation<Project, InsertResponse<Project>> INSERT_PROJECT = 
            new ScrumToolOperation<Project, InsertResponse<Project>>() {
        @Override
        public InsertResponse<Project> execute(Project arg, Scrumtool service) throws ScrumToolException {
            try {
                ScrumProject insert = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(arg);
                insert.setLastModUser(Session.getCurrentSession().getUser().getEmail());
                return new InsertResponse<Project>(arg, service.insertScrumProject(insert).execute());
                
            } catch (IOException e) {
                throw new InsertException(e, "Project insertion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, Void> DELETE_PROJECT = 
            new ScrumToolOperation<String, Void>() {
                
        @Override
        public Void execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.removeScrumProject(arg).execute();
            } catch (IOException e) {
                throw new DeleteException(e, "Project deletion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<Void, CollectionResponseScrumProject> LOAD_PROJECTS = 
            new ScrumToolOperation<Void, CollectionResponseScrumProject>() {

        @Override
        public CollectionResponseScrumProject execute(Void arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.loadProjects(Session.getCurrentSession().getUser().getEmail()).execute();
            } catch (IOException e) {
                throw new LoadException(e, "Loading project list failed");
            }
        }
    };

}
