package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.DashboardActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.network.Session;

/**
 * @author LeoWirz
 * 
 */
public class DashboardActivityTest extends
        ActivityInstrumentationTestCase2<DashboardActivity> {

    private Project project = MockData.PROJECT;
    private MainTask task = MockData.TASK1;
    private Issue issue1 = MockData.ISSUE1;
    private Issue issue2 = MockData.ISSUE2;
    private User user = MockData.USER1;
    @SuppressWarnings("unused")
    private Player player = MockData.PLAYERLIST.get(1);
    private TaskIssueProject taskIssueProject1 = new TaskIssueProject(task,
            project, issue1);
    private TaskIssueProject taskIssueProject2 = new TaskIssueProject(task,
            project, issue2);

    private List<Player> playerList = new ArrayList<Player>();
    private List<Project> projectList = new ArrayList<Project>();
    private List<TaskIssueProject> issueList = new ArrayList<TaskIssueProject>();

    private DatabaseScrumClient mockclient = Mockito
            .mock(DatabaseScrumClient.class);

    private Answer<Void> answer1 = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) {
            ((Callback<List<Project>>) invocation.getArguments()[0])
                    .interactionDone(projectList);
            return null;
        }
    };

    private Answer<Void> answer2 = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) {
            ((Callback<List<TaskIssueProject>>) invocation.getArguments()[1])
                    .interactionDone(issueList);
            return null;
        }
    };

    private Answer<Void> answer3 = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) {
            ((Callback<List<Player>>) invocation.getArguments()[0])
                    .interactionDone(playerList);
            return null;
        }
    };

    public DashboardActivityTest() {
        super(DashboardActivity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        playerList.clear();
        //FIXME doesn't work when there's a player in the list
        // playerList.add(player);
        projectList.clear();
        //FIXME it doesn't work anymore when i put a project in the list
        // projectList.add(project);
        issueList.clear();
        issueList.add(taskIssueProject1);
        issueList.add(taskIssueProject2);
        Client.setScrumClient(mockclient);
        new Session(user) {
            // We use this to mock the session
        };
        doAnswer(answer1).when(mockclient).loadProjects(any(Callback.class));
        doAnswer(answer2).when(mockclient).loadIssuesForUser(any(User.class),
                any(Callback.class));
        doAnswer(answer3).when(mockclient).loadInvitedPlayers(
                any(Callback.class));

        getActivity();
    }

    @SuppressWarnings("unchecked")
    public void testOpenIssue() {
        onData(instanceOf(TaskIssueProject.class))
                .inAdapterView(allOf(withId(R.id.dashboard_issue_summary)))
                .atPosition(0).perform(click());
    }

    public void testAddProject() {
        onView(withId(R.id.dashboard_project_summary_empty)).perform(click());
    }

    public void testDenyInvitation() {
        onView(withText("Work alone")).perform(click());
    }

    public void testAcceptInvitation() {
        onView(withText("Join the fun")).perform(click());
    }

}
