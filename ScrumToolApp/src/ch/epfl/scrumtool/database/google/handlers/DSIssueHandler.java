package ch.epfl.scrumtool.database.google.handlers;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.database.IssueHandler;
import ch.epfl.scrumtool.database.google.containers.InsertIssueArgs;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.CollectionResponseConverters;
import ch.epfl.scrumtool.database.google.conversion.IssueConverters;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusConverters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.OperationExecutor;
import ch.epfl.scrumtool.database.google.operations.IssueOperations;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;

/**
 * @author sylb, aschneuw, zenhaeus
 * 
 */
public class DSIssueHandler implements IssueHandler {

    @Override
    public void insert(final Issue issue, final Callback<Issue> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(final Issue issue, final MainTask maintask,
        final Callback<Issue> callback) {
        InsertIssueArgs args = new InsertIssueArgs(issue, maintask.getKey());
        DSExecArgs.Factory<InsertIssueArgs, InsertResponse<Issue>, Issue> factory = 
                new Factory<InsertIssueArgs, InsertResponse<Issue>, Issue>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(IssueConverters.OPSTATISSUE_TO_ISSUE);
        factory.setOperation(IssueOperations.INSERT_ISSUE_MAINTASK);
        OperationExecutor.execute(args, factory.build());
    }

    @Override
    public void load(final String issueKey, final Callback<Issue> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Issue modified, final Issue ref,
            final Callback<Boolean> callback) {
        DSExecArgs.Factory<Issue, OperationStatus, Boolean> factory = 
                new DSExecArgs.Factory<Issue, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setCallback(callback);
        factory.setOperation(IssueOperations.UPDATE_ISSUE);
        OperationExecutor.execute(modified, factory.build());
    }

    @Override
    public void remove(final Issue issue, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, OperationStatus, Boolean> factory = 
                new DSExecArgs.Factory<String, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(IssueOperations.DELETE_ISSUE);
        OperationExecutor.execute(issue.getKey(), factory.build());
    }

    @Override
    public void loadIssues(final MainTask mainTask,
            final Callback<List<Issue>> callback) {
        DSExecArgs.Factory<String, CollectionResponseScrumIssue, List<Issue>> factory = 
                new Factory<String, CollectionResponseScrumIssue, List<Issue>>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(CollectionResponseConverters.ISSUES);
        factory.setOperation(IssueOperations.LOAD_ISSUES_MAINTASK);
        OperationExecutor.execute(mainTask.getKey(), factory.build());
    }

    @Override
    public void loadIssues(final Sprint sprint, final Callback<List<Issue>> callback) {
        DSExecArgs.Factory<String, CollectionResponseScrumIssue, List<Issue>> factory = 
                new Factory<String, CollectionResponseScrumIssue, List<Issue>>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(CollectionResponseConverters.ISSUES);
        factory.setOperation(IssueOperations.LOAD_ISSUES_SPRINT);
        OperationExecutor.execute(sprint.getKey(), factory.build());
    }

    @Override
    public void assignIssueToSprint(final Issue issue, final Sprint sprint,
            Callback<Boolean> callback) {
        InsertIssueArgs args = new InsertIssueArgs(issue, sprint.getKey());
        DSExecArgs.Factory<InsertIssueArgs, OperationStatus, Boolean> factory = 
                new Factory<InsertIssueArgs, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(IssueOperations.INSERT_ISSUE_SPRINT);
        OperationExecutor.execute(args, factory.build());
    }
    
    public void loadUnsprintedIssues(final Project project, final Callback<List<Issue>> callback) {
        DSExecArgs.Factory<String, CollectionResponseScrumIssue, List<Issue>> factory = 
                new Factory<String, CollectionResponseScrumIssue, List<Issue>>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(CollectionResponseConverters.ISSUES);
        factory.setOperation(IssueOperations.LOAD_ISSUES_NO_SPRINT);
        OperationExecutor.execute(project.getKey(), factory.build());
    }

    @Override
    public void removeIssueFromSprint(Issue issue, Callback<Boolean> cB) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadIssuesForUser(User user, Callback<List<TaskIssueProject>> cB) {
        DSExecArgs.Factory<String, CollectionResponseScrumIssue, List<TaskIssueProject>> factory =
            new DSExecArgs.Factory<String, CollectionResponseScrumIssue, List<TaskIssueProject>>(MODE.AUTHENTICATED);
        factory.setCallback(cB);
        factory.setConverter(IssueConverters.DASHBOARD_ISSUES);
        factory.setOperation(IssueOperations.LOAD_ISSUES_USER);
        OperationExecutor.execute(user.getEmail(), factory.build());
    }

}