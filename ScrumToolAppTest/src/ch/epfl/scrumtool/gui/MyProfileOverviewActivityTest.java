package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class MyProfileOverviewActivityTest extends ActivityInstrumentationTestCase2<MyProfileOverviewActivity> {

    public MyProfileOverviewActivityTest() {
        super(MyProfileOverviewActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
