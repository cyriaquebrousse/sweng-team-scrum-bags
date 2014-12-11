package ch.epfl.scrumtool.gui.test;


import static ch.epfl.scrumtool.gui.utils.test.CustomMatchers.withHint;
import static ch.epfl.scrumtool.gui.utils.test.CustomMatchers.withRole;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doAnswer;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.gui.PlayerAddActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
/**
 * @author vincent
 *
 */
public class PlayerAddActivityTest extends ActivityInstrumentationTestCase2<PlayerAddActivity> {
    
    DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    private final static Project PROJECT = MockData.MURCS;
    private final static Player PLAYER = MockData.VINCENT_ADMIN;
    

    public PlayerAddActivityTest() {
        super(PlayerAddActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Client.setScrumClient(mockClient);
        Intent openPlayerAddIntent = new Intent();
        openPlayerAddIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        setActivityIntent(openPlayerAddIntent);
        getActivity();
    }
    
    public void testViewsAreDisplayed() {
        Resources res = getInstrumentation().getTargetContext().getResources();

        onView(withId(R.id.player_address_add))
            .check(matches(isDisplayed()));
        onView(withId(R.id.player_address_add))
            .check(matches(withHint(res.getString(R.string.player_name_add_hint))));
        onView(withId(R.id.player_role_sticker))
            .check(matches(isDisplayed()));
        onView(withId(R.id.player_role_sticker))
            .check(matches(withRole(Role.STAKEHOLDER)));
        onView(withId(R.id.player_select_contact_button))
            .check(matches(isDisplayed()));
        onView(withId(R.id.player_select_contact_button))
            .check(matches(withText(R.string.phone_contact_button)));
    }
    
    @SuppressWarnings("unchecked")
    public void testChangeRole() {
        onView(withId(R.id.player_role_sticker))
            .perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3)
            .perform(click());
        onView(withId(R.id.player_role_sticker))
            .check(matches(withRole(Role.DEVELOPER)));
    }
    
    public void testBrowseContactInsertAddress() {
        onView(withId(R.id.player_select_contact_button))
            .perform(click());
        fail("Not Yet Implemented");
    }
    
    public void testAutoComplete() {
        //check dropDown not displayed
        
        // write one letter
        onView(withId(R.id.player_address_add))
            .perform(typeText("s"));
        
        //check DropDown displayed
        
        fail("Not Yet Implemented");

    }
    
    public void testClickInvite() {
        Answer<Void> addPlayerAnswer = new Answer<Void>() {

            @SuppressWarnings("unchecked")
            @Override
            public Void answer(InvocationOnMock invocation) {
                ((Callback<Player>) invocation.getArguments()[3]).interactionDone(PLAYER);
                return null;
            }
            
        };
        doAnswer(addPlayerAnswer).when(mockClient).addPlayerToProject(Mockito.any(Project.class),
                Mockito.anyString(), Mockito.any(Role.class), Matchers.<Callback<Player>>any());
        onView(withId(Menu.FIRST))
            .perform(click());
    }
}
