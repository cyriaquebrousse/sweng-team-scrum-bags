package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import android.view.Menu;
import static org.hamcrest.Matchers.not;


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
        testAddProject();
    }

    public void testProfilButton() {
        onView(withId(R.id.dashboard_button_my_profile)).check(
                matches(isClickable()));
        onView(withId(R.id.dashboard_button_my_profile)).perform(click());
    }

    @Suppress
    public void testAddProject() {
        onView(withId(Menu.FIRST)).perform(click());
        onView(withId(R.id.project_title_edit)).perform(typeText("espresso test"));
        onView(withId(R.id.project_description_edit)).perform(typeText("Project for test purpose, this should be removed automatically"));
        onView(withId(R.id.project_edit_button_next)).check(matches(isClickable()));
        onView(withId(R.id.project_edit_button_next)).perform(click());
    }
}
