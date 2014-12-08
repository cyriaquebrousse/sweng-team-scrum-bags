package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withError;
import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withPlayer;
import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withSprint;

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
import android.view.Menu;

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
    private static final Float ESTIMATION = 2f;
    private static final Float LARGE_ESTIMATION = 125f;
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

        setActivityIntent(createMockIntent());
        getActivity();

        onView(withId(Menu.FIRST)).check(
                matches(isClickable()));
        newIssue();
    }

    @LargeTest
    public void testEditIssueAllFieldsAreDisplayed() throws InterruptedException {

        setActivityIntent(createMockIntent());
        getActivity();
        checkAllFields();
    }

    @LargeTest
    public void testEditIssueUpdateIssue() throws InterruptedException {

        Intent intent = createMockIntent();
        intent.putExtra(Issue.SERIALIZABLE_NAME, ISSUE);

        setActivityIntent(intent);
        getActivity();

        onView(withId(Menu.FIRST)).check(
                matches(isClickable()));
        updateIssue();
    }

    @LargeTest
    public void testEditIssueBadUserInputs() throws InterruptedException {

        Intent intent = createMockIntent();

        setActivityIntent(intent);
        getActivity();
        Resources res = getInstrumentation().getTargetContext().getResources();

        nameIsEmpty(res);
        descriptionIsEmpty(res);
        estimationIsEmpty(res);
        largeInputForTheName(res);
        largeInputForTheEstimation(res);
    }

    private Intent createMockIntent() {

        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        mockIntent.putExtra(MainTask.SERIALIZABLE_NAME, TASK);

        return mockIntent;
    }
    
    private void checkAllFields() throws InterruptedException {
        // check that all fields are displayed
        onView(withId(R.id.issue_name_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_description_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_estimation_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_assignee_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_sprint_spinner)).check(matches(isDisplayed()));
        onView(withId(Menu.FIRST)).check(matches(isDisplayed()));
    }

    @SuppressWarnings("unchecked")
    private void newIssue() throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.issue_name_edit)).perform(typeText(TEST_TEXT));
        onView(withId(R.id.issue_description_edit)).perform(typeText(TEST_TEXT));
        onView(withId(R.id.issue_estimation_edit)).perform(clearText());
        onView(withId(R.id.issue_estimation_edit)).perform(typeText(ESTIMATION.toString()), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_assignee_spinner)).perform(click());
        onData(allOf(is(instanceOf(Player.class)))).atPosition(0).perform(click());
        onView(withId(R.id.issue_sprint_spinner)).perform(click());
        onData(allOf(is(instanceOf(Sprint.class)))).atPosition(1).perform(click());

        // check the values in the fields of the new issue
        onView(withId(R.id.issue_name_edit)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.issue_description_edit)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.issue_estimation_edit)).check(matches(withText(ESTIMATION.toString())));
        onView(withId(R.id.issue_assignee_spinner)).check(matches(withPlayer(PLAYER)));
        onView(withId(R.id.issue_sprint_spinner)).check(matches(withSprint(SPRINT2)));
        // click on save button
        onView(withId(Menu.FIRST)).perform(click());
    }

    @SuppressWarnings("unchecked")
    private void updateIssue() throws InterruptedException {
        // check if the fields are displayed correctly
        onView(withId(R.id.issue_name_edit)).check(matches(withText(ISSUE.getName())));
        onView(withId(R.id.issue_description_edit)).check(matches(withText(ISSUE.getDescription())));
        onView(withId(R.id.issue_estimation_edit)).check(matches(withText(
                ((Float) ISSUE.getEstimatedTime()).toString())));
        onView(withId(R.id.issue_assignee_spinner)).check(matches(withPlayer(ISSUE.getPlayer())));
        onView(withId(R.id.issue_sprint_spinner)).check(matches(withSprint(ISSUE.getSprint())));

        // fill the different fields
        onView(withId(R.id.issue_name_edit)).perform(clearText());
        onView(withId(R.id.issue_name_edit)).perform(typeText(TEST_TEXT));
        onView(withId(R.id.issue_description_edit)).perform(clearText());
        onView(withId(R.id.issue_description_edit)).perform(typeText(TEST_TEXT));
        onView(withId(R.id.issue_estimation_edit)).perform(clearText());
        onView(withId(R.id.issue_estimation_edit)).perform(typeText(ESTIMATION.toString()), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_assignee_spinner)).perform(click());
        onData(allOf(is(instanceOf(Player.class)))).atPosition(0).perform(click());
        onView(withId(R.id.issue_sprint_spinner)).perform(click());
        onData(allOf(is(instanceOf(Sprint.class)))).atPosition(1).perform(click());

        // check the values in the fields of the new issue
        onView(withId(R.id.issue_name_edit)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.issue_description_edit)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.issue_estimation_edit)).check(matches(withText(ESTIMATION.toString())));
        onView(withId(R.id.issue_assignee_spinner)).check(matches(withPlayer(PLAYER)));
        onView(withId(R.id.issue_sprint_spinner)).check(matches(withSprint(SPRINT2)));
        // click on save button
        onView(withId(Menu.FIRST)).perform(click());
    }

    private void nameIsEmpty(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.issue_name_edit)).perform(clearText(), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);

        // check the values in the fields of the new issue
        onView(withId(R.id.issue_name_edit)).check(matches(withText("")));

        // click on save button et check the error on the name
        onView(withId(Menu.FIRST)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_name_edit)).check(matches(withError(res.getString(R.string.error_field_required))));
    }

    private void descriptionIsEmpty(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.issue_description_edit)).perform(clearText(), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);

        // check the values in the fields of the new issue
        onView(withId(R.id.issue_description_edit)).check(matches(withText("")));

        // click on save button et check the error on the name
        onView(withId(Menu.FIRST)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_description_edit)).check(
                matches(withError(res.getString(R.string.error_field_required))));
    }

    private void estimationIsEmpty(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.issue_estimation_edit)).perform(clearText(), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);

        // check the values in the fields of the new issue
        onView(withId(R.id.issue_estimation_edit)).check(matches(withText("")));

        // click on save button and check the error on the estimation
        onView(withId(Menu.FIRST)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_estimation_edit)).check(
                matches(withError(res.getString(R.string.error_field_required))));
    }

    private void largeInputForTheName(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.issue_name_edit)).perform(clearText());
        onView(withId(R.id.issue_name_edit)).perform(typeText(VERY_LONG_TEXT), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);

        // check the large value for the name
        onView(withId(R.id.issue_name_edit)).check(matches(withText(VERY_LONG_TEXT)));

        // click on save button and check the error on the name
        onView(withId(Menu.FIRST)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_name_edit)).check(matches(withError(res.getString(R.string.error_name_too_long))));
    }
    
    private void largeInputForTheEstimation(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.issue_estimation_edit)).perform(typeText(LARGE_ESTIMATION.toString()), closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);

        // check the values in the fields of the new issue
        onView(withId(R.id.issue_estimation_edit)).check(matches(withText(LARGE_ESTIMATION.toString())));

        // click on save button et check the error on the name
        onView(withId(Menu.FIRST)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.issue_estimation_edit)).check(matches(withError(res.getString(R.string.error_estimation_too_big))));
    }
}
