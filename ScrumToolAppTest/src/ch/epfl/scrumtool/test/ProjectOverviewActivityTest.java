/**
 * 
 */
package ch.epfl.scrumtool.test;

import ch.epfl.scrumtool.gui.BacklogActivity;
import ch.epfl.scrumtool.gui.ProjectOverviewActivity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;


/**
 * @author LeoWirz
 * 
 */
public class ProjectOverviewActivityTest extends
        ActivityInstrumentationTestCase2<ProjectOverviewActivity> {

    private ProjectOverviewActivity mActivity;
    private Button mBacklogButton;
    private Button mPlayersButton;
    private Button mSprintsButton;

    public ProjectOverviewActivityTest() {
        super(ProjectOverviewActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

        mActivity = getActivity();

        mBacklogButton = (Button) mActivity
                .findViewById(ch.epfl.scrumtool.R.id.project_button_backlog);
        mPlayersButton = (Button) mActivity
                .findViewById(ch.epfl.scrumtool.R.id.project_button_players);
        mSprintsButton = (Button) mActivity
                .findViewById(ch.epfl.scrumtool.R.id.project_button_sprints);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPrecondition() {
        assertTrue(mBacklogButton.isClickable());
        assertTrue(mPlayersButton.isClickable());
        assertTrue(mSprintsButton.isClickable());
    }
    
    public void testBacklogUI() {
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(
                BacklogActivity.class.getName(), null, false);

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                mBacklogButton.performClick();
            }
        });

        BacklogActivity nextActivity = (BacklogActivity) getInstrumentation()
                .waitForMonitorWithTimeout(activityMonitor, 5);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

}
