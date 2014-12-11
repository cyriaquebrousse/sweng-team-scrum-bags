package ch.epfl.scrumtool.gui.test;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.DashboardActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Session;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

/**
 * @author LeoWirz
 * 
 */
public class WalkThroughtTest extends
        ActivityInstrumentationTestCase2<DashboardActivity> {

    public WalkThroughtTest() {
        super(DashboardActivity.class);
    }

    private User user = MockData.VINCENT;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        new Session(user){
            //This is used to trick authentication for tests
        };
        getActivity();
    }

    @SuppressWarnings("unchecked")
    public void testAll() throws InterruptedException {
        addProject();

        // click on first project to open the card
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .perform(click());
        // click on backlog icon
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .onChildView(withId(R.id.project_row_players)).perform(click());

        addPlayer();

        onView(withId(R.id.project_playerlist)).perform(pressBack());

        // click on first project to open the card
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .perform(click());
        // click on sprint icon
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .onChildView(withId(R.id.project_row_sprints)).perform(click());

        addSprint("1");
        addSprint("2");

        onView(withId(R.id.sprint_list)).perform(pressBack());

        // click on first project to open the card
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .perform(click());
        // click on backlog icon
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .onChildView(withId(R.id.project_row_backlog)).perform(click());

        addTask("1");
        addTask("2");
        removeTask();

        // click on first item of the backlog
        onData(instanceOf(MainTask.class))
                .inAdapterView(allOf(withId(R.id.backlog_tasklist)))
                .atPosition(0).perform(click());

        addIssue("1", "5");
        addIssue("2", "4");
        removeIssue();

        onView(withId(R.id.issue_list)).perform(pressBack());
        onView(withId(R.id.backlog_tasklist)).perform(pressBack());

        removeProject();

    }

    /**
     * 
     */
    private void addSprint(String number) {
        onView(withId(Menu.FIRST)).perform(click());
        onView(withId(R.id.sprint_name_edit)).perform(
                typeText("Sprint" + number));
        onView(withId(R.id.sprint_date_edit)).perform(click());
        // onView(withText("26")).perform(click());
        onView(withText("Appliquer")).perform(click());
        onView(withId(Menu.FIRST)).perform(click());
    }

    /**
     * 
     */
    private void addPlayer() {
        // click on "+" button
        onView(withId(Menu.FIRST)).perform(click());
        // write an e-mail
        onView(withId(R.id.player_address_add)).perform(
                typeText("testee@test.ch"));
        // chose a role
        onView(withId(R.id.player_role_sticker)).perform(click());
        onView(withText("Developer")).perform(click());
        onView(withId(Menu.FIRST)).perform(click());
    }

    @SuppressWarnings("unchecked")
    private void addIssue(String number, String hours)
            throws InterruptedException {
        // click on "+" button
        onView(withId(Menu.FIRST)).perform(click());
        // edit fields
        onView(withId(R.id.issue_name_edit)).perform(
                typeText(number + "Issue test"));
        onView(withId(R.id.issue_description_edit)).perform(
                typeText("auto-remove"));
        onView(withId(R.id.issue_estimation_edit)).perform(
                ViewActions.clearText());
        onView(withId(R.id.issue_estimation_edit)).perform(typeText(hours));
        onView(withId(R.id.issue_assignee_spinner)).perform(click());
        onData(allOf(is(instanceOf(Player.class)))).atPosition(0).perform(
                click());
        onView(withId(R.id.issue_sprint_spinner)).perform(click());
        onData(allOf(is(instanceOf(Sprint.class)))).atPosition(0).perform(
                click());
        onView(withId(Menu.FIRST)).perform(click());
    }

    private void addTask(String number) throws InterruptedException {
        // click on "+" button
        onView(withId(Menu.FIRST)).perform(click());
        // fill the different fields
        onView(withId(R.id.task_name_edit)).perform(
                typeText(number + "task test"));
        onView(withId(R.id.task_description_edit)).perform(
                typeText("auto-remove"));

        // click on save button
        onView(withId(Menu.FIRST)).perform(click());
    }

    private void addProject() throws InterruptedException {
        // click on projects button
        onView(withId(R.id.dashboard_button_project_list)).perform(click());
        // click on the "+" button
        onView(withId(Menu.FIRST)).perform(click());
        // edit fields
        onView(withId(R.id.project_title_edit)).perform(
                typeText("1project test"));
        onView(withId(R.id.project_description_edit)).perform(
                typeText("auto-remove"));

        // click on save button
        onView(withId(Menu.FIRST)).perform(click());
    }

    @SuppressWarnings("unchecked")
    public void removeProject() {
        // long click on first project and click on "delete" menu
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .perform(click());
        onData(instanceOf(Project.class))
                .inAdapterView(allOf(withId(R.id.project_list))).atPosition(0)
                .perform(longClick());
        onView(withText("Delete")).perform(click());
        onView(withText("OK")).perform(click());
    }

    @SuppressWarnings("unchecked")
    public void removeTask() {
        onData(instanceOf(MainTask.class))
                .inAdapterView(allOf(withId(R.id.backlog_tasklist)))
                .atPosition(0).perform(longClick());
        onView(withText("Delete")).perform(click());
        onView(withText("OK")).perform(click());
    }

    @SuppressWarnings("unchecked")
    public void removeIssue() {
        onData(instanceOf(Issue.class))
                .inAdapterView(allOf(withId(R.id.issue_list))).atPosition(0)
                .perform(longClick());
        onView(withText("Delete")).perform(click());
        onView(withText("OK")).perform(click());
    }

}
