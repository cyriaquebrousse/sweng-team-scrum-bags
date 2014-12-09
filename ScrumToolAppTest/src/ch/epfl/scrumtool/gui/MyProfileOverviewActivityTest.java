package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.utils.MockData;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import ch.epfl.scrumtool.R;

public class MyProfileOverviewActivityTest extends BaseInstrumentationTestCase<MyProfileOverviewActivity> {

    public MyProfileOverviewActivityTest() {
        super(MyProfileOverviewActivity.class);
    }
    
    private final static User USER = MockData.VINCENT;
    
    @SuppressWarnings("static-access")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra(USER.SERIALIZABLE_NAME, USER);
        setActivityIntent(intent);
        getActivity();
    }
    
    public void testIsEditable() {
        onView(withId(Menu.FIRST)).perform(click());
    }
    
    public void testUserCorrectlyDisplayed() {
        onView(withId(R.id.profile_email)).check(matches(withText(USER.getEmail())));
        onView(withId(R.id.profile_company)).check(matches(withText(USER.getCompanyName())));
        onView(withId(R.id.profile_gender)).check(matches(withText(USER.getGender().toString().toLowerCase())));
        onView(withId(R.id.profile_jobtitle)).check(matches(withText(USER.getJobTitle())));
        onView(withId(R.id.profile_name)).check(matches(withText(USER.fullname())));
    }
    
    public void testEditCompany() {
        onView(withId(R.id.profile_company)).perform(click());
        onView(withId(R.id.popup_user_input)).perform(typeText("hello"));
        onView(withId(android.R.id.button1)).perform(click());
    }
    
    public void testEditName() {
        onView(withId(R.id.profile_name)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }
    
    public void testEditJob() {
        onView(withId(R.id.profile_jobtitle)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }
    
    public void testEditDate() {
        onView(withId(R.id.profile_date_of_birth)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }
    
}
