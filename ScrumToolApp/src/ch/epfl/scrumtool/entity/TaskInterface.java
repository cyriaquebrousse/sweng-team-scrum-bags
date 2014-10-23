package ch.epfl.scrumtool.entity;

import java.util.Set;

/**
 * @author ketsio
 */
public interface TaskInterface {
    String getName();
    String getDescription();
    Status getStatus();
    Priority getPriority();
    Set<IssueInterface> getIssues();
    int getIssuesFinishedCount();
}
