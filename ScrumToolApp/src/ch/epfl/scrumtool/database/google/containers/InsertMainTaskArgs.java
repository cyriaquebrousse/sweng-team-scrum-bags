package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Arguments for MainTask Insert operation
 * 
 * @author aschneuw
 *
 */
public class InsertMainTaskArgs {
    private final String projectKey;
    private final MainTask mainTask;
    
    public InsertMainTaskArgs(final String projectKey, MainTask mainTask) {
        Preconditions.throwIfNull("Wrapper must contain a key and and MainTask", projectKey, mainTask);
        Preconditions.throwIfEmptyString("Project key must be non-empty", projectKey);
        this.projectKey = projectKey;
        this.mainTask = mainTask;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public MainTask getMainTask() {
        return mainTask;
    }
}
