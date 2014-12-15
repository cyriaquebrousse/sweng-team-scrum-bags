package ch.epfl.scrumtool.gui.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.ProjectPlayerListActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;
import ch.epfl.scrumtool.network.Session;

import com.google.android.apps.common.testing.ui.espresso.DataInteraction;

/**
 * 
 * @author
 *
 */
public class ProjectPlayerListActivityTest extends ActivityInstrumentationTestCase2<ProjectPlayerListActivity> {

    private static final Project PROJECT = MockData.PROJECT;
    private static final Player PLAYER1 = MockData.USER1_ADMIN;
    private static final User USER = MockData.USER1;
    //private static final Player PLAYER2 = MockData.JOEY_DEV;
    
    private static final List<Player> PLAYERLIST = new ArrayList<Player>();
    
    private static final DatabaseScrumClient MOCKCLIENT = Mockito.mock(DatabaseScrumClient.class);

    public ProjectPlayerListActivityTest() {
        super(ProjectPlayerListActivity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(MOCKCLIENT);
        new Session(USER) {
            //This is used to trick authentication for tests
        };
        
        Intent openPlayerListIntent = new Intent();
        openPlayerListIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        
        PLAYERLIST.add(PLAYER1);

        Answer<Void> loadPlayersAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                ((Callback<List<Player>>) invocation.getArguments()[1]).interactionDone(PLAYERLIST);
                return null;
            }
        };
        
        doAnswer(loadPlayersAnswer).when(MOCKCLIENT).loadPlayers(Mockito.any(Project.class),
                Matchers.<Callback<List<Player>>>any());
        setActivityIntent(openPlayerListIntent);
        getActivity();
    }
    
    @SuppressWarnings("unchecked")
    public void testPlayerIsDisplayed() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .check(matches(isDisplayed()));
        DataInteraction listInteraction = onData(instanceOf(Player.class))
            .inAdapterView(allOf(withId(R.id.project_playerlist)));
        listInteraction.atPosition(0).onChildView(withId(R.id.player_row_name))
            .check(matches(withText(PLAYER1.getUser().fullname())));
    }
    
    public void testClickOnCrossOpenPlayerAddActivity() {
        onView(withId(Menu.FIRST))
            .perform(click());
    }
    
    @SuppressWarnings("unchecked")
    public void testClickOnPlayerOpenUserOverview() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(click());
        onView(withId(R.id.profile_email))
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testLongClickOnPlayerOpensContextMenu() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText(R.string.action_player_edit_role))
            .check(matches(isDisplayed()));
        onView(withText(R.string.action_delete))
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testChangePlayersRole() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText("Set Role"))
            .perform(click());
        onView(withText("Set role"))
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testSetAsAdminCancel() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText("Set Admin"))
            .perform(click());
        onView(withId(android.R.id.button2))
            .perform(click());
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testRemovePlayerCancel() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText(R.string.action_delete)).perform(click());
        //cancel
        onView(withId(android.R.id.button2)).perform(click());
        //check player is still here
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testRemovePlayerOK() {
        Answer<Void> removePlayersAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                PLAYERLIST.remove(0);
                ((Callback<Void>) invocation.getArguments()[1]).interactionDone(null);
                return null;
            }
        };
        
        doAnswer(removePlayersAnswer).when(MOCKCLIENT).removePlayer(Mockito.any(Player.class),
                Matchers.<Callback<Void>>any());
        
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText(R.string.action_delete)).perform(click());
        //delete
        onView(withId(android.R.id.button1)).perform(click());
    }

}
