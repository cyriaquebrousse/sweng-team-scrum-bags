package ch.epfl.scrumtool.gui.test;

import org.junit.Before;
import org.mockito.Mockito;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.gui.LoginActivity;
import ch.epfl.scrumtool.settings.ApplicationSettings;
import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.mockito.Mockito;

import android.app.Activity;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.settings.ApplicationSettings;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginActivityTest() {
        super(LoginActivity.class);
    }
    
    ApplicationSettings SETTINGS = Mockito.mock(ApplicationSettings.class);
    
    @SuppressWarnings("static-access")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        when(SETTINGS.getCachedUser(Mockito.any(Activity.class))).thenReturn(null);
        getActivity();
    }
    
    @Before
    public void logout() {
        onView(withId(R.id.action_overflow)).perform(click());
        onView(withText("Logout")).perform(click());
    }
    
    public void testDisplayedButton() {
        onView(withId(R.id.button_login)).check(matches(isDisplayed()));
    }
    
    public void testLoginButton() {
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(android.R.id.button2));
        onView(withText("Annuler")).perform(click());
    }

}
