package ch.epfl.scrumtool.entity;

import java.util.Set;

/**
 * @author ketsio
 */
public class Task implements TaskInterface {

    private String name;
    private String description;
    private Set<IssueInterface> issues;
    private Status status;
    private Priority priority;

    /**
     * @param name
     * @param description
     * @param issues
     * @param status
     */
    public Task(String name, String description, Set<IssueInterface> issues,
            Status status, Priority priority) {
        this.name = name;
        this.description = description;
        this.issues = issues;
        this.status = status;
        this.priority = priority;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Set<IssueInterface> getIssues() {
        return issues;
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int getIssuesFinishedCount() {
        int count = 0;
        for (IssueInterface i : issues) {
            if (i.getStatus() == Status.FINISHED) {
                ++count;
            }
        }
        return count;
    }

}
