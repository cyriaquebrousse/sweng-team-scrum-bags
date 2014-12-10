package ch.epfl.scrumtool.gui.test;

import static ch.epfl.scrumtool.gui.utils.test.CustomMatchers.withPlayer;
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
import ch.epfl.scrumtool.gui.ProjectPlayerListActivity;
import ch.epfl.scrumtool.gui.utils.test.CustomViewActions;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import com.google.android.apps.common.testing.ui.espresso.DataInteraction;

public class ProjectPlayerListActivityTest extends ActivityInstrumentationTestCase2<ProjectPlayerListActivity> {

    private static final Project PROJECT = MockData.MURCS;
    private static final Player PLAYER1 = MockData.VINCENT_ADMIN;
    private static final Player PLAYER2 = MockData.JOEY_DEV;
    
    List<Player> playerList = new ArrayList<Player>();
    
    DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);

    public ProjectPlayerListActivityTest() {
        super(ProjectPlayerListActivity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Client.setScrumClient(mockClient);
        
        Intent openPlayerListIntent = new Intent();
        openPlayerListIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        
        playerList.add(PLAYER1);

        Answer<Void> loadPlayersAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                ((Callback<List<Player>>) invocation.getArguments()[1]).interactionDone(playerList);
                return null;
            }
        };
        
        doAnswer(loadPlayersAnswer).when(mockClient).loadPlayers(Mockito.any(Project.class),
                Matchers.<Callback<List<Player>>>any());
        setActivityIntent(openPlayerListIntent);
        getActivity();
    }
    
    @SuppressWarnings("unchecked")
    public void testPlayerIsDisplayed() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .check(matches(isDisplayed()));
        //TODO find out why this doesn't work
        DataInteraction listInteraction = onData(instanceOf(Player.class))
            .inAdapterView(allOf(withId(R.id.project_playerlist)));
        
        listInteraction.atPosition(0)
            .check(matches(withPlayer(PLAYER1)));
    }
    
    public void testClickOnCrossOpenPlayerAddActivity() {
        onView(withId(Menu.FIRST))
            .perform(click());
    }
    
    @SuppressWarnings("unchecked")
    public void testSwipeDownUpdatesPlayerList() {
        playerList.add(PLAYER2);
        onView(withId(R.id.swipe_update_project_playerlist)).perform(CustomViewActions.swipeDown());
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(1)
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testClickOnPlayerOpenUserOverview() {
        fail("Not implemented yet");
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(click());
    }
    
    @SuppressWarnings("unchecked")
    public void testLongClickOnPlayerOpensContextMenu() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText(R.string.action_edit))
            .check(matches(isDisplayed()));
        onView(withText(R.string.action_delete))
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testChangePlayersRole() {
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText(R.string.action_edit))
            .perform(click());
        onView(withText("Set role"))
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
                playerList.remove(0);
                ((Callback<Void>) invocation.getArguments()[1]).interactionDone(null);
                return null;
            }
        };
        
        doAnswer(removePlayersAnswer).when(mockClient).removePlayer(Mockito.any(Player.class),
                Matchers.<Callback<Void>>any());
        
        onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        onView(withText(R.string.action_delete)).perform(click());
        //delete
        onView(withId(android.R.id.button1)).perform(click());
        //TODO check if list is empty
        //onView(withId(R.id.swipe_update_project_playerlist)).check(matches(not(isDisplayed())));

    }

}
