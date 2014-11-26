package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class SprintEditActivityTest extends ActivityInstrumentationTestCase2<SprintEditActivity> {

    public SprintEditActivityTest() {
        super(SprintEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
