package ch.epfl.scrumtool.gui.test;

import org.junit.Before;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.gui.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @author LeoWirz
 * 
 */
public class LoginActivityTest extends
        ActivityInstrumentationTestCase2<LoginActivity> {

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
        fail("fix this testCase");
        onView(withId(R.id.action_overflow)).perform(click());
        onView(withText("Logout")).perform(click());
    }

    public void testDisplayedButton() {
        fail("fix this testCase");
        onView(withId(R.id.button_login)).check(matches(isDisplayed()));
    }
    
    public void testLoginButtonAndCancel() {
        fail("fix this testCase");
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.button_login)).perform(pressBack());
    }

    public void testLoginButton() {
        fail("fix this testCase");
        onView(withId(R.id.button_login)).perform(click(), pressBack());
        onView(withText("Annuler")).perform(click());
        onView(withText("Annuler"))
                .inRoot(withDecorView(not(is(getActivity().getWindow()
                        .getDecorView())))).perform(click());
    }

}
