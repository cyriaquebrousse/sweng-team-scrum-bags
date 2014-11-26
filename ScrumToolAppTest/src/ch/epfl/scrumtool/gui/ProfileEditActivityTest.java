package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;

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
    
    public void testFirstnameLength() {
        onView(withId(R.id.profile_edit_firstname)).perform(typeText("Leonardo"));
    }
    
    public void testLastnameLength() {
        onView(withId(R.id.profile_edit_lastname)).perform(typeText("Wirz"));
    }
    
    public void testGenderLength() {
        onView(withId(R.id.profile_edit_gender)).perform(typeText("M"));
    }

}
