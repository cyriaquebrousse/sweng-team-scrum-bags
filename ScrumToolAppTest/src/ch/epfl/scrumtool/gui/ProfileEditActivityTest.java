package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
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
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.utils.MockData;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;
/**
 * @author LeoWirz, AlexVeuthey
 *
 */
public class ProfileEditActivityTest extends ActivityInstrumentationTestCase2<ProfileEditActivity> {

    public ProfileEditActivityTest() {
        super(ProfileEditActivity.class);
    }

    private User user = MockData.VINCENT;
    private String firstName = "Leonardo";
    private String lastName = "Wirz";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        getActivity();
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
    public void testGenderField() {
        onView(withId(R.id.profile_edit_gender)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Male"))).perform(click());
    }
    
    public void testCompanyField() {
        onView(withId(R.id.profile_edit_company)).perform(typeText("myCompany"));
    }
    
    public void testJobField() {
        onView(withId(R.id.profile_edit_jobtitle)).perform(typeText("myJob"));
    }
    
    public void testButton() {
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
    }

}
