package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withError;
import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withPriority;
import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withPlayer;
import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withSprint;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.utils.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Menu;

/**
 * 
 * @author sylb
 *
 */
public class IssueOverviewActivityTest extends ActivityInstrumentationTestCase2<IssueOverviewActivity> {

    private static final MainTask TASK = MockData.TASK1;
    private static final Project PROJECT = MockData.MURCS;
    private static final Issue ISSUE1 = MockData.ISSUE1;
    private static final Sprint SPRINT1 = MockData.SPRINT1;
    private static final Sprint SPRINT2 = MockData.SPRINT2;
    private static final Player PLAYER1 = MockData.VINCENT_ADMIN;
    private static final Player PLAYER2 = MockData.JOEY_DEV;
    
    private List<Sprint> sprintsList = new ArrayList<Sprint>();
    private List<Player> playersList = new ArrayList<Player>();
    
    private static final String TEST_TEXT = "Test Text";
    private static final String VERY_LONG_TEXT = "blablablablablablablablablablablablabla"
            + "blablablablablablablablablablablablablablablablablablablablablablablablablablabla";
    
    private static final long THREADSLEEPTIME = 100;
    
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    
    public IssueOverviewActivityTest() {
        super(IssueOverviewActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Client.setScrumClient(mockClient);
        
        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        mockIntent.putExtra(MainTask.SERIALIZABLE_NAME, TASK);
        mockIntent.putExtra(Issue.SERIALIZABLE_NAME, ISSUE1);
        setActivityIntent(mockIntent);
        
        sprintsList.add(SPRINT1);
        sprintsList.add(SPRINT2);
        playersList.add(PLAYER1);
        playersList.add(PLAYER2);
        
        Answer<Void> loadPlayersAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Player>>) invocation.getArguments()[1]).interactionDone(playersList);
                return null;
            }
        };
        
        Answer<Void> loadSprintsAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Sprint>>) invocation.getArguments()[1]).interactionDone(sprintsList);
                return null;
            }
        };
        
        Mockito.doAnswer(loadPlayersAnswer).when(mockClient).loadPlayers(Mockito.any(Project.class),
                Mockito.any(Callback.class));
        Mockito.doAnswer(loadSprintsAnswer).when(mockClient).loadSprints(Mockito.any(Project.class),
                Mockito.any(Callback.class));
        getActivity();
    }
    
    @LargeTest
    public void testTaskOverviewAllFieldsAreDisplayed() {
        // check that all fields are displayed
        onView(withId(R.id.issue_name)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_desc)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_status)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_estimation_stamp)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_assignee_label)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_assignee_name)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_sprint)).check(matches(isDisplayed()));
        onView(withId(Menu.FIRST)).check(matches(isDisplayed()));
        onView(withId(R.id.action_overflow)).check(matches(isDisplayed()));
    }
    
    @LargeTest
    public void testTaskOverviewCheckClickableFields() {
        // check that some fields are clickable
        onView(withId(R.id.issue_name)).check(matches(isClickable()));
        onView(withId(R.id.issue_desc)).check(matches(isClickable()));
        onView(withId(R.id.issue_estimation_stamp)).check(matches(isClickable()));
        onView(withId(R.id.issue_sprint)).check(matches(isClickable()));
        onView(withId(R.id.issue_assignee_label)).check(matches(isClickable()));
        onView(withId(R.id.issue_assignee_name)).check(matches(isClickable()));
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
        onView(withId(R.id.action_overflow)).check(matches(isClickable()));
    }
    
    @SuppressWarnings("unchecked")
    @LargeTest
    public void testOverviewModifyIssue() throws InterruptedException {
        // check if the fields are displayed correctly
        onView(withId(R.id.issue_name)).check(matches(withText(ISSUE1.getName())));
        onView(withId(R.id.issue_desc)).check(matches(withText(ISSUE1.getDescription())));
//        onView(withId(R.id.issue_estimation_stamp)).check(matches(withText(ISSUE1.getEstimatedTime()));
        onView(withId(R.id.issue_sprint)).check(matches(with))
        
        
        
        // fill the modifiable fields with new values
        onView(withId(R.id.task_name)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());
        onView(withId(R.id.popup_user_input)).perform(typeText(TEST_TEXT));
        onView(withText(android.R.string.ok)).perform(click());
        
        onView(withId(R.id.task_desc)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());
        onView(withId(R.id.popup_user_input)).perform(typeText(TEST_TEXT));
        onView(withText(android.R.string.ok)).perform(click());
        
        // set the priority of the task to high
        onView(withId(R.id.task_priority)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());
        
        // check if the values have been changed
        onView(withId(R.id.task_name)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.task_desc)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.task_priority)).check(matches(withPriority(Priority.HIGH)));
    }

}
