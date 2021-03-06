package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.BacklogActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import com.google.android.apps.common.testing.ui.espresso.Espresso;

/**
 * @author LeoWirz
 * 
 */
public class BacklogActivityTest extends
        ActivityInstrumentationTestCase2<BacklogActivity> {

    public BacklogActivityTest() {
        super(BacklogActivity.class);
    }

    private final static MainTask TASK = MockData.TASK1;

    private final static Project PROJECT = MockData.PROJECT;

    private final static List<MainTask> TASKLIST = new ArrayList<MainTask>();

    private final static DatabaseScrumClient MOCKCLIENT = Mockito
            .mock(DatabaseScrumClient.class);

    private final Answer<Void> answer = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) {
            ((Callback<List<MainTask>>) invocation.getArguments()[1])
                    .interactionDone(TASKLIST);
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
        doAnswer(answer).when(MOCKCLIENT).loadBacklog(any(Project.class),
                any(Callback.class));

        Intent intent = new Intent();
        intent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        setActivityIntent(intent);

        getActivity();
    }

    public void testListIsDisplayed() {
        onView(withText("write tests")).check(matches(isDisplayed()));
    }

    public void testAddTask() {
        onView(withId(Menu.FIRST)).perform(click());
        onView(withId(R.id.task_name_edit)).perform(typeText("task"));
        onView(withId(R.id.task_description_edit)).perform(typeText("des"));
        onView(withId(R.id.task_priority_edit)).perform(click());
        onView(withText("Low")).perform(click());
        onView(withId(Menu.FIRST)).perform(click());
    }

    @SuppressWarnings("unchecked")
    public void testRemoveTask() {
        onData(instanceOf(MainTask.class))
                .inAdapterView(allOf(withId(R.id.backlog_tasklist)))
                .atPosition(0).perform(longClick());
        onView(withText("Delete")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }

    @SuppressWarnings("unchecked")
    public void testEditTask() {
        onData(instanceOf(MainTask.class))
                .inAdapterView(allOf(withId(R.id.backlog_tasklist)))
                .atPosition(0).perform(longClick());
        onView(withText("Edit")).perform(click());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(typeText("des"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.task_priority_edit)).perform(click());
        onView(withText("Urgent")).perform(click());
        onView(withId(Menu.FIRST)).perform(click());
    }

}
