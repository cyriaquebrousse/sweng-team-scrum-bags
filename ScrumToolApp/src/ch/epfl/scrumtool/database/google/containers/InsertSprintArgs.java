package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.Sprint;

/**
 * 
 * @author aschneuw
 *
 */
public class InsertSprintArgs {
    private final String projectKey;
    private final Sprint sprint;
    
    public InsertSprintArgs(final String projectKey, final Sprint sprint) {
        if (projectKey == null || sprint == null) {
            throw new NullPointerException();
        }
        
        this.projectKey = projectKey;
        this.sprint = sprint;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public Sprint getSprint() {
        return sprint;
    }
}
