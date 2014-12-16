package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.SprintOverviewActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import com.robotium.solo.Solo;

/**
 * 
 * @author sylb
 *
 */
public class SprintOverviewActivityTest extends ActivityInstrumentationTestCase2<SprintOverviewActivity> {

    private static final Project PROJECT = MockData.PROJECT;
    private static final Sprint SPRINT = MockData.SPRINT1;
    
    private List<Issue> issuesList = new ArrayList<Issue>();
    private List<Issue> unsprintedIssueList = new ArrayList<Issue>();

    private static final String TEST_TEXT = MockData.TEST_TEXT;

    private Solo solo = null;
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);

    public SprintOverviewActivityTest() {
        super(SprintOverviewActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Client.setScrumClient(mockClient);

        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        mockIntent.putExtra(Sprint.SERIALIZABLE_NAME, SPRINT);
        setActivityIntent(mockIntent);

    }

    @SuppressWarnings("unchecked")
    private void setSuccesfulLoadOperationsBoth() {
        issuesList = MockData.generateIssueLists();
        unsprintedIssueList = MockData.generateUnsprintedIssueLists();

        Answer<Void> loadIssueAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).interactionDone(issuesList);
                return null;
            }
        };

        Answer<Void> loadUnsprintedIssueAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).interactionDone(unsprintedIssueList);
                return null;
            }
        };

        Mockito.doAnswer(loadIssueAnswer).when(mockClient).loadIssues(
                Mockito.any(Sprint.class), Mockito.any(Callback.class));
        Mockito.doAnswer(loadUnsprintedIssueAnswer).when(mockClient).loadUnsprintedIssues(
                Mockito.any(Project.class), Mockito.any(Callback.class));
        
        getActivity();
    }
    
    @SuppressWarnings("unchecked")
    private void setFailedLoadOperationUnsprintedIssues() {
        solo = new Solo(getInstrumentation());
        
        issuesList = MockData.generateIssueLists();
        unsprintedIssueList = MockData.generateUnsprintedIssueLists();

        Answer<Void> loadIssueAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).interactionDone(issuesList);
                return null;
            }
        };
        
        Answer<Void> loadUnsprintedIssuesAnswerFail = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).failure(MockData.ERROR_MESSAGE);
                return null;
            }
        };
        
        Mockito.doAnswer(loadIssueAnswer).when(mockClient).loadIssues(
                Mockito.any(Sprint.class), Mockito.any(Callback.class));
        Mockito.doAnswer(loadUnsprintedIssuesAnswerFail).when(mockClient).loadUnsprintedIssues(
                Mockito.any(Project.class), Mockito.any(Callback.class));
       
        getActivity();
    }
    
    @SuppressWarnings("unchecked")
    private void setFailedLoadOperationIssues() {
        solo = new Solo(getInstrumentation());
        
        issuesList = MockData.generateIssueLists();
        unsprintedIssueList = MockData.generateUnsprintedIssueLists();

        Answer<Void> loadIssueAnswerFail = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).failure(MockData.ERROR_MESSAGE);
                return null;
            }
        };
        
        Answer<Void> loadUnsprintedIssuesAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).interactionDone(unsprintedIssueList);
                return null;
            }
        };
        
        Mockito.doAnswer(loadIssueAnswerFail).when(mockClient).loadIssues(
                Mockito.any(Sprint.class), Mockito.any(Callback.class));
        Mockito.doAnswer(loadUnsprintedIssuesAnswer).when(mockClient).loadUnsprintedIssues(
                Mockito.any(Project.class), Mockito.any(Callback.class));
       
        getActivity();
    }
    
    @LargeTest
    public void testSprintOverviewAllFieldsAreDisplayed() {
        setSuccesfulLoadOperationsBoth();
        // check that all fields are displayed
        onView(withId(R.id.sprint_overview_name)).check(matches(isDisplayed()));
        onView(withId(R.id.sprint_overview_deadline)).check(matches(isDisplayed()));
        onView(withId(R.id.sprint_overview_issue_list)).check(matches(isDisplayed()));
        onView(withId(Menu.FIRST)).check(matches(isDisplayed()));
        onView(withId(R.id.action_overflow)).check(matches(isDisplayed()));
    }

    @LargeTest
    public void testSprintOverviewCheckClickableFields() {
        setSuccesfulLoadOperationsBoth();
        // check that some fields are clickable
        onView(withId(R.id.sprint_overview_name)).check(matches(isClickable()));
        onView(withId(R.id.sprint_overview_deadline)).check(matches(isClickable()));
        onView(withId(R.id.sprint_overview_issue_list)).check(matches(isClickable()));
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
        onView(withId(R.id.action_overflow)).check(matches(isClickable()));
    }

    @LargeTest
    public void testSprintOverviewModifiySprint() throws InterruptedException {
        setSuccesfulLoadOperationsBoth();
        // check if the fields are displayed correctly
        onView(withId(R.id.sprint_overview_name)).check(matches(withText(SPRINT.getTitle())));
        //checkDeadline();

        // fill the modifiable fields with new values
        onView(withId(R.id.sprint_overview_name)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());
        onView(withId(R.id.popup_user_input)).perform(typeText(TEST_TEXT));
        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.sprint_overview_name)).check(matches(withText(TEST_TEXT)));
        //checkDeadline();
    }

    @LargeTest
    public void testIssuesLists() {
        testIssuesAreDisplayedInTheList();
        testUnsprintedIssuesAreDisplayed();
        testLongClickOnIssue();
    }
    
