package ch.epfl.scrumtool.database.google.conversion;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Ensures conversion between ScrumIssue and issue
 * 
 * @author vincent
 * 
 */
public class IssueConverters {
    public static final EntityConverter<ScrumIssue, Issue> SCRUMISSUE_TO_ISSUE = 
        new EntityConverter<ScrumIssue, Issue>() {
            @Override
            public Issue convert(ScrumIssue dbIssue) {
                Preconditions.throwIfInconsistentData("Trying to convert an Issue with null parameters",
                        dbIssue.getKey(),
                        dbIssue.getName(),
                        dbIssue.getDescription(),
                        dbIssue.getEstimation(),
                        dbIssue.getPriority(),
                        dbIssue.getStatus());

                Issue.Builder issue = new Issue.Builder();

                String key = dbIssue.getKey();
                issue.setKey(key);

                String description = dbIssue.getDescription();
                issue.setDescription(description);

                String name = dbIssue.getName();
                issue.setName(name);

                issue.setEstimatedTime(dbIssue.getEstimation());

                String priority = dbIssue.getPriority();
                issue.setPriority(Priority.valueOf(priority));

                String status = dbIssue.getStatus();
                issue.setStatus(Status.valueOf(status));

                ScrumSprint dbSprint = dbIssue.getSprint();
                if (dbSprint != null) {
                    Sprint sprint = SprintConverters.SCRUMSPRINT_TO_SPRINT
                            .convert(dbSprint);
                    issue.setSprint(sprint);
                }

                ScrumPlayer dbPlayer = dbIssue.getAssignedPlayer();
                if (dbPlayer != null) {
                    Player player = PlayerConverters.SCRUMPLAYER_TO_PLAYER
                            .convert(dbPlayer);
                    issue.setPlayer(player);
                }
                return issue.build();
            }
        };

    public static final EntityConverter<Issue, ScrumIssue> ISSUE_TO_SCRUMISSUE = 
        new EntityConverter<Issue, ScrumIssue>() {
            @Override
            public ScrumIssue convert(Issue issue) {
                ScrumIssue dbIssue = new ScrumIssue();

                if (!issue.getKey().equals("")) {
                    dbIssue.setKey(issue.getKey());
                }
                dbIssue.setDescription(issue.getDescription());
                dbIssue.setName(issue.getName());
                dbIssue.setEstimation(issue.getEstimatedTime());
                dbIssue.setPriority(issue.getPriority().name());
                dbIssue.setStatus(issue.getStatus().name());

                ScrumPlayer dbPlayer;
                if (issue.getPlayer() != null) {
                    dbPlayer = new ScrumPlayer();
                    dbPlayer.setKey(issue.getPlayer().getKey());
                } else {
                    dbPlayer = null;
                }
                dbIssue.setAssignedPlayer(dbPlayer);

                ScrumSprint dbSprint;
                if (issue.getSprint() != null) {
                    dbSprint = new ScrumSprint();
                    dbSprint.setKey(issue.getSprint().getKey());
                } else {
                    dbSprint = null;
                }
                
                dbIssue.setSprint(dbSprint);
                
                return dbIssue;
            }
        };

    public static final EntityConverter<InsertResponse<Issue>, Issue> INSERTRESPONSE_TO_ISSUE = 
        new EntityConverter<InsertResponse<Issue>, Issue>() {
            @Override
            public Issue convert(InsertResponse<Issue> a) {
                return a.getEntity()
                        .getBuilder()
                        .setKey(a.getKeyReponse().getKey())
                        .build();
            }
        };
    
    public static final EntityConverter<CollectionResponseScrumIssue, List<TaskIssueProject>> DASHBOARD_ISSUES = 
        new EntityConverter<CollectionResponseScrumIssue, List<TaskIssueProject>>() {
            @Override
            public List<TaskIssueProject> convert(CollectionResponseScrumIssue a) {
                List<TaskIssueProject> issues = new ArrayList<TaskIssueProject>();
                if (a != null && a.getItems() != null) {
                    for (ScrumIssue s: a.getItems()) {
                        //Check data integrity
                        Preconditions.throwIfInconsistentData("Server did not sent MainTask data", s.getMainTask());
                        Preconditions.throwIfInconsistentData("Server dit not sent Project data",
                                s.getMainTask().getProject());
                        
                        Issue issue = IssueConverters.SCRUMISSUE_TO_ISSUE.convert(s);
                        MainTask mainTask = MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(s.getMainTask());
                        Project project = ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(
                                s.getMainTask().getProject());
                        issues.add(new TaskIssueProject(mainTask, project, issue));
                    }
                }
                return issues;
            }
        };
}
