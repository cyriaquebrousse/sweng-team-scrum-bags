package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.epfl.scrumtool.gui.utils.CustomViewActions;
import ch.epfl.scrumtool.gui.utils.CustomMatchers;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import com.google.android.apps.common.testing.ui.espresso.Espresso;


import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.utils.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

import android.content.Intent;

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
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Player>>) invocation.getArguments()[1]).interactionDone(playerList);
                return null;
            }
        };
        
        Mockito.doAnswer(loadPlayersAnswer).when(mockClient).loadPlayers(Mockito.any(Project.class),
                Mockito.any(Callback.class));
        setActivityIntent(openPlayerListIntent);
        getActivity();
    }
    
    @SuppressWarnings("unchecked")
    public void testPlayerIsDisplayed() {
        Espresso.onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .check(matches(isDisplayed()));
    }
    
    public void testClickOnCrossAddsAPlayer() {
        Espresso.onView(withId(Menu.FIRST))
            .perform(click());
        Espresso.onView(withText("Enter the new user's email address : "))
            .check(matches(ViewMatchers.isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testSwipeDownUpdatesPlayerList() {
        playerList.add(PLAYER2);
        onView(withId(R.id.swipe_update_project_playerlist)).perform(CustomViewActions.swipeDown());
        Espresso.onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(1)
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testClickOnPlayerOpenUserOverview() {
        Espresso.onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(click());
        Espresso.onView(withId(R.id.profile_email))
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testLongClickOnPlayerOpensContextMenu() {
        Espresso.onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        Espresso.onView(withText(R.string.action_edit))
            .check(matches(isDisplayed()));
        Espresso.onView(withText(R.string.action_delete))
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testChangePlayersRole() {
        Espresso.onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        Espresso.onView(withText(R.string.action_edit))
            .perform(click());
        Espresso.onView(withText("Set role"))
            .check(matches(isDisplayed()));
    }
    
    @SuppressWarnings("unchecked")
    public void testRemovePlayer() {
        Espresso.onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .perform(longClick());
        Espresso.onView(withText(R.string.action_edit)).perform(click());
        
        Answer<Void> removePlayersAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<Boolean>) invocation.getArguments()[1]).failure("haha");
                return null;
            }
        };
        
        Mockito.doAnswer(removePlayersAnswer).when(mockClient).removePlayer(Mockito.any(Player.class),
                Mockito.any(Callback.class));
        Espresso.onData(instanceOf(Player.class)).inAdapterView(allOf(withId(R.id.project_playerlist))).atPosition(0)
            .check(matches(isDisplayed()));
        
        Answer<Void> removePlayersAnswer2 = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<Boolean>) invocation.getArguments()[1]).interactionDone(true);
                return null;
            }
        };
        
        Mockito.doAnswer(removePlayersAnswer2).when(mockClient).removePlayer(Mockito.any(Player.class),
                Mockito.any(Callback.class));
        //TODO check that the list is empty
        onView(withId(R.id.project_playerlist))
            .check(matches(not(CustomMatchers.withAdaptedData(is(Player.class)))));

    }

}
