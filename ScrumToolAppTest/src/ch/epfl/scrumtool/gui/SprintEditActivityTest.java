package ch.epfl.scrumtool.gui;

import java.util.Calendar;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;

public class SprintEditActivityTest extends ActivityInstrumentationTestCase2<SprintEditActivity> {

    Activity activity;
    Sprint sprint = null;
    Sprint.Builder sprintBuilder;
    
    Project project;
    Project.Builder projectBuilder;
    
    public SprintEditActivityTest() {
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
        
        sprintBuilder = new Sprint.Builder();
        Calendar deadlineCal = Calendar.getInstance();
        deadlineCal.add(Calendar.DAY_OF_MONTH, 1);
        sprintBuilder.setDeadline(deadlineCal.getTimeInMillis());
        sprintBuilder.setKey("0");
        sprintBuilder.setTitle("sprint title");
        
        sprint = sprintBuilder.build();
        
        Intent intent = new Intent();
        
        intent.setClassName("ch.epfl.scrumtool.gui", "ch.epfl.scrumtool.gui.SprintEditActivity");
        
        intent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        intent.putExtra(Project.SERIALIZABLE_NAME, project);
        
        setActivityIntent(intent);
        
        activity = getActivity();
    }
    
    public void testSaveButtonIsClickable() {
        ViewInteraction save = onView(withId(R.id.sprintEditDoneButton));
        save.check(matches(isClickable()));
        save.check(matches(withText("save")));
        save.perform(click());
    }
    
    public void testTextViewsHaveRightNamesAndAreDisplayed() {
        onView(withId(R.id.sprintName)).check(matches(withText("Name")));
        onView(withId(R.id.sprintName)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.sprintEditDateTextView)).check(matches(withText("Date")));
        onView(withId(R.id.sprintEditDateTextView)).check(matches(ViewMatchers.isDisplayed()));
    }
    
    public void testTitleIsNewSprintWhenEditingOrCreating() {
        if (sprint == null) {
            assertEquals("New sprint", activity.getTitle());
        } else {
            assertEquals("sprint title", activity.getTitle());
        }
    }
    
    public void testEditTextNameIsEditable() {
        ViewInteraction name = onView(withId(R.id.editName));
        name.check(matches(withText("sprint title")));
        name.check(matches(ViewMatchers.isClickable()));
        name.perform(click());
        name.perform(ViewActions.clearText());
        name.perform(ViewActions.typeText("sprint title 2"));
        name.check(matches(withText("sprint title 2")));
    }
}
