package ch.epfl.scrumtool.gui.test;

import static ch.epfl.scrumtool.gui.utils.test.CustomMatchers.withError;
import static ch.epfl.scrumtool.gui.utils.test.CustomMatchers.withPriority;
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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.robotium.solo.Solo;

import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.TaskOverviewActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

/**
 * 
 * @author sylb
 *
 */
public class TaskOverviewActivityTest extends ActivityInstrumentationTestCase2<TaskOverviewActivity> {

    private static final MainTask TASK = MockData.TASK1;
    private static final Project PROJECT = MockData.MURCS;
    
    private List<Issue> issuesList = new ArrayList<Issue>();

    private static final String TEST_TEXT = MockData.TEST_TEXT;
    private static final String VERY_LONG_TEXT = MockData.VERY_LONG_TEXT;
    
    private Solo solo = null;
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    
    public TaskOverviewActivityTest() {
        super(TaskOverviewActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Client.setScrumClient(mockClient);
        
        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        mockIntent.putExtra(MainTask.SERIALIZABLE_NAME, TASK);
        setActivityIntent(mockIntent);
    }
    
    @SuppressWarnings("unchecked")
    private void setSuccessFulLoadOperation() {
        issuesList = MockData.generateIssueLists();
        Answer<Void> loadIssuesAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).interactionDone(issuesList);
                return null;
            }
        };
        
        Mockito.doAnswer(loadIssuesAnswer).when(mockClient).loadIssues(Mockito.any(MainTask.class),
                Mockito.any(Callback.class));
        getActivity();
    }
    
    @SuppressWarnings("unchecked")
    private void setFailedLoadOperation() {
        solo = new Solo(getInstrumentation());
        
        Answer<Void> loadIssuesAnswerFail = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).failure(MockData.ERROR_MESSAGE);
                return null;
            }
        };
        
        Mockito.doAnswer(loadIssuesAnswerFail).when(mockClient).loadIssues(Mockito.any(MainTask.class),
                Mockito.any(Callback.class));
        getActivity();
    }
    
    @LargeTest
    public void testTaskOverviewAllFieldsAreDisplayed() {
        setSuccessFulLoadOperation();
        // check that all fields are displayed
        onView(withId(R.id.task_name)).check(matches(isDisplayed()));
        onView(withId(R.id.task_desc)).check(matches(isDisplayed()));
        onView(withId(R.id.task_priority)).check(matches(isDisplayed()));
        onView(withId(R.id.task_priority_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.task_slate_estimation)).check(matches(isDisplayed()));
        onView(withId(R.id.issue_list)).check(matches(isDisplayed()));
        onView(withId(Menu.FIRST)).check(matches(isDisplayed()));
        onView(withId(R.id.action_overflow)).check(matches(isDisplayed()));
    }
    
    @LargeTest
    public void testTaskOverviewCheckClickableFields() {
        setSuccessFulLoadOperation();
        // check that some fields are clickable
        onView(withId(R.id.task_name)).check(matches(isClickable()));
        onView(withId(R.id.task_desc)).check(matches(isClickable()));
        onView(withId(R.id.task_priority)).check(matches(isClickable()));
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
        onView(withId(R.id.action_overflow)).check(matches(isClickable()));
    }
    
    @SuppressWarnings("unchecked")
    @LargeTest
    public void testOverviewModifyTask() throws InterruptedException {
        setSuccessFulLoadOperation();
        // check if the fields are displayed correctly
        onView(withId(R.id.task_name)).check(matches(withText(TASK.getName())));
        onView(withId(R.id.task_desc)).check(matches(withText(TASK.getDescription())));
        onView(withId(R.id.task_priority)).check(matches(withPriority(TASK.getPriority())));
        
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
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        
        // check if the values have been changed
        onView(withId(R.id.task_name)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.task_desc)).check(matches(withText(TEST_TEXT)));
        onView(withId(R.id.task_priority)).check(matches(withPriority(Priority.HIGH)));
    }
    
    @LargeTest
    public void testOverviewCheckBadUserInputs() throws InterruptedException {
        setSuccessFulLoadOperation();
        
        Resources res = getInstrumentation().getTargetContext().getResources();
        
        nameIsEmpty(res);
        descriptionIsEmpty(res);
        largeInputForTheName(res);
    }
    
    @LargeTest
    public void testOverviewTestIssueList() {
        setSuccessFulLoadOperation();
        
        testIssuesAreDisplayedInTheList();
        testLongClickOnIssueToOpenContextMenu();
        
    }
    
    @LargeTest
    public void testToastMessage() throws Exception {
        setFailedLoadOperation();
        assertTrue(solo.waitForText(MockData.ERROR_MESSAGE));
    }
    private void nameIsEmpty(Resources res) {
        // empty the task name
        onView(withId(R.id.task_name)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());

        // check the value in the field is empty
        onView(withId(R.id.popup_user_input)).check(matches(withText("")));

        // click on save button and check the error on the name
        onView(withText(android.R.string.ok)).perform(click());
        onView(withId(R.id.popup_user_input)).check(matches(withError(res.getString(R.string.error_field_required))));
        onView(withId(R.id.popup_user_input)).perform(pressBack());
    }
    
    private void descriptionIsEmpty(Resources res) {
        // empty the task description
        onView(withId(R.id.task_desc)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());

        // check the value in the field is empty
        onView(withId(R.id.popup_user_input)).check(matches(withText("")));

        // click on save button and check the error on the description
        onView(withText(android.R.string.ok)).perform(click());
        onView(withId(R.id.popup_user_input)).check(matches(withError(res.getString(R.string.error_field_required))));
        onView(withId(R.id.popup_user_input)).perform(pressBack());
    }
    
    private void largeInputForTheName(Resources res) {
        // fill the task name with a large input
        onView(withId(R.id.task_name)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());
        onView(withId(R.id.popup_user_input)).perform(typeText(VERY_LONG_TEXT));

        // check the value in the field is correct
        onView(withId(R.id.popup_user_input)).check(matches(withText(VERY_LONG_TEXT)));

        // click on save button and check the error on the name
        onView(withText(android.R.string.ok)).perform(click());
        onView(withId(R.id.popup_user_input)).check(matches(withError(res.getString(R.string.error_name_too_long))));
        onView(withId(R.id.popup_user_input)).perform(pressBack());
        onView(withId(R.id.popup_user_input)).perform(pressBack());
    }
    
    @SuppressWarnings("unchecked")
    public void testIssuesAreDisplayedInTheList() {
        setSuccessFulLoadOperation();
        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.issue_list))).atPosition(0)
            .check(matches(isDisplayed()));
        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.issue_list))).atPosition(1)
            .check(matches(isDisplayed()));
//        FIXME : find how to test if the field is clickable and if the correct value is displayed
//        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.issue_list))).atPosition(0)
//            .check(matches(isClickable()));
//        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.issue_list))).atPosition(0)
//            .check(matches(isClickable()));
//        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.issue_list))).atPosition(0)
//            .check(matches(withText(ISSUE1.getName())));

    }
    
    @SuppressWarnings("unchecked")
    public void testLongClickOnIssueToOpenContextMenu() {
        setSuccessFulLoadOperation();
        onData(instanceOf(Issue.class)).inAdapterView(allOf(withId(R.id.issue_list))).
        atPosition(0).perform(longClick());
        onView(withText(R.string.action_edit)).check(matches(isDisplayed()));
        onView(withText(R.string.action_delete)).check(matches(isDisplayed()));
        onView(withText(R.string.action_markDoneUndone)).check(matches(isDisplayed()));
//        onView(withText(R.string.action_edit)).check(matches(isClickable()));
//        onView(withText(R.string.action_delete)).check(matches(isClickable()));
//        onView(withText(R.string.action_markDoneUndone)).check(matches(isClickable()));
    }
}
