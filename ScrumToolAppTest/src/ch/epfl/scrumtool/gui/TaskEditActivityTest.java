package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static ch.epfl.scrumtool.gui.utils.Matchers.withError;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;

import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

public class TaskEditActivityTest extends ActivityInstrumentationTestCase2<TaskEditActivity> {

    private static final String KEY_1 = "ID1";
    private static final String NAME_1 = "name1";
    private static final String DESCRIPTION_1 = "description";
    private static final Status STATUS_1 = Status.READY_FOR_ESTIMATION;
    private static final Priority PRIORITY_1 = Priority.NORMAL;
    
    private static final String KEY_2 = "ID2";
    private static final String NAME_2 = "name2";
    private static final String DESCRIPTION_2 = "description2";
    
    private static final String TASK_TEST = "task test";
    private static final String VERY_LONG_TEXT = "blablablablablablablablablablablablabla" +
            "blablablablablablablablablablablablablablablablablablablablablablablablablablabla";
    
    MainTask task = new MainTask.Builder()
        .setKey(KEY_1)
        .setName(NAME_1)
        .setDescription(DESCRIPTION_1)
        .setStatus(STATUS_1)
        .setPriority(PRIORITY_1).build();
    
    Project project = new Project.Builder()
        .setKey(KEY_2)
        .setName(NAME_2)
        .setDescription(DESCRIPTION_2).build();
    
    public TaskEditActivityTest() {
        super(TaskEditActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @LargeTest
    public void testEditTaskNewTask() throws InterruptedException {
        
        setActivityIntent(createMockIntentNewTask());
        getActivity();
        
        onView(withId(R.id.task_edit_button_next)).check(
                matches(isClickable()));
        newTask();
    }
    
    @LargeTest
    public void testEditTaskUpdateTask() throws InterruptedException {
        
        setActivityIntent(createMockIntentUpdateTask());
        getActivity();
        
        onView(withId(R.id.task_edit_button_next)).check(
                matches(isClickable()));
        updateTask();
    }
    
    @LargeTest
    public void testBadInputsForTheNameAndDescription() throws InterruptedException {
        
        setActivityIntent(createMockIntentNewTask());
        getActivity();
        Resources res = getInstrumentation().getTargetContext().getResources();
        

        NameIsEmpty(res);
        DescriptionIsEmpty(res);
        LargeInputForTheName(res);
        LargeInputForTheDescription(res);
    }
    
    private Intent createMockIntentNewTask() {
        
        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        
        return mockIntent;
    }
    
    private Intent createMockIntentUpdateTask() {

        Intent mockIntent = new Intent();
        mockIntent.putExtra(MainTask.SERIALIZABLE_NAME, task);
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        
        return mockIntent;
    }
    
    @SuppressWarnings("unchecked")
    private void newTask() throws InterruptedException {
          // fill the different fields
          onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
          onView(withId(R.id.task_description_edit)).perform(typeText(TASK_TEST), ViewActions.closeSoftKeyboard());
          
          // wait a bit after closing the keyboard
          Thread.sleep(1000);
          
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
        onView(withId(R.id.task_name_edit)).check(matches(withText(NAME_1)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(DESCRIPTION_1)));
        
        // fill the fields with other values
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
        onView(withId(R.id.task_description_edit)).perform(typeText(" " + TASK_TEST), ViewActions.closeSoftKeyboard());
        
     // wait a bit after closing the keyboard
        Thread.sleep(1000);
        
        // set the priority of the task to high
        onView(withId(R.id.task_priority_edit)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(DESCRIPTION_1 + " " + TASK_TEST)));
        onView(withId(R.id.task_priority_edit)).check(matches(withText("HIGH")));
        
        // click on save button
        onView(withId(R.id.task_edit_button_next)).perform(click());
    }
    
    private void NameIsEmpty(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(typeText(TASK_TEST), ViewActions.closeSoftKeyboard());
        
        // wait a bit after closing the keyboard
        Thread.sleep(1000);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText("")));
        onView(withId(R.id.task_description_edit)).check(matches(withText(TASK_TEST)));
        
        // click on save button et check the error on the name
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.task_name_edit)).check(matches(withError(res.getString(R.string.error_field_required))));
    }
    
    private void DescriptionIsEmpty(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
        onView(withId(R.id.task_description_edit)).perform(clearText(), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_description_edit)).check(matches(withText("")));
        
        // click on save button et check the error on the description
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.task_description_edit)).check(matches(withError(res.getString(R.string.error_field_required))));
    }
    
    private void LargeInputForTheName(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(VERY_LONG_TEXT));
        onView(withId(R.id.task_description_edit)).perform(typeText(TASK_TEST), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(VERY_LONG_TEXT)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(TASK_TEST)));
        
        // click on save button et check the error on the name
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.task_name_edit)).check(matches(withError(res.getString(R.string.error_field_required))));
    }
    
    private void LargeInputForTheDescription(Resources res) throws InterruptedException {
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(clearText());
        onView(withId(R.id.task_description_edit)).perform(clearText());
        onView(withId(R.id.task_name_edit)).perform(typeText(TASK_TEST));
        onView(withId(R.id.task_description_edit)).perform(typeText(VERY_LONG_TEXT), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        
        // check the values in the fields of the new task
        onView(withId(R.id.task_name_edit)).check(matches(withText(TASK_TEST)));
        onView(withId(R.id.task_description_edit)).check(matches(withText(VERY_LONG_TEXT)));
        
        // click on save button et check that there are no errors on the name and the description
        onView(withId(R.id.task_edit_button_next)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.task_name_edit)).check(matches(withError("no error")));
        onView(withId(R.id.task_description_edit)).check(matches(withError("no error")));
    }
    
}
