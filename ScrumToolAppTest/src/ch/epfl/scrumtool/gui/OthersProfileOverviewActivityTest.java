package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class OthersProfileOverviewActivityTest extends ActivityInstrumentationTestCase2<OthersProfileOverviewActivity> {

    public OthersProfileOverviewActivityTest() {
        super(OthersProfileOverviewActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
