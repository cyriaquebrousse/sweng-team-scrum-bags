package ch.epfl.scrumtool.gui;

import org.mockito.Mock;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import org.mockito.Mockito;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;
import ch.epfl.scrumtool.gui.utils.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
/**
 * @author LeoWirz
 *
 */
public class ProfileEditActivityTest extends BaseInstrumentationTestCase<ProfileEditActivity> {

    public ProfileEditActivityTest() {
        super(ProfileEditActivity.class);
    }

    User user = MockData.VINCENT;
    
    Session mocksession = mock(Session.class);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mocksession.setUser(user);
        when(mocksession.getUser()).thenReturn(user);
//        when(mocksession.getCurrentSession()).thenReturn(mocksession);
//        when(mocksession.getCurrentSession().getUser()).thenReturn(user);
        
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
        onView(withId(Menu.FIRST)).check(matches(isClickable()));
    }

}
