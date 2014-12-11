package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
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
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
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

    private Project project = MockData.MURCS;
    private MainTask task = MockData.TASK1;
    private Issue issue = MockData.ISSUE1;
    private User user = MockData.VINCENT;
    private TaskIssueProject taskIssueProject = new TaskIssueProject(task,
            project, issue);

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

    public DashboardActivityTest() {
        super(DashboardActivity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        projectList.clear();
        projectList.add(project);
        issueList.clear();
        issueList.add(taskIssueProject);
        Client.setScrumClient(mockclient);
        new Session(user) {
            // We use this to mock the session
        };
        doAnswer(answer1).when(mockclient).loadProjects(any(Callback.class));
        doAnswer(answer2).when(mockclient).loadIssuesForUser(any(User.class),
                any(Callback.class));

        getActivity();
    }

    public void testTest() {
        assertTrue(true);
    }

}
