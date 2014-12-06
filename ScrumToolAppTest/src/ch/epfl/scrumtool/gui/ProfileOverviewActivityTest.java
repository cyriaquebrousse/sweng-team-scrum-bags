package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class ProfileOverviewActivityTest extends ActivityInstrumentationTestCase2<MyProfileOverviewActivity> {

    public ProfileOverviewActivityTest() {
        super(MyProfileOverviewActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
