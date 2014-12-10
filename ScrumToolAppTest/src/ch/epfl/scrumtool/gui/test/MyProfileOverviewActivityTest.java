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

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.MyProfileOverviewActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

/**
 * @author LeoWirz
 * 
 */
public class MyProfileOverviewActivityTest extends ActivityInstrumentationTestCase2<MyProfileOverviewActivity> {

    public MyProfileOverviewActivityTest() {
        super(MyProfileOverviewActivity.class);
    }

    private final static User USER = MockData.VINCENT;

    private DatabaseScrumClient mockClient = Mockito
            .mock(DatabaseScrumClient.class);

    @SuppressWarnings("static-access")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(mockClient);
        Intent intent = new Intent();
        intent.putExtra(USER.SERIALIZABLE_NAME, USER);
        setActivityIntent(intent);
        getActivity();
    }

    public void testIsEditable() {
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
    }
    
    public void testIsRemovable() {
        onView(withId(Menu.FIRST + 1)).check(matches(isClickable()));
    }

    public void testUserCorrectlyDisplayed() {
        onView(withId(R.id.profile_email)).check(
                matches(withText(USER.getEmail())));
        onView(withId(R.id.profile_company)).check(
                matches(withText(USER.getCompanyName())));
        onView(withId(R.id.profile_gender)).check(
                matches(withText(USER.getGender().toString().toLowerCase())));
        onView(withId(R.id.profile_jobtitle)).check(
                matches(withText(USER.getJobTitle())));
        onView(withId(R.id.profile_name)).check(
                matches(withText(USER.fullname())));
    }

    public void testEditCompany() {
        onView(withId(R.id.profile_company)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());
        onView(withId(R.id.popup_user_input)).perform(typeText("text"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.profile_company)).check(
                matches(withText("text")));
    }

    public void testEditName() {
        onView(withId(R.id.profile_name)).perform(click());
        onView(withId(R.id.popup_user_input_first_name)).perform(clearText());
        onView(withId(R.id.popup_user_input_first_name)).perform(typeText("text"));
        onView(withId(R.id.popup_user_input_last_name)).perform(clearText());
        onView(withId(R.id.popup_user_input_last_name)).perform(typeText("text"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.profile_name)).check(
                matches(withText("text text")));
    }

    public void testEditJob() {
        onView(withId(R.id.profile_jobtitle)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(clearText());
        onView(withId(R.id.popup_user_input)).perform(typeText("text"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.profile_jobtitle)).check(
                matches(withText("text")));
    }

    public void testEditDate() {
        onView(withId(R.id.profile_date_of_birth)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }

}
