package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

/**
 * @author LeoWirz
 * 
 */
public class ProjectListActivityTest extends ActivityInstrumentationTestCase2<ProjectListActivity> {

    public ProjectListActivityTest() {
        super(ProjectListActivity.class);
    }
    
    Project project = new Project.Builder()
        .setDescription("TestDescription")
        .setName("TestProject").build();
    
    List<Project> projectList = new ArrayList<Project>();
    
    DatabaseScrumClient mockclient = Mockito.mock(DatabaseScrumClient.class);
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(mockclient);
        
        Answer<Void> answer = new Answer<Void>() {

            @SuppressWarnings("unchecked")
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                projectList.add(project);
                ((Callback<List<Project>>) invocation.getArguments()[0]).interactionDone(projectList);
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockclient).loadProjects(Mockito.any(Callback.class));
        getActivity();
    }

    public void testAddNewProject() {
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
            .check(matches(withText("TestProject")));
    }
    
    public void testRemoveProject() {
    }

}
