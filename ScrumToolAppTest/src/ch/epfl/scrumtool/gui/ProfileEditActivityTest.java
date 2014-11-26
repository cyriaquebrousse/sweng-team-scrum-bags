package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;

/**
 * @author LeoWirz
 *
 */
public class ProfileEditActivityTest extends ActivityInstrumentationTestCase2<ProfileEditActivity> {

    public ProfileEditActivityTest() {
        super(ProfileEditActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
    
    public void testFirstnameField() {
        onView(withId(R.id.profile_edit_firstname)).perform(typeText("Leonardo"));
    }
    
    public void testLastnameField() {
        onView(withId(R.id.profile_edit_lastname)).perform(typeText("Wirz"));
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
        onView(withId(R.id.profile_edit_submit_button)).check(matches(isClickable()));
    }

}
