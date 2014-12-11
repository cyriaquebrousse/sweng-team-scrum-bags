package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import org.mockito.Mockito;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.ProjectEditActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.network.Session;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;

/**
 * @author AlexVeuthey
 */
public class ProjectEditActivityTestCreate extends ActivityInstrumentationTestCase2<ProjectEditActivity> {
    
    private Activity activity;
    private User user = MockData.JOEY;
    private Project project = MockData.MURCS;
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    
    public ProjectEditActivityTestCreate() {
        super(ProjectEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        new Session(user){
            //This is used to trick authentication for tests
        };
        Client.setScrumClient(mockClient);
        
        activity = getActivity();
    }
    
    public void testTitleIsNewProject() {
        assertEquals(activity.getResources().getString(R.string.title_activity_project_edit_new), activity.getTitle());
    }
    
    public void testSaveButtonIsClickable() {
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
    }
    
    public void testFieldsAreEditable() {
        ViewInteraction name = onView(withId(R.id.project_title_edit));
        name.perform(click());
        name.perform(clearText());
        name.perform(typeText(project.getName()));
        name.check(matches(withText(project.getName())));
        
        ViewInteraction description = onView(withId(R.id.project_description_edit));
        description.perform(click());
        description.perform(clearText());
        description.perform(typeText(project.getDescription()));
        description.check(matches(withText(project.getDescription())));
    }
}
