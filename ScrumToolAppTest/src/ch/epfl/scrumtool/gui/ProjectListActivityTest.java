package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;

/**
 * @author LeoWirz
 * 
 */
public class ProjectListActivityTest extends
        ActivityInstrumentationTestCase2<ProjectListActivity> {

    public ProjectListActivityTest() {
        super(ProjectListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testAddNewProject() {
        onView(withId(Menu.FIRST)).perform(click());
        onView(withId(R.id.project_title_edit)).perform(typeText("espresso test"));
        onView(withId(R.id.project_description_edit)).perform(typeText("this should be removed automatically"), closeSoftKeyboard());
        onView(withId(R.id.project_edit_button_next)).check(matches(isClickable()));
        }
    
    public void testRemoveProject() {
//        TODO
//        onView(withId(R.id.project_list)).perform(longClick());
//        onView(withId(R.id.action_entity_delete)).perform(click());
    }

}
