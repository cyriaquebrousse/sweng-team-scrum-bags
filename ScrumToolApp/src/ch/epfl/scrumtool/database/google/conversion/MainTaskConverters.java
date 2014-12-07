package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Ensures conversiont between ScrumMaintask and Maintask
 * 
 * @author vincent
 * 
 */
public final class MainTaskConverters {
    /**
     * Converts a ScrumMainTask into a MainTask
     */
    public static final EntityConverter<ScrumMainTask, MainTask> SCRUMMAINTASK_TO_MAINTASK =
            new EntityConverter<ScrumMainTask, MainTask>() {

        @Override
        public MainTask convert(ScrumMainTask dbMainTask) {
            
            Preconditions.throwIfNull("Trying to convert a MainTask with null parameters",
                    dbMainTask.getKey(),
                    dbMainTask.getName(),
                    dbMainTask.getDescription(),
                    dbMainTask.getPriority(),
                    dbMainTask.getStatus());
            
            Preconditions.throwIfInvalidKey(dbMainTask.getKey());
            
            MainTask.Builder maintask = new MainTask.Builder();

            String key = dbMainTask.getKey();
            maintask.setKey(key);

            String name = dbMainTask.getName();
            maintask.setName(name);

            String description = dbMainTask.getDescription();
            maintask.setDescription(description);

            String priority = dbMainTask.getPriority();
            maintask.setPriority(Priority.valueOf(priority));

            String status = dbMainTask.getStatus();
            maintask.setStatus(Status.valueOf(status));
            
            int issuesFinished = dbMainTask.getIssuesFinished() == null ? 0 : dbMainTask.getIssuesFinished();
            int totalIssues = dbMainTask.getTotalIssues() == null ? 0 : dbMainTask.getTotalIssues();
            float timeFinished = dbMainTask.getTimeFinished() == null ? 0 : dbMainTask.getTimeFinished();
            float totalTime = dbMainTask.getTotalTime() == null ? 0 : dbMainTask.getTotalTime();
            
            maintask.setFinishedIssues(issuesFinished);
            maintask.setFinishedIssueTime(timeFinished);
            maintask.setTotalIssues(totalIssues);
            maintask.setTotalIssueTime(totalTime);

            return maintask.build();
        }
    };

    /**
     * Converts a MainTask into a ScrumMainTask
     */
    public static final EntityConverter<MainTask, ScrumMainTask> MAINTASK_TO_SCRUMMAINTASK =
            new EntityConverter<MainTask, ScrumMainTask>() {

        @Override
        public ScrumMainTask convert(MainTask maintask) {
            ScrumMainTask dbMainTask = new ScrumMainTask();

            if (!maintask.getKey().equals("")) {
                dbMainTask.setKey(maintask.getKey());
            }
            
            dbMainTask.setName(maintask.getName());
            dbMainTask.setDescription(maintask.getDescription());
            dbMainTask.setPriority(maintask.getPriority().name());
            dbMainTask.setStatus(maintask.getStatus().name());
            // Currently we don't need LastModDate and LastModUser

            return dbMainTask;
        }
    };
    
    public static final EntityConverter<InsertResponse<MainTask>, MainTask> OPSTATMAINTASK_TO_MAINTASK = 
            new EntityConverter<InsertResponse<MainTask>, MainTask>() {

        @Override
        public MainTask convert(InsertResponse<MainTask> a) {
            return a.getEntity()
                    .getBuilder()
                    .setKey(a.getkeyReponse().getKey())
                    .build();
        }
    };
}
