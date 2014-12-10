package ch.epfl.scrumtool.gui;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import org.mockito.Mockito;

import android.content.Intent;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.utils.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

/**
 * @author LeoWirz
 * 
 */
public class OthersProfileOverviewActivityTest extends
        BaseInstrumentationTestCase<OthersProfileOverviewActivity> {

    public OthersProfileOverviewActivityTest() {
        super(OthersProfileOverviewActivity.class);
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
}