//    @LargeTest
//    public void testRemoveIssue() {
//        setSuccesfulLoadOperationsBoth();
//        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.sprint_overview_issue_list)))
//        .atPosition(0).perform(longClick());
//        onView(withText(R.string.action_delete)).perform(click());
//        onView(withText(android.R.string.yes)).perform(click());
//        
//        assertTrue(solo.waitForText("Issue removed from Sprint"));
//    }
    
    @LargeTest
    public void testToastMessageFailedUnsprintedIssue() throws Exception {
        setFailedLoadOperationUnsprintedIssues();
        onView(withId(Menu.FIRST)).perform(click());
        assertTrue(solo.waitForText(MockData.ERROR_MESSAGE));
    }
    
    @LargeTest
    public void testToastMessageFailedIssue() throws Exception {
        setFailedLoadOperationIssues();
        assertTrue(solo.waitForText(MockData.ERROR_MESSAGE));
    }

    @SuppressWarnings("unchecked")
    public void testIssuesAreDisplayedInTheList() {
        setSuccesfulLoadOperationsBoth();
        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.sprint_overview_issue_list))).atPosition(0)
        .check(matches(isDisplayed()));
        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.sprint_overview_issue_list))).atPosition(1)
        .check(matches(isDisplayed()));
    }

    @SuppressWarnings("unchecked")
    public void testUnsprintedIssuesAreDisplayed() {
        setSuccesfulLoadOperationsBoth();
        onView(withId(Menu.FIRST)).perform(click());
        onData(allOf(is(instanceOf(Issue.class)))).atPosition(0).check(matches(isDisplayed()));
        onData(allOf(is(instanceOf(Issue.class)))).atPosition(1).check(matches(isDisplayed()));
        onView(withText("Cancel")).perform(click());
    }

    
    @SuppressWarnings("unchecked")
    public void testLongClickOnIssue() {
        setSuccesfulLoadOperationsBoth();
        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.sprint_overview_issue_list)))
        .atPosition(0).perform(longClick());
        onView(withText(R.string.action_edit)).check(matches(isDisplayed()));
        onView(withText(R.string.action_delete)).check(matches(isDisplayed()));
    }

    public void checkDeadline() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(SPRINT.getDeadline());
        onView(withId(R.id.sprint_date_edit)).check(matches(withText(sdf.format(date.getTime()))));
    }
}
