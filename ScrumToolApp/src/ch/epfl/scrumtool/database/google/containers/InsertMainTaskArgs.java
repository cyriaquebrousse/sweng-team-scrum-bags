package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.MainTask;

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
        if (projectKey == null || mainTask == null) {
            throw new NullPointerException();
        }
        
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
