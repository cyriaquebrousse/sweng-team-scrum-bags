package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import android.test.ActivityInstrumentationTestCase2;

/**
 * @author LeoWirz
 * 
 */
public class DashboardActivityTest extends
        ActivityInstrumentationTestCase2<DashboardActivity> {

    public DashboardActivityTest() {
        super(DashboardActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
    
    public void testProjectsButton() {
        onView(withId(R.id.dashboard_button_project_list)).check(
                matches(isClickable()));
        onView(withId(R.id.dashboard_button_project_list)).perform(click());
    }

    public void testProfilButton() {
        onView(withId(R.id.dashboard_button_my_profile)).check(
                matches(isClickable()));
        onView(withId(R.id.dashboard_button_my_profile)).perform(click());
    }

}
