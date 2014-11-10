package ch.epfl.scrumtool.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListAdapter;
import android.widget.ListView;
import ch.epfl.scrumtool.gui.BacklogActivity;
import ch.epfl.scrumtool.gui.TaskOverviewActivity;

/**
 * @author LeoWirz
 * 
 */
public class BacklogActivityTest extends
        ActivityInstrumentationTestCase2<BacklogActivity> {

    private BacklogActivity mActivity;
    private ListView mBacklogView;
    private ListAdapter mPlanetData;

    public BacklogActivityTest() {
        super(BacklogActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

        mActivity = getActivity();

        mBacklogView = (ListView) mActivity
                .findViewById(ch.epfl.scrumtool.R.id.backlog_tasklist);

        mPlanetData = mBacklogView.getAdapter();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPrecondition() {
        assertTrue(mBacklogView.isClickable());
        assertTrue(mPlanetData != null);
        assertTrue(mPlanetData.getCount() >= 0);
    }

    public void testBacklogUI() {
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(
                TaskOverviewActivity.class.getName(), null, false);

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                mBacklogView.getChildAt(0).performClick();
            }
        });

        TaskOverviewActivity nextActivity = (TaskOverviewActivity) getInstrumentation()
                .waitForMonitorWithTimeout(activityMonitor, 5);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

}
