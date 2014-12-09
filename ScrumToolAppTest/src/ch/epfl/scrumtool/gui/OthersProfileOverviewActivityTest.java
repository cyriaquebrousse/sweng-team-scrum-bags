package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class OthersProfileOverviewActivityTest extends BaseInstrumentationTestCase<OthersProfileOverviewActivity> {

    public OthersProfileOverviewActivityTest() {
        super(OthersProfileOverviewActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
