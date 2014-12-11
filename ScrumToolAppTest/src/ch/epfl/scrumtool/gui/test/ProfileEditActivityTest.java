package ch.epfl.scrumtool.gui.test;

import org.mockito.Mockito;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.network.Session;
import android.view.Menu;
import android.widget.Spinner;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.scrumtool.gui.ProfileEditActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;

/**
 * @author LeoWirz, AlexVeuthey
 *
 */
public class ProfileEditActivityTest extends ActivityInstrumentationTestCase2<ProfileEditActivity> {

    private User user = MockData.VINCENT;
    private String firstName = "Leonardo";
    private String lastName = "Wirz";
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    
    private Activity activity;

    public ProfileEditActivityTest() {
        super(ProfileEditActivity.class);
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
    
    public void testNameFieldsAreEditable() {
        ViewInteraction fName = onView(withId(R.id.profile_edit_firstname));
        fName.perform(clearText());
        fName.perform(typeText(firstName));
        fName.check(matches(withText(firstName)));
        
        ViewInteraction lName = onView(withId(R.id.profile_edit_lastname));
        lName.perform(clearText());
        lName.perform(typeText(lastName));
        lName.check(matches(withText(lastName)));
    }
    
    @SuppressWarnings("unchecked")
    public void testChangeGender() {
        ViewInteraction gender = onView(withId(R.id.profile_edit_gender));
        gender.perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Female"))).perform(click());
        Spinner genderSpinner = (Spinner) activity.findViewById(R.id.profile_edit_gender);
        assertEquals(genderSpinner.getSelectedItem(), "Female");
    }
    
    public void testCompanyField() {
        ViewInteraction company = onView(withId(R.id.profile_edit_company));
        company.perform(clearText());
        company.perform(typeText("myCompany"));
        company.check(matches(withText("myCompany")));
    }
    
    public void testJobField() {
        ViewInteraction jobTitle = onView(withId(R.id.profile_edit_jobtitle));
        jobTitle.perform(clearText());
        jobTitle.perform(typeText("myJob"));
        jobTitle.check(matches(withText("myJob")));
    }
    
    public void testChangeDateOfBirth() {
        onView(withId(R.id.profile_edit_dateofbirth)).perform(click());
        onView(withText("OK")).perform(click());
    }
    
    public void testSaveButtonIsClickable() {
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
    }
}
