/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Sprint;

/**
 * @author aschneuw
 *
 */
public interface IssueHandler extends DatabaseHandler<Issue> {
    void loadIssues(final MainTask mainTask, final Callback<List<Issue>> cB);
    void loadIssues(final Sprint sprint, final Callback<List<Issue>> cB);
    void insert(final Issue issue, final MainTask mainTask, final Callback<Issue> cB);
    void addIssue(final Issue issue, final Sprint sprint, final Callback<Boolean> cB);
    void removeIssue(final Issue issue, final Sprint sprint, final Callback<Boolean> cB);

}
