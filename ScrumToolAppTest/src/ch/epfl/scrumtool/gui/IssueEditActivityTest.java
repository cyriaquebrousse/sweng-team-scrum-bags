package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withError;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.utils.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

/**
 * 
 * @author sylb
 *
 */
public class IssueEditActivityTest extends ActivityInstrumentationTestCase2<IssueEditActivity> {

    private static final Issue ISSUE = MockData.ISSUE1;
    private static final MainTask TASK = MockData.TASK1;
    private static final Project PROJECT = MockData.MURCS;
    private static final Player PLAYER = MockData.VINCENT_ADMIN;
    private static final Sprint SPRINT1 = MockData.SPRINT1;
    private static final Sprint SPRINT2 = MockData.SPRINT2;
    
    private static final String TEST_TEXT = "test text";
    private static final String VERY_LONG_TEXT = "blablablablablablablablablablablablabla"
            + "blablablablablablablablablablablablablablablablablablablablablablablablablablabla";
    
    private static final long THREADSLEEPTIME = 100;

    private List<Player> playerList = new ArrayList<Player>();
    private List<Sprint> sprintList = new ArrayList<Sprint>();

    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);

    public IssueEditActivityTest() {
        super(IssueEditActivity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(mockClient);
        
        playerList.add(PLAYER);
        sprintList.add(SPRINT1);
        sprintList.add(SPRINT2);
        
        Answer<Void> loadPlayersAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Player>>) invocation.getArguments()[1]).interactionDone(playerList);
                return null;
            }
        };
        
        Answer<Void> loadSprintsAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Sprint>>) invocation.getArguments()[1]).interactionDone(sprintList);
                return null;
            }
        };
        
        Mockito.doAnswer(loadPlayersAnswer).when(mockClient).loadPlayers(Mockito.any(Project.class),
                Mockito.any(Callback.class));
        Mockito.doAnswer(loadSprintsAnswer).when(mockClient).loadSprints(Mockito.any(Project.class),
                Mockito.any(Callback.class));
        
    }

    @LargeTest
    public void testEditIssueNewIssue() throws InterruptedException {

        setActivityIntent(createMockIntentNewIssue());
        getActivity();

        onView(withId(R.id.issue_edit_button_next)).check(
                    matches(isClickable()));
        newIssue();
    }

    private Intent createMockIntentNewIssue() {

        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        mockIntent.putExtra(MainTask.SERIALIZABLE_NAME, TASK);

        return mockIntent;
    }

    @SuppressWarnings("unchecked")
    private void newIssue() throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.issue_name_edit)).perform(typeText(TEST_TEXT));
        onView(withId(R.id.issue_description_edit)).perform(typeText(TEST_TEXT), closeSoftKeyboard());
//        onView(withId(R.id.issue_estimation_edit)).perform(clearText());
//        onView(withId(R.id.issue_estimation_edit)).perform(typeText(), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_assignee_spinner)).perform(click());
        onData(allOf(is(instanceOf(Player.class)))).atPosition(0).perform(click());
        onView(withId(R.id.sprint_spinner)).perform(click());
        onData(allOf(is(instanceOf(Sprint.class)))).atPosition(1).perform(click());

        // check the values in the fields of the new issue
        onView(withId(R.id.issue_name_edit)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.issue_description_edit)).check(matches(withText(TEST_TEXT)));
//        onView(withId(R.id.issue_estimation_edit)).check(matches(withText(ISSUE_ESTIMATION.toString())));
//        onView(withId(R.id.issue_assignee_spinner)).check(matches(withText(PLAYER.getUser().getName())));
        // click on save button
        onView(withId(R.id.issue_edit_button_next)).perform(click());
    }

}
