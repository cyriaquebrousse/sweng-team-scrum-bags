package ch.epfl.scrumtool.gui;

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

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.utils.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.network.Session;

/**
 * @author LeoWirz
 * 
 */
public class DashboardActivityTest extends BaseInstrumentationTestCase<DashboardActivity> {

    public DashboardActivityTest() {
        super(DashboardActivity.class);
    }

    Project project = MockData.MURCS;
    MainTask task = MockData.TASK1;
    Issue issue = MockData.ISSUE1;
    User user = MockData.VINCENT;
    TaskIssueProject taskIssueProject = new TaskIssueProject(task, project, issue);

    List<Project> projectList = new ArrayList<Project>();
    List<TaskIssueProject> issueList= new ArrayList<TaskIssueProject>();

    DatabaseScrumClient mockclient = Mockito.mock(DatabaseScrumClient.class);
    Session mocksession = Mockito.mock(Session.class);

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(mockclient);

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
        Mockito.doAnswer(answer2).when(Mockito.mock(User.class)).loadIssuesForUser(Mockito.any(Callback.class));
        Mockito.doReturn(user).when(mocksession).getUser();
        
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
