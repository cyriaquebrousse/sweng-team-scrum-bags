package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

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
            assert dbIssue != null;

            Issue.Builder issue = new Issue.Builder();

            String key = dbIssue.getKey();
            if (key != null) {
                issue.setKey(key);
            }

            String description = dbIssue.getDescription();
            if (description != null) {
                issue.setDescription(description);
            }

            String name = dbIssue.getName();
            if (name != null) {
                issue.setName(name);
            }

            issue.setEstimatedTime(dbIssue.getEstimation());

            String priority = dbIssue.getPriority();
            if (priority != null) {
                issue.setPriority(Priority.valueOf(priority));
            }

            String status = dbIssue.getStatus();
            if (status != null) {
                issue.setStatus(Status.valueOf(status));
            }

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
            assert issue != null;

            ScrumIssue dbIssue = new ScrumIssue();

            dbIssue.setKey(issue.getKey());
            dbIssue.setDescription(issue.getDescription());
            dbIssue.setName(issue.getName());
            dbIssue.setEstimation(issue.getEstimatedTime());
            dbIssue.setPriority(issue.getPriority().name());
            dbIssue.setStatus(issue.getStatus().name());

            ScrumPlayer dbPlayer = new ScrumPlayer();
            dbPlayer.setKey(issue.getPlayer().getKey());
            dbIssue.setAssignedPlayer(dbPlayer);

            ScrumSprint dbSprint = new ScrumSprint();
            dbSprint.setKey(issue.getSprint().getKey());
            dbIssue.setSprint(dbSprint);
            // Currently we don't need LastModDate and LasModUser

            return dbIssue;
        }
    };

}