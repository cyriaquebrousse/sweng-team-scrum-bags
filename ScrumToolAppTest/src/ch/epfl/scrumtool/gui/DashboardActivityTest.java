package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.utils.CustomViewActions;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Menu;
import android.widget.ImageButton;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

import org.hamcrest.Matchers;
import org.junit.Before;

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
    
    @LargeTest
    public void testProjectsButton() {
        onView(withId(R.id.dashboard_button_project_list)).check(
                matches(isClickable()));
        onView(withId(R.id.dashboard_button_project_list)).perform(click());
        addProject();
//        addTask();
        removeProject();
    }

    @LargeTest
    public void testProfilButton() {
        onView(withId(R.id.dashboard_button_my_profile)).check(
                matches(isClickable()));
        onView(withId(R.id.dashboard_button_my_profile)).perform(click());
    }

    public void addProject() {
        //click on the "+" button
        onView(withId(Menu.FIRST)).perform(click());
        //edit fields
        onView(withId(R.id.project_title_edit)).perform(typeText("1e project test"));
        onView(withId(R.id.project_description_edit)).perform(typeText("auto-remove"), closeSoftKeyboard());
        //close keyboard (twice)
        ViewActions.closeSoftKeyboard();
        //click on save button
        onView(withId(R.id.project_edit_button_next)).perform(click());
    }
    

    @SuppressWarnings("unchecked")
    public void removeProject() {
        //refresh before
        onView(withId(R.id.swipe_update_project_list)).perform(CustomViewActions.swipeDown());
        //longclick on first project and click on "delete" menu
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0).perform(longClick());
        onView(withText("Delete")).perform(click());

//        click on the delete button, won't be like that
//        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
//        .onChildView(withId(R.id.project_delete_button)).perform(click());
    }
    

    @SuppressWarnings("unchecked")
    public void addTask() {
        //click on first project to open the card
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0).perform(click());
        
        //click on backlog icon
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
        .onChildView(withId(R.id.project_row_backlog)).perform(click());
        
        //click on "+" button
        onView(withId(Menu.FIRST)).perform(click());
        
        //fill the different fields
        onView(withId(R.id.task_name_edit)).perform(typeText("1e task test"));
        onView(withId(R.id.task_description_edit)).perform(typeText("auto-remove"));
        
        //close keyboard
        ViewActions.closeSoftKeyboard();
        
        //click on save button
        onView(withId(R.id.task_edit_button_next)).perform(click());
        pressBack();
        
      //click on first project to close the card
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.project_list))).atPosition(0).perform(click());
    }
    
    @SuppressWarnings("unchecked")
    public void removeTask() {
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.backlog_tasklist))).atPosition(0).perform(longClick());
        onView(withText("Delete")).perform(click());
    }
    
    @SuppressWarnings("unchecked")
    public void addIssue() {
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.backlog_tasklist))).atPosition(0).perform(click());
        
        onData(instanceOf(Project.class)).inAdapterView(allOf(withId(R.id.backlog_tasklist)))
        .onChildView(withId(R.id.backlog_tasklist)).perform(click());// l'id est fausse, t'en es là Leo
        
    }
}
