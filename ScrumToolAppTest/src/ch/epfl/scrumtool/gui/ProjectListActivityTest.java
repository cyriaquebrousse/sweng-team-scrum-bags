package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.allOf;

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
        onView(withId(R.id.project_edit_button_next)).perform(click());
        }
    
    @SuppressWarnings("unchecked")
    public void testRemoveProject() {
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0).perform(longClick());
        onView(withText("Delete")).perform(click());
    }

}
