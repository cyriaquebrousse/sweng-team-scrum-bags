package ch.epfl.scrumtool.gui;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;

import ch.epfl.scrumtool.R;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;

public class SprintEditActivityTest extends ActivityInstrumentationTestCase2<SprintEditActivity> {

    Activity activity;
    
    public SprintEditActivityTest() {
        super(SprintEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }
    
    public void testSaveButton() {
        ViewInteraction done = onView(withId(R.id.sprintEditDoneButton));
        done.check(matches(isClickable()));
        done.perform(click());
        //activity.getIntent().putExtra(name, value);
    }
}
