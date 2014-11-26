package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class IssueOverviewActivityTest extends ActivityInstrumentationTestCase2<IssueOverviewActivity> {

    public IssueOverviewActivityTest() {
        super(IssueOverviewActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

}
