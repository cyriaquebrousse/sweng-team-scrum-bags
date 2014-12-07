package ch.epfl.scrumtool.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;

public class SprintEditActivityTestCreate extends ActivityInstrumentationTestCase2<SprintEditActivity> {

    Activity activity;
    Sprint sprint = null;
    Sprint.Builder sprintBuilder;
    
    Project project;
    Project.Builder projectBuilder;
    
    public SprintEditActivityTestCreate() {
        super(SprintEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        projectBuilder = new Project.Builder();
        projectBuilder.setDescription("project description");
        projectBuilder.setKey("0");
        projectBuilder.setName("project name");
        
        project = projectBuilder.build();
        
        Intent intent = new Intent();
        
        intent.setClassName("ch.epfl.scrumtool.gui", "ch.epfl.scrumtool.gui.SprintEditActivity");
        
        intent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        intent.putExtra(Project.SERIALIZABLE_NAME, project);
        
        setActivityIntent(intent);
        
        activity = getActivity();
    }
    
    public void testSaveButtonIsClickable() {
        ViewInteraction save = onView(withId(R.id.sprintEditDoneButton));
        save.check(matches(withText("save")));
        save.check(matches(isClickable()));
    }
    
    public void testPickADateButCancel() {
        ViewInteraction datePick = onView(withId(R.id.sprintDateButton));
        datePick.check(matches(withText("Pick a date")));
        datePick.check(matches(ViewMatchers.isDisplayed()));
        datePick.check(matches(ViewMatchers.isClickable()));
        datePick.perform(ViewActions.click());
        DatePickerFragment fragment = (DatePickerFragment) activity.getFragmentManager().findFragmentByTag("datePicker");
        assertTrue(fragment.getShowsDialog());
        
        ViewInteraction cancelButton = onView(withText("Cancel"));
        cancelButton.check(matches(ViewMatchers.isClickable()));
        cancelButton.perform(ViewActions.click());
        
        onView(withId(R.id.sprintDate)).check(matches(withText("No date selected")));
    }
    
    public void testPickADate() throws Throwable {
        ViewInteraction datePick = onView(withId(R.id.sprintDateButton));
        datePick.check(matches(ViewMatchers.isClickable()));
        datePick.perform(ViewActions.click());
        
        final Calendar dateToSet = Calendar.getInstance();
        dateToSet.add(Calendar.DAY_OF_MONTH, 2);
        
        DatePickerFragment fragment = (DatePickerFragment) activity.getFragmentManager().findFragmentByTag("datePicker");
        final DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
        
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.updateDate(dateToSet.get(Calendar.YEAR), 
                        dateToSet.get(Calendar.MONTH), dateToSet.get(Calendar.DAY_OF_MONTH));
           }
        });
        
        ViewInteraction okButton = onView(withText("OK"));
        okButton.check(matches(ViewMatchers.isClickable()));
        okButton.perform(ViewActions.click());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        onView(withId(R.id.sprintDate)).check(matches(withText(sdf.format(dateToSet.getTime()))));
    }
    
    public void testTextViewsHaveRightNamesAndAreDisplayed() {
        onView(withId(R.id.sprintName)).check(matches(withText("Name")));
        onView(withId(R.id.sprintName)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.sprintEditDateTextView)).check(matches(withText("Date")));
        onView(withId(R.id.sprintEditDateTextView)).check(matches(ViewMatchers.isDisplayed()));
    }
    
    public void testTitleIsNewSprint() {
        assertEquals("New sprint", activity.getTitle());
    }
    
    public void testDeadlineIsNoDeadline() {
        onView(withId(R.id.sprintDate)).check(matches(withText("No date selected")));
    }
    
    public void testEditTextNameIsEditable() {
        ViewInteraction name = onView(withId(R.id.editName));
        name.check(matches(ViewMatchers.isClickable()));
        name.perform(click());
        name.perform(ViewActions.typeText("sprint title"));
        name.check(matches(withText("sprint title")));
    }
}
