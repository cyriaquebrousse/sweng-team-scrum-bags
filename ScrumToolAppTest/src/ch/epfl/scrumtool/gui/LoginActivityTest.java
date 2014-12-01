package ch.epfl.scrumtool.gui;

import org.junit.Before;

import ch.epfl.scrumtool.R;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginActivityTest() {
        super(LoginActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
    
    @Before
    public void logout() {
        onView(withId(R.id.action_overflow)).perform(click());
        onView(withText("Logout")).perform(click());
    }
    
    public void testLoginButton() {
        logout();
        onView(withId(R.id.button_login)).perform(click());
        onView(withText("wirz.leonardo@gmail.com")).perform(click());
        onView(withText("OK")).perform(click());
    }

}
