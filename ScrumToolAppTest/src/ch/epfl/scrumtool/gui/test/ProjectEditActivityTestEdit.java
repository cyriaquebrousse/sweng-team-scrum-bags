package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;

import org.mockito.Mockito;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.ProjectEditActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.network.Session;

/**
 * @author AlexVeuthey
 */
public class ProjectEditActivityTestEdit extends ActivityInstrumentationTestCase2<ProjectEditActivity> {
    
    private Project project = MockData.MURCS;
    private User user = MockData.JOEY;
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    
    private String editName = "project name";
    private String editDescription = "project description";
    
    private Activity activity;
    
    public ProjectEditActivityTestEdit() {
        super(ProjectEditActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        new Session(user){
            //This is used to trick authentication for tests
        };
        Client.setScrumClient(mockClient);
        
        Intent intent = new Intent();
        intent.setClassName("ch.epfl.scrumtool.gui", "ch.epfl.scrumtool.gui.ProjectEditActivity");
        intent.putExtra(Project.SERIALIZABLE_NAME, project);
        
        setActivityIntent(intent);
        
        activity = getActivity();
    }
    
    public void testSaveButtonIsClickable() {
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
    }
    
    public void testTitleIsEditProject() {
        assertEquals(activity.getResources().getString(R.string.title_activity_project_edit), activity.getTitle());
    }
    
    public void testFieldsAreEditable() {
        ViewInteraction name = onView(withId(R.id.project_title_edit));
        name.perform(click());
        name.perform(clearText());
        name.perform(typeText(editName));
        name.check(matches(withText(editName)));
        
        ViewInteraction description = onView(withId(R.id.project_description_edit));
        description.perform(click());
        description.perform(clearText());
        description.perform(typeText(editDescription));
        description.check(matches(withText(editDescription)));
    }
}
