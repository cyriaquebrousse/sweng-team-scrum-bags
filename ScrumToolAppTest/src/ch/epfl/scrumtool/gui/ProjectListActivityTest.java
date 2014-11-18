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
    
    public void testTrue() {
        assertTrue(true);
    }

    public void testAddNewProject() {
//        FIXME
//        onView(withId(R.id.action_new)).perform(click());
//        onView(withId(R.id.project_title_edit)).perform(typeText("project test purpose"));
//        onView(withId(R.id.project_description_edit)).perform(typeText("this project is generated automatically and should be erase automatically"));
//        onView(withId(R.id.project_edit_button_next)).perform(click());
    }
    
    public void testRemoveProject() {
        onView(withId(R.id.project_list)).perform(longClick());
        onView(withId(R.id.action_entity_delete)).perform(click());
    }

}
