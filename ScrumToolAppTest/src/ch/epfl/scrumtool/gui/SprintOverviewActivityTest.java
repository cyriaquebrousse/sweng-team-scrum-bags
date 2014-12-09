package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class SprintOverviewActivityTest extends BaseInstrumentationTestCase<SprintOverviewActivity> {

    public SprintOverviewActivityTest() {
        super(SprintOverviewActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
