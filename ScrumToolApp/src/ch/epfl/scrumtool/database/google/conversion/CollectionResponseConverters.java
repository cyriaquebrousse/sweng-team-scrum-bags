package ch.epfl.scrumtool.database.google.conversion;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * Ensures conversion bewteen CollectionResponse-A- and List-A-
 * 
 * @author vincent
 *
 */
public class CollectionResponseConverters {

    public static final EntityConverter<CollectionResponseScrumIssue, List<Issue>> ISSUES =
            new EntityConverter<CollectionResponseScrumIssue, List<Issue>>() {

        @Override
        public List<Issue> convert(CollectionResponseScrumIssue dbIssues) {
            ArrayList<Issue> issues = new ArrayList<Issue>();
            if (dbIssues != null && dbIssues.getItems() != null) {
                for (ScrumIssue i : dbIssues.getItems()) {
                    Issue tmp = IssueConverters.SCRUMISSUE_TO_ISSUE.convert(i);
                    issues.add(tmp);
                }
            }
            return issues;
        }
    };
    
    public static final EntityConverter<CollectionResponseScrumMainTask, List<MainTask>> MAINTASKS =
            new EntityConverter<CollectionResponseScrumMainTask, List<MainTask>>() {

        @Override
        public List<MainTask> convert(CollectionResponseScrumMainTask dbMainTasks) {
            ArrayList<MainTask> maintasks = new ArrayList<MainTask>();
            if (dbMainTasks != null && dbMainTasks.getItems() != null) {
                for (ScrumMainTask i : dbMainTasks.getItems()) {
                    MainTask tmp = MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(i);
                    maintasks.add(tmp);
                }
            }
            return maintasks;
        }
    };
    
    public static final EntityConverter<CollectionResponseScrumProject, List<Project>> PROJECTS =
            new EntityConverter<CollectionResponseScrumProject, List<Project>>() {

        @Override
        public List<Project> convert(CollectionResponseScrumProject dbProjects) {
            ArrayList<Project> projects = new ArrayList<Project>();
            if (dbProjects != null && dbProjects.getItems() != null) {
                for (ScrumProject i : dbProjects.getItems()) {
                    Project tmp = ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(i);
                    projects.add(tmp);
                }
            }
            return projects;
        }
    };
    
    public static final EntityConverter<CollectionResponseScrumSprint, List<Sprint>> SPRINTS =
            new EntityConverter<CollectionResponseScrumSprint, List<Sprint>>() {

        @Override
        public List<Sprint> convert(CollectionResponseScrumSprint dbSprints) {
            ArrayList<Sprint> sprints = new ArrayList<Sprint>();
            if (dbSprints != null && dbSprints.getItems() != null) {
                for (ScrumSprint i : dbSprints.getItems()) {
                    Sprint tmp = SprintConverters.SCRUMSPRINT_TO_SPRINT.convert(i);
                    sprints.add(tmp);
                }
            }
            return sprints;
        }
    };
    
    public static final EntityConverter<CollectionResponseScrumPlayer, List<Player>> PLAYERS =
            new EntityConverter<CollectionResponseScrumPlayer, List<Player>>() {

        @Override
        public List<Player> convert(CollectionResponseScrumPlayer dbPlayers) {
            ArrayList<Player> players = new ArrayList<Player>();
            if (dbPlayers != null && dbPlayers.getItems() != null) {
                for (ScrumPlayer i : dbPlayers.getItems()) {
                    Player tmp = PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(i);
                    players.add(tmp);
                }
            }
            return players;
        }
    };
}
