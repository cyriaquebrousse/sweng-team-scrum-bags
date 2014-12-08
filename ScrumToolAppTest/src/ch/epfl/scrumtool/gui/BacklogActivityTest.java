package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.gui.utils.*;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;

/**
 * @author LeoWirz
 * 
 */
public class BacklogActivityTest extends ActivityInstrumentationTestCase2<BacklogActivity> {

    public BacklogActivityTest() {
        super(BacklogActivity.class);
    }
    
    private final MainTask TASK = MockData.TASK1;
    
    private final static Project PROJECT = MockData.MURCS;

    private final static List<MainTask> TASKLIST = new ArrayList<MainTask>();

    private final static DatabaseScrumClient MOCKCLIENT = Mockito.mock(DatabaseScrumClient.class);
    
    private final Answer<Void> answer = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            ((Callback<List<MainTask>>) invocation.getArguments()[1]).interactionDone(TASKLIST);
            return null;
        }
    };

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TASKLIST.clear();
        TASKLIST.add(TASK);
        Client.setScrumClient(MOCKCLIENT);
        doAnswer(answer).when(MOCKCLIENT).loadBacklog(Mockito.any(Project.class), any(Callback.class));
        
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BacklogActivity.class);
        intent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        setActivityIntent(intent);
        
        getActivity();
    }
    
    public void testListIsDisplayed() {
        onView(withText("write tests")).check(matches(isDisplayed()));
    }
    
    public void testAddTask() throws InterruptedException{
        onView(withId(Menu.FIRST)).perform(click());
        onView(withId(R.id.task_name_edit)).perform(typeText("task"));
        onView(withId(R.id.task_description_edit)).perform(typeText("des"), closeSoftKeyboard());
        onView(withId(R.id.task_priority_edit)).perform(click());
        onView(withText("Low")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.task_edit_button_next)).perform(click());
    }
    
    @SuppressWarnings("unchecked")
    public void testRemoveTask() {
        onData(instanceOf(MainTask.class)).inAdapterView(allOf(withId(R.id.backlog_tasklist))).atPosition(0).perform(longClick());
        onView(withText("Delete")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }
    
    public void testEmptyListShowsHint() throws Throwable {
        onView(withId(R.id.swipe_update_empty_backlog_tasklist)).check(matches(not(isDisplayed())));
        TASKLIST.remove(0);
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onResume();
            }
        });
        onView(withId(R.id.swipe_update_empty_backlog_tasklist)).check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testEditTask() throws InterruptedException {
        onData(instanceOf(MainTask.class)).inAdapterView(allOf(withId(R.id.backlog_tasklist))).atPosition(0).perform(longClick());
        onView(withText("Edit")).perform(click());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(typeText("des"), closeSoftKeyboard());
        onView(withId(R.id.task_priority_edit)).perform(click());
        onView(withText("Urgent")).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.task_edit_button_next)).perform(click());
        
    }

}
