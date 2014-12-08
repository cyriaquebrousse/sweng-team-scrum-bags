package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static ch.epfl.scrumtool.gui.utils.CustomMatchers.withError;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.utils.MockData;

import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

/**
 * 
 * @author sylb
 *
 */
public class TaskEditActivityTest extends ActivityInstrumentationTestCase2<TaskEditActivity> {

    private static final MainTask TASK = MockData.TASK1;
    private static final Project PROJECT = MockData.MURCS;
    
    private static final String TASK_TEST = "task test";
    private static final String VERY_LONG_TEXT = "blablablablablablablablablablablablabla"
            + "blablablablablablablablablablablablablablablablablablablablablablablablablablabla";
    
    private static final long THREADSLEEPTIME = 100;
    
    public TaskEditActivityTest() {
        super(TaskEditActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @LargeTest
    public void testEditIssueAllFieldsAreDisplayed() throws InterruptedException {

        setActivityIntent(createMockIntent());
        getActivity();
        checkAllFields();
    }
    
    @LargeTest
    public void testEditTaskNewTask() throws InterruptedException {
        
        setActivityIntent(createMockIntent());
        getActivity();
        
        onView(withId(R.id.task_edit_button_next)).check(
                matches(isClickable()));
        newTask();
    }
    
    @LargeTest
    public void testEditTaskUpdateTask() throws InterruptedException {
        
        Intent mockIntent = createMockIntent();
        mockIntent.putExtra(MainTask.SERIALIZABLE_NAME, TASK);
        
        setActivityIntent(mockIntent);
        getActivity();
        
        onView(withId(R.id.task_edit_button_next)).check(
                matches(isClickable()));
        updateTask();
    }
    
    @LargeTest
    public void testBadInputsForTheNameAndDescription() throws InterruptedException {
        
        setActivityIntent(createMockIntent());
        getActivity();
        Resources res = getInstrumentation().getTargetContext().getResources();
        
        nameIsEmpty(res);
        descriptionIsEmpty(res);
        largeInputForTheName(res);
        largeInputForTheDescription(res);
    }
    
    private Intent createMockIntent() {
        
        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        
        return mockIntent;
    }
    
    private void checkAllFields() throws InterruptedException {
        // check that all fields are displayed
        onView(withId(R.id.task_name_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.task_description_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.task_priority_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.task_edit_button_next)).check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    private void newTask() throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
        onView(withId(R.id.task_description_edit)).perform(typeText(TASK_TEST), ViewActions.closeSoftKeyboard());

        // wait a bit after closing the keyboard
        Thread.sleep(THREADSLEEPTIME);

        // set the priority of the task to high
        onView(withId(R.id.task_priority_edit)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());

        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_priority_edit)).check(matches(withText("HIGH")));

        // click on save button
        onView(withId(R.id.task_edit_button_next)).perform(click());
    }

    @SuppressWarnings("unchecked")
    private void updateTask() throws InterruptedException {
        // check if the fields are displayed correctly
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK.getName())));
        onView(withId(R.id.task_description_edit)).check(matches(withText(TASK.getDescription())));
        
        // fill the fields with other values
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
        onView(withId(R.id.task_description_edit)).perform(typeText(" " + TASK_TEST), ViewActions.closeSoftKeyboard());
        
     // wait a bit after closing the keyboard
        Thread.sleep(THREADSLEEPTIME);
        
        // set the priority of the task to high
        onView(withId(R.id.task_priority_edit)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(TASK.getDescription() + " " + TASK_TEST)));
        onView(withId(R.id.task_priority_edit)).check(matches(withText("HIGH")));
        
        // click on save button
        onView(withId(R.id.task_edit_button_next)).perform(click());
    }
    
    private void nameIsEmpty(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(typeText(TASK_TEST), ViewActions.closeSoftKeyboard());
        
        // wait a bit after closing the keyboard
        Thread.sleep(THREADSLEEPTIME);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText("")));
        onView(withId(R.id.task_description_edit)).check(matches(withText(TASK_TEST)));
        
        // click on save button and check the error on the name
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.task_name_edit)).check(matches(withError(res.getString(R.string.error_field_required))));
    }
    
    private void descriptionIsEmpty(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
        onView(withId(R.id.task_description_edit)).perform(clearText(), ViewActions.closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_description_edit)).check(matches(withText("")));
        
        // click on save button and check the error on the description
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.task_description_edit)).check(matches(
                withError(res.getString(R.string.error_field_required))));
    }
    
    private void largeInputForTheName(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(VERY_LONG_TEXT));
        onView(withId(R.id.task_description_edit)).perform(typeText(TASK_TEST), ViewActions.closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(VERY_LONG_TEXT)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(TASK_TEST)));
        
        // click on save button and check the error on the name
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.task_name_edit)).check(matches(withError(res.getString(R.string.error_name_too_long))));
    }
    
    private void largeInputForTheDescription(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
        onView(withId(R.id.task_description_edit)).perform(typeText(VERY_LONG_TEXT), ViewActions.closeSoftKeyboard());
        Thread.sleep(THREADSLEEPTIME);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(VERY_LONG_TEXT)));
        
        // click on save button and check that there are no errors on the name and the description
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(THREADSLEEPTIME);
        onView(withId(R.id.task_name_edit)).check(matches(withError("no error")));
        onView(withId(R.id.task_description_edit)).check(matches(withError("no error")));
    }
    
}
