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
import static org.mockito.Mockito.doAnswer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.SprintListActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import com.google.android.apps.common.testing.ui.espresso.DataInteraction;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

/**
 * 
 * @author
 *
 */
public class SprintListActivityTest extends ActivityInstrumentationTestCase2<SprintListActivity> {

    private static final DatabaseScrumClient MOCKCLIENT = Mockito.mock(DatabaseScrumClient.class);

    private static final String SPRINT_TITLE = "Sprint Title";
    private static final long SPRINT_DEADLINE = System.currentTimeMillis();

    private static final Sprint SPRINT = new Sprint.Builder()
        .setDeadline(SPRINT_DEADLINE)
        .setTitle(SPRINT_TITLE).build();
    private final List<Sprint> sprintList = new ArrayList<Sprint>();

    private static final Project PROJECT = MockData.MURCS;

    private final Answer<Void> loadAnswer = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) {
            ((Callback<List<Sprint>>) invocation.getArguments()[1]).interactionDone(sprintList);
            return null;
        }
    };

    private final Answer<Void> removeAnswer = new Answer<Void>() {
        @SuppressWarnings("unchecked")
        @Override
        public Void answer(InvocationOnMock invocation) {
            sprintList.remove(0);
            ((Callback<Void>) invocation.getArguments()[1]).interactionDone(null);
            return null;
        }
        
    };

    public SprintListActivityTest() {
        super(SprintListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sprintList.add(SPRINT);
        Client.setScrumClient(MOCKCLIENT);
        Intent openSprintListIntent = new Intent();
        openSprintListIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);

        doAnswer(loadAnswer).when(MOCKCLIENT)
            .loadSprints(Mockito.any(Project.class), Matchers.<Callback<List<Sprint>>>any());
        setActivityIntent(openSprintListIntent);

        getActivity();
    }
    
    @SuppressLint("SimpleDateFormat") public void testDisplayLoadedSprints() {
        @SuppressWarnings("unchecked")
        DataInteraction listInteraction = onData(instanceOf(Sprint.class))
            .inAdapterView(allOf(withId(R.id.sprint_list)));
        
        listInteraction.atPosition(0).onChildView(withId(R.id.sprint_row_name))
            .check(matches(withText(SPRINT_TITLE)));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        
        listInteraction.atPosition(0).onChildView(withId(R.id.sprint_row_date))
            .check(matches(withText(sdf.format(SPRINT_DEADLINE))));
    }

    @SuppressWarnings("unchecked")
    public void testRemoveSprintOk() {
        doAnswer(removeAnswer).when(MOCKCLIENT)
            .deleteProject(Mockito.any(Project.class), Matchers.<Callback<Void>>any());

        onData(instanceOf(Sprint.class)).inAdapterView(allOf(withId(R.id.sprint_list)))
            .atPosition(0).perform(ViewActions.longClick());
        onView(withText("Delete")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        // check if list is empty now
        onView(withId(R.id.swipe_update_empty_sprint_list)).check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testRemoveSprintCancel() {
        doAnswer(removeAnswer).when(MOCKCLIENT)
            .deleteSprint(Mockito.any(Sprint.class), Matchers.<Callback<Void>>any());

        onData(instanceOf(Sprint.class)).inAdapterView(allOf(withId(R.id.sprint_list)))
            .atPosition(0).perform(ViewActions.longClick());
        onView(withText("Delete")).perform(click());
        onView(withId(android.R.id.button2)).perform(click());
        // check if list still contains sprint
        DataInteraction listInteraction = onData(instanceOf(Sprint.class))
                .inAdapterView(allOf(withId(R.id.sprint_list)));
            
        listInteraction.atPosition(0).onChildView(withId(R.id.sprint_row_name))
            .check(matches(withText(SPRINT_TITLE)));

        listInteraction.atPosition(0).onChildView(withId(R.id.sprint_row_date))
            .check(matches(withText(getDateStringFromLong(SPRINT_DEADLINE))));
    }


    public void testAddSprint() throws InterruptedException {
        onView(withId(Menu.FIRST)).perform(ViewActions.click());
        Thread.sleep(1000);
        onView(withId(R.id.sprint_date_edit)).check(matches(isDisplayed()));
    }

    @SuppressWarnings("unchecked")
    public void testEditSprint() {
        onData(instanceOf(Sprint.class)).inAdapterView(allOf(withId(R.id.sprint_list)))
            .atPosition(0).perform(ViewActions.longClick());
        onView(withText("Edit")).perform(click());
        onView(withId(R.id.sprint_name_edit)).check(matches(withText(SPRINT_TITLE)));
        onView(withId(R.id.sprint_date_edit)).check(matches(withText(getDateStringFromLong(SPRINT_DEADLINE))));
    }

    /* Helper functions */
    @SuppressLint("SimpleDateFormat") private String getDateStringFromLong(final long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(date).toString();
    }

}
