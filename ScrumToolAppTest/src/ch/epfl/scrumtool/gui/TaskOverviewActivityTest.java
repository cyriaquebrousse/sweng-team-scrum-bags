package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class TaskOverviewActivityTest extends ActivityInstrumentationTestCase2<TaskOverviewActivity> {

    public TaskOverviewActivityTest() {
        super(TaskOverviewActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
