package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertMainTaskArgs;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.MainTaskConverters;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.InsertException;
import ch.epfl.scrumtool.exception.LoadException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;

/**
 * Operations for MainTask
 * 
 * @author vincent, aschneuw
 *
 */
public class MainTaskOperations {
    public static final ScrumToolOperation<MainTask, Void> UPDATE_MAINTASK = 
            new ScrumToolOperation<MainTask, Void>() {
        @Override
        public Void execute(MainTask arg, Scrumtool service) throws ScrumToolException {
            ScrumMainTask scrumMainTask = MainTaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(arg);
            try {
                return service.updateScrumMainTask(scrumMainTask).execute();
            } catch (IOException e) {
                throw new UpdateException(e, "MainTask update failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, Void> DELETE_MAINTASK = 
            new ScrumToolOperation<String, Void>() {
        @Override
        public Void execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.removeScrumMainTask(arg).execute();
            } catch (IOException e) {
                throw new DeleteException(e, "MainTask deletion failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, CollectionResponseScrumMainTask> LOAD_MAINTASKS = 
            new ScrumToolOperation<String, CollectionResponseScrumMainTask>() {
        @Override
        public CollectionResponseScrumMainTask execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.loadMainTasks(arg).execute();
            } catch (IOException e) {
                throw new LoadException(e, "Task list could not be loaded");
            }

        }
    };
    
    public static final ScrumToolOperation<InsertMainTaskArgs, InsertResponse<MainTask>> INSERT_MAINTASK = 
            new ScrumToolOperation<InsertMainTaskArgs, InsertResponse<MainTask>>() {

        @Override
        public InsertResponse<MainTask> execute(InsertMainTaskArgs arg,
                Scrumtool service) throws ScrumToolException {
            try {
                ScrumMainTask scrumMainTask = MainTaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(arg.getMainTask());
                KeyResponse serverAnswer = 
                        service.insertScrumMainTask(arg.getProjectKey(), scrumMainTask).execute();
                InsertResponse<MainTask> response = 
                        new InsertResponse<MainTask>(arg.getMainTask(), serverAnswer);
                return response;

            } catch (IOException e) {
                throw new InsertException(e, "Task insertion failed");
            }
        }
    };
}
