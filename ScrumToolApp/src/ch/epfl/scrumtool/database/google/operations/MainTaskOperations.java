package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertMainTaskArgs;
import ch.epfl.scrumtool.database.google.conversion.MaintaskConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusEntity;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.InsertException;
import ch.epfl.scrumtool.exception.LoadException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;

/**
 * Operations for MainTask
 * 
 * @author vincent, aschneuw
 *
 */
public class MainTaskOperations {
    public static final ScrumToolOperation<MainTask, OperationStatus> UPDATE_MAINTASK = 
            new ScrumToolOperation<MainTask, OperationStatus>() {
        @Override
        public OperationStatus execute(MainTask arg, Scrumtool service) throws ScrumToolException {
            ScrumMainTask scrumMainTask = MaintaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(arg);
            try {
                return service.updateScrumMainTask(scrumMainTask).execute();
            } catch (IOException e) {
                throw new UpdateException(e, "MainTask update failed");
            }
        }
    };
    
    public static final ScrumToolOperation<String, OperationStatus> DELETE_MAINTASK = 
            new ScrumToolOperation<String, OperationStatus>() {
        @Override
        public OperationStatus execute(String arg, Scrumtool service) throws ScrumToolException {
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
    
    public static final ScrumToolOperation<InsertMainTaskArgs, OperationStatusEntity<MainTask>> INSERT_MAINTASK = 
            new ScrumToolOperation<InsertMainTaskArgs, OperationStatusEntity<MainTask>>() {

        @Override
        public OperationStatusEntity<MainTask> execute(InsertMainTaskArgs arg,
                Scrumtool service) throws ScrumToolException {
            try {
                ScrumMainTask scrumMainTask = MaintaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(arg.getMainTask());
                OperationStatus serverAnswer = 
                        service.insertScrumMainTask(arg.getProjectKey(), scrumMainTask).execute();
                OperationStatusEntity<MainTask> response = 
                        new OperationStatusEntity<MainTask>(arg.getMainTask(), serverAnswer);
                return response;

            } catch (IOException e) {
                throw new InsertException(e, "Task insertion failed");
            }
        }
    };
}
