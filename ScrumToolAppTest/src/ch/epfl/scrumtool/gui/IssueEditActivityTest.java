package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class IssueEditActivityTest extends ActivityInstrumentationTestCase2<IssueEditActivity> {

    public IssueEditActivityTest() {
        super(IssueEditActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

}
