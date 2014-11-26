package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class ProjectPlayerListActivityTest extends ActivityInstrumentationTestCase2<ProjectPlayerListActivity> {

    public ProjectPlayerListActivityTest() {
        super(ProjectPlayerListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

}
