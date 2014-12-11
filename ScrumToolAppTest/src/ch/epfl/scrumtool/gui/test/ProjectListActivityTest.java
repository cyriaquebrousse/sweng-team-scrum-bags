package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.ProjectListActivity;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import com.google.android.apps.common.testing.ui.espresso.DataInteraction;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

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
    
    private static final List<Project> PROJECT_LIST = new ArrayList<Project>();
    
    private static final DatabaseScrumClient MOCKCLIENT = Mockito.mock(DatabaseScrumClient.class);
    
    private static final Answer<Void> ANSWER_LOAD = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            ((Callback<List<Project>>) invocation.getArguments()[0]).interactionDone(PROJECT_LIST);
            return null;
        }
    };
    
    private static final Answer<Void> ANSWER_REMOVE = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            PROJECT_LIST.remove(0);
            ((Callback<Void>) invocation.getArguments()[1]).interactionDone(null);
            return null;
        }
        
    };
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        PROJECT_LIST.add(PROJECT);
        Client.setScrumClient(MOCKCLIENT);
        doAnswer(ANSWER_LOAD).when(MOCKCLIENT).loadProjects(Matchers.<Callback<List<Project>>>any());

        getActivity();
    }

    public void testDisplayLoadedProject() {
        @SuppressWarnings("unchecked")
        DataInteraction listInteraction = onData(instanceOf(Project.class))
            .inAdapterView(allOf(withId(R.id.project_list)));
        
        listInteraction.atPosition(0).onChildView(withId(R.id.project_row_name))
            .check(matches(withText(PROJECT_NAME)));

        listInteraction.atPosition(0).onChildView(withId(R.id.project_row_description))
            .check(matches(withText(PROJECT_DESCRIPTION)));
    }

    @SuppressWarnings("unchecked")
    public void testRemoveProjectOk() {
        doAnswer(ANSWER_REMOVE).when(MOCKCLIENT)
            .deleteProject(Mockito.any(Project.class), Matchers.<Callback<Void>>any());

        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list)))
            .atPosition(0).perform(ViewActions.longClick());
        onView(withText("Delete")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        // check if list is empty now
        onView(withId(R.id.swipe_update_empty_project_list)).check(matches(isDisplayed()));
    }

    public void testRemoveProjectCancel() {
        doAnswer(ANSWER_REMOVE).when(MOCKCLIENT)
            .deleteProject(Mockito.any(Project.class), Matchers.<Callback<Void>>any());

        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list)))
            .atPosition(0).perform(ViewActions.longClick());
        onView(withText("Delete")).perform(click());
        onView(withId(android.R.id.button2)).perform(click());
        // check if list still contains project
        DataInteraction listInteraction = onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list)));
            
            listInteraction.atPosition(0).onChildView(withId(R.id.project_row_name))
                .check(matches(withText(PROJECT_NAME)));

            listInteraction.atPosition(0).onChildView(withId(R.id.project_row_description))
                .check(matches(withText(PROJECT_DESCRIPTION)));
    }

    @SuppressWarnings("unchecked")
    public void testExpandListEntry() {
        onData(instanceOf(Project.class)).onChildView(withId(R.id.project_row_header_block))
            .check(matches(isDisplayed()));
        onData(instanceOf(Project.class)).onChildView(withId(R.id.project_row_expanded_block))
            .check(matches(not(isDisplayed())));
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list)))
            .atPosition(0).perform(ViewActions.click());
        onData(instanceOf(Project.class)).onChildView(withId(R.id.project_row_header_block))
            .check(matches(isDisplayed()));
        onData(instanceOf(Project.class)).onChildView(withId(R.id.project_row_expanded_block))
            .check(matches(isDisplayed()));
    }
    
//    public void testEmptyListShowsHint() throws Throwable {
//        onView(withId(R.id.swipe_update_empty_project_list)).check(matches(not(isDisplayed())));
//        PROJECT_LIST.remove(0);
//        runTestOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                getActivity().onResume();
//            }
//        });
//        onView(withId(R.id.swipe_update_empty_project_list)).check(matches(isDisplayed()));
//    }
    
    public void testAddProject() throws InterruptedException {
        onView(withId(Menu.FIRST)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.project_description_edit)).check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testEditProject() {
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list)))
            .atPosition(0).perform(ViewActions.longClick());
        onView(withText("Edit")).perform(click());
        onView(withId(R.id.project_title_edit)).check(matches(withText(PROJECT_NAME)));
        onView(withId(R.id.project_description_edit)).check(matches(withText(PROJECT_DESCRIPTION)));
    }
}
