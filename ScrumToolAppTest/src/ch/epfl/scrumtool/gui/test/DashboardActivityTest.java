package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

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
public class DashboardActivityTest extends ActivityInstrumentationTestCase2<DashboardActivity> {

    private Project project = MockData.MURCS;
    private MainTask task = MockData.TASK1;
    private Issue issue = MockData.ISSUE1;
    private User user = MockData.VINCENT;
    private TaskIssueProject taskIssueProject = new TaskIssueProject(task, project, issue);

    private List<Project> projectList = new ArrayList<Project>();
    private List<TaskIssueProject> issueList= new ArrayList<TaskIssueProject>();

    private DatabaseScrumClient mockclient = Mockito.mock(DatabaseScrumClient.class);
    
    public DashboardActivityTest() {
        super(DashboardActivity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(mockclient);
        new Session(user) {
            //We use this to mock the session
        };
        
        Answer<Void> answer = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                projectList.add(project);
                ((Callback<List<Project>>) invocation.getArguments()[0]).interactionDone(projectList);
                return null;
            }
        };
        
        Answer<Void> answer2 = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                issueList.add(taskIssueProject);
                ((Callback<List<TaskIssueProject>>) invocation.getArguments()[1]).interactionDone(issueList);
                return null;
            }
        };
        
        Mockito.doAnswer(answer).when(mockclient).loadProjects(Mockito.any(Callback.class));
        Mockito.doAnswer(answer2).when(mockclient).loadIssuesForUser(Mockito.any(User.class), Mockito.any(Callback.class));
        
        getActivity();
    }

    public void testProjectsButton() {
        onView(withId(R.id.dashboard_button_project_list)).check(matches(isClickable()));
        onView(withId(R.id.dashboard_button_project_list)).perform(click());
    }

    public void testProfilButton() {
        onView(withId(R.id.dashboard_button_my_profile)).check(matches(isClickable()));
        onView(withId(R.id.dashboard_button_my_profile)).perform(click());
    }
    
    public void testTest() {
        assertTrue(true);
    }

}
