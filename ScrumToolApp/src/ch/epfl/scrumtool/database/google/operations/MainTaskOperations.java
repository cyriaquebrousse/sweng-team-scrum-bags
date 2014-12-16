package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.MainTaskConverters;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;

/**
 * Operations for MainTask
 * 
 * @author vincent
 * @author aschneuw
 */
public final class MainTaskOperations {
    
    /**
     * operation used to update a MainTask
     */
    public static final ScrumToolOperation<MainTask, Void> UPDATE_MAINTASK = 
        new ScrumToolOperation<MainTask, Void>() {
            @Override
            public Void operation(MainTask arg, Scrumtool service) throws IOException {
                ScrumMainTask scrumMainTask = MainTaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(arg);
                return service.updateScrumMainTask(scrumMainTask).execute();
            }
        };
    
    /**
     * Operation used to delete a MainTask
     */
    public static final ScrumToolOperation<String, Void> DELETE_MAINTASK = 
        new ScrumToolOperation<String, Void>() {
            @Override
            public Void operation(String arg, Scrumtool service) throws IOException {
                return service.removeScrumMainTask(arg).execute();
            }
        };
    
    /**
     * Operation to load MainTasks for a given project
     */
    public static final ScrumToolOperation<String, CollectionResponseScrumMainTask> LOAD_MAINTASKS = 
        new ScrumToolOperation<String, CollectionResponseScrumMainTask>() {
            @Override
            public CollectionResponseScrumMainTask operation(String arg, Scrumtool service) throws IOException {
                return service.loadMainTasks(arg).execute();
            }
        };
    
    /**
     * Operation to insert a MainTask for a given project to the database
     */
    public static final ScrumToolOperation<EntityKeyArg<MainTask>, InsertResponse<MainTask>> INSERT_MAINTASK = 
        new ScrumToolOperation<EntityKeyArg<MainTask>, InsertResponse<MainTask>>() {

            @Override
            public InsertResponse<MainTask> operation(EntityKeyArg<MainTask> arg,
                Scrumtool service) throws IOException {
                ScrumMainTask scrumMainTask = MainTaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(arg.getEntity());
                KeyResponse serverAnswer = 
                        service.insertScrumMainTask(arg.getKey(), scrumMainTask).execute();
                InsertResponse<MainTask> response = 
                        new InsertResponse<MainTask>(arg.getEntity(), serverAnswer);
                return response;
            }
        };
}
