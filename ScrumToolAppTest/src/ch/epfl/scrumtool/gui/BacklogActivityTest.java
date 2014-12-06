package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

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
    
    MainTask task = MockData.TASK1;
    
    Project project = MockData.MURCS;

    List<MainTask> taskList = new ArrayList<MainTask>();

    DatabaseScrumClient mockclient = Mockito.mock(DatabaseScrumClient.class);

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(mockclient);
        
        Answer<Void> answer = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                taskList.add(task);
                ((Callback<List<MainTask>>) invocation.getArguments()[1]).interactionDone(taskList);
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockclient).loadBacklog(Mockito.any(Project.class), Mockito.any(Callback.class));
        
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BacklogActivity.class);
        intent.putExtra(Project.SERIALIZABLE_NAME, project);
        
        setActivityIntent(intent);
        getActivity();
    }
    
    public void testListIsDisplayed() {
        onView(withText("write tests")).check(matches(isDisplayed()));
    }
    
    public void testAddTask() throws InterruptedException{
        onView(withId(Menu.FIRST)).perform(click());
        onView(withId(R.id.task_name_edit)).perform(typeText("task test"));
        onView(withId(R.id.task_description_edit)).perform(typeText("description"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.task_edit_button_next)).perform(click());
    }
    
    @SuppressWarnings("unchecked")
    public void testRemoveTask() {
        onData(instanceOf(MainTask.class)).inAdapterView(allOf(withId(R.id.backlog_tasklist))).atPosition(0).perform(longClick());
        onView(withText("Delete")).perform(click());
    }

}
