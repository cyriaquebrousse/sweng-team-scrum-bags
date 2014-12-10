package ch.epfl.scrumtool.gui;

import java.util.List;

import javax.annotation.meta.When;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.doAnswer;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.gui.utils.CustomViewActions;
import ch.epfl.scrumtool.gui.utils.CustomMatchers;
import ch.epfl.scrumtool.gui.utils.MockData;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import android.content.Intent;
import android.view.Menu;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
/**
 * @author vincent
 *
 */
public class PlayerAddActivityTest extends BaseInstrumentationTestCase<PlayerAddActivity> {
    
    DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    private final static Project PROJECT = MockData.MURCS;
    private final static Player PLAYER = MockData.VINCENT_ADMIN;

    public PlayerAddActivityTest() {
        super(PlayerAddActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent openPlayerAddIntent = new Intent();
        openPlayerAddIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        setActivityIntent(openPlayerAddIntent);
        getActivity();
    }
    
    public void testViewsAreDisplayed() {
        onView(withId(R.id.player_address_add))
            .check(matches(isDisplayed()));
        onView(withId(R.id.player_address_add))
            .check(matches(withText(R.string.player_name_add_hint)));
        onView(withId(R.id.player_role_sticker))
            .check(matches(isDisplayed()));
        onView(withId(R.id.player_role_sticker))
            .check(matches(withText(Role.STAKEHOLDER.name())));
        onView(withId(R.id.player_select_contact_button))
            .check(matches(isDisplayed()));
        onView(withId(R.id.player_select_contact_button))
            .check(matches(withText(R.string.phone_contact_button)));
    }
    
    public void testChangeRole() {
        onView(withId(R.id.player_role_sticker))
            .perform(click());
        onView(withText(Role.DEVELOPER.name()))
            .perform(click());
        onView(withId(R.id.player_role_sticker))
            .check(matches(withText(Role.DEVELOPER.name())));
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
                ((Callback<Player>) invocation.getArguments()[2]).interactionDone(PLAYER);
                return null;
            }
            
        };
        doAnswer(addPlayerAnswer).when(mockClient).addPlayerToProject(Mockito.any(Project.class),
                Mockito.anyString(), Mockito.any(Role.class), Matchers.<Callback<Player>>any());
        onView(withId(R.layout.activity_project_player_list))
            .check(matches(isDisplayed()));
    }
}
