package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.utils.CustomViewActions;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;

public class ProjectPlayerListActivityTest extends ActivityInstrumentationTestCase2<ProjectPlayerListActivity> {

    private static final String PROJECT_KEY = "Key du projet 1";
    private static final String PROJECT_NAME = "Projet 1";
    private static final String PROJECT_DESCRIPTION = "Description du projet 1";

    public ProjectPlayerListActivityTest() {
        super(ProjectPlayerListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Project.Builder project = new Project.Builder();
        project.setKey(PROJECT_KEY)
            .setName(PROJECT_NAME)
            .setDescription(PROJECT_DESCRIPTION);
        Intent openPlayerListIntent = new Intent();
        openPlayerListIntent.putExtra(Project.SERIALIZABLE_NAME, project.build());
        setActivityIntent(openPlayerListIntent);
        getActivity();
    }
    
    protected void testClickOnCrossAddsAPlayer() {
        Espresso.onView(ViewMatchers.withId(Menu.FIRST)).perform(ViewActions.click());
        Espresso.pressBack();

    }
    
    protected void testSwipeDownUpdatesPlayerList() {
        Espresso.onView(ViewMatchers.withId(R.id.project_playerlist)).perform(CustomViewActions.swipeDown());

    }
    
    public void testClickOnPlayerOpenUserOverview() {
        Espresso.onView(ViewMatchers.withId(R.id.project_playerlist)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.profile_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.pressBack();
    }
    
    public void testLongClickOnPlayerOpensContextMenu() {
        Espresso.onView(ViewMatchers.withId(R.id.project_playerlist)).perform(ViewActions.longClick());

    }
    
    public void testChangePlayersRole() {
        Espresso.onView(ViewMatchers.withId(R.id.project_playerlist)).perform(ViewActions.longClick());
        Espresso.onView(ViewMatchers.withId(R.id.action_entity_edit)).perform(ViewActions.click());
    }
    
    public void testRemovePlayer() {
        Espresso.onView(ViewMatchers.withId(R.id.project_playerlist)).perform(ViewActions.longClick());
        Espresso.onView(ViewMatchers.withId(R.id.action_entity_delete)).perform(ViewActions.click());
    }

}
