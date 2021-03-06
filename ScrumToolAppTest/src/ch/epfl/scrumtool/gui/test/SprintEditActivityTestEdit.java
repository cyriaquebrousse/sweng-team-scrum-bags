package ch.epfl.scrumtool.gui.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.mockito.Mockito;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.SprintEditActivity;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.network.Session;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

/**
 * @author AlexVeuthey
 *
 */
public class SprintEditActivityTestEdit extends ActivityInstrumentationTestCase2<SprintEditActivity> {

    private Activity activity;
    private Sprint sprint;
    private Sprint.Builder sprintBuilder;
    private Project project;
    private Project.Builder projectBuilder;
    
    private User user = MockData.USER2;
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    
    public SprintEditActivityTestEdit() {
        super(SprintEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        new Session(user){
            //This is used to trick authentication for tests
        };
        Client.setScrumClient(mockClient);
        
        projectBuilder = new Project.Builder();
        projectBuilder.setDescription("project description");
        projectBuilder.setKey("0");
        projectBuilder.setName("project name");
        
        project = projectBuilder.build();
        
        sprintBuilder = new Sprint.Builder();
        Calendar deadlineCal = Calendar.getInstance();
        deadlineCal.add(Calendar.DAY_OF_MONTH, 2);
        sprintBuilder.setDeadline(deadlineCal.getTimeInMillis());
        sprintBuilder.setKey("0");
        sprintBuilder.setTitle("sprint title");
        
        sprint = sprintBuilder.build();
        
        Intent intent = new Intent();
        
        intent.setClassName("ch.epfl.scrumtool.gui", "ch.epfl.scrumtool.gui.SprintEditActivity");
        
        intent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        intent.putExtra(Project.SERIALIZABLE_NAME, project);
        
        setActivityIntent(intent);
        
        activity = getActivity();
    }
    
    public void testSaveButtonIsClickable() {
        ViewInteraction save = onView(withId(Menu.FIRST));
        save.check(matches(isClickable()));
    }
    
    public void testPickADateButCancel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        
        ViewInteraction datePick = onView(withId(R.id.sprint_date_edit));
        
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(sprint.getDeadline());
        
        datePick.check(matches(withText(sdf.format(date.getTime()))));
        datePick.check(matches(ViewMatchers.isDisplayed()));
        datePick.check(matches(ViewMatchers.isClickable()));
        datePick.perform(ViewActions.click());
        DatePickerFragment fragment = 
                (DatePickerFragment) activity.getFragmentManager().findFragmentByTag("datePicker");
        assertTrue(fragment.getShowsDialog());
        
        ViewInteraction cancelButton = onView(withId(android.R.id.button2));
        cancelButton.check(matches(ViewMatchers.isClickable()));
        cancelButton.perform(ViewActions.click());
        
        datePick.check(matches(withText(sdf.format(date.getTime()))));
    }
    
    public void testPickADate() {
        ViewInteraction datePick = onView(withId(R.id.sprint_date_edit));
        datePick.check(matches(ViewMatchers.isClickable()));
        datePick.perform(ViewActions.click());
        
        final Calendar dateToSet = Calendar.getInstance();
        dateToSet.add(Calendar.MONTH, 1);
        
        DatePickerFragment fragment = 
                (DatePickerFragment) activity.getFragmentManager().findFragmentByTag("datePicker");
        final DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
        
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.updateDate(dateToSet.get(Calendar.YEAR), 
                            dateToSet.get(Calendar.MONTH), dateToSet.get(Calendar.DAY_OF_MONTH));
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        ViewInteraction okButton = onView(withId(android.R.id.button1));
        okButton.check(matches(ViewMatchers.isClickable()));
        okButton.perform(ViewActions.click());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        onView(withId(R.id.sprint_date_edit)).check(matches(withText(sdf.format(dateToSet.getTime()))));
    }
    
    public void testDeadlineIsSprintsDeadline() {
        ViewInteraction deadline = onView(withId(R.id.sprint_date_edit));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(sprint.getDeadline());
        deadline.check(matches(withText(sdf.format(date.getTime()))));
    }
    
    public void testEditTextNameIsEditable() {
        ViewInteraction name = onView(withId(R.id.sprint_name_edit));
        name.check(matches(withText("sprint title")));
        name.check(matches(ViewMatchers.isClickable()));
        name.perform(click());
        name.perform(ViewActions.clearText());
        name.perform(ViewActions.typeText("sprint title 2"));
        name.check(matches(withText("sprint title 2")));
    }
}
