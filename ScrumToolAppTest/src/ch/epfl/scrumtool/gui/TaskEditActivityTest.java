package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class TaskEditActivityTest extends ActivityInstrumentationTestCase2<TaskEditActivity> {

    public TaskEditActivityTest() {
        super(TaskEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
