package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * 
 * @author aschneuw
 *
 */
public class InsertSprintArgs {
    private final String projectKey;
    private final Sprint sprint;
    
    public InsertSprintArgs(final String projectKey, final Sprint sprint) {
        Preconditions.throwIfNull("Must contain a key and a sprint", projectKey, sprint);
        Preconditions.throwIfEmptyString("Project key must be non-empty", projectKey);
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
