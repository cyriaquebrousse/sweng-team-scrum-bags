package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class SprintListActivityTest extends ActivityInstrumentationTestCase2<SprintListActivity> {

    public SprintListActivityTest() {
        super(SprintListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
