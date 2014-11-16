package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.*;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;

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

    public void testEditProfilButton() {
        onView(withId(R.id.dashboard_button_edit_my_profile)).check(
                matches(isClickable()));
        onView(withId(R.id.dashboard_button_edit_my_profile)).perform(click());
    }

}
