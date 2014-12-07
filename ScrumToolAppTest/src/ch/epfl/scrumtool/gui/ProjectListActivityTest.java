package ch.epfl.scrumtool.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import com.google.android.apps.common.testing.ui.espresso.DataInteraction;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.BoundedMatcher;

/**
 * @author LeoWirz, zenhaeus
 * 
 */
public class ProjectListActivityTest extends ActivityInstrumentationTestCase2<ProjectListActivity> {

    public ProjectListActivityTest() {
        super(ProjectListActivity.class);
    }
    
    private static final String PROJECT_NAME = "TestProject";
    private static final String PROJECT_DESCRIPTION = "TestDescription";
    
    private static final Project PROJECT = new Project.Builder()
        .setDescription(PROJECT_DESCRIPTION)
        .setName(PROJECT_NAME).build();
    
    private final List<Project> PROJECT_LIST = new ArrayList<Project>();
    
    private static final DatabaseScrumClient MOCKCLIENT = Mockito.mock(DatabaseScrumClient.class);
    
    private final Answer<Void> ANSWER_LOAD = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            ((Callback<List<Project>>) invocation.getArguments()[0]).interactionDone(PROJECT_LIST);
            return null;
        }
    };
    
    private final Answer<Void> ANSWER_REMOVE = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            ((Callback<Void>) invocation.getArguments()[1]).interactionDone(null);
            return null;
        }
        
    };
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        PROJECT_LIST.add(PROJECT);
        Client.setScrumClient(MOCKCLIENT);
        Mockito.doAnswer(ANSWER_LOAD).when(MOCKCLIENT).loadProjects(Mockito.any(Callback.class));

        getActivity();
    }

    public void testDisplayLoadedProject() {
        DataInteraction listInteraction = onData(instanceOf(Project.class))
            .inAdapterView(allOf(withId(R.id.project_list)));
        
        listInteraction.atPosition(0).onChildView(withId(R.id.project_row_name))
            .check(matches(withText(PROJECT_NAME)));

        listInteraction.atPosition(0).onChildView(withId(R.id.project_row_description))
            .check(matches(withText(PROJECT_DESCRIPTION)));
    }

    public void testRemoveProject() {
        Mockito.doAnswer(ANSWER_REMOVE).when(MOCKCLIENT)
            .deleteProject(Mockito.any(Project.class), Mockito.any(Callback.class));

        DataInteraction listInteraction = 
                onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0);
        listInteraction.perform(ViewActions.longClick());
        onView(withText("Delete")).perform(click());

//        TODO check that item is deleted from list
//        onView(withId(R.id.project_list));

    }
}
