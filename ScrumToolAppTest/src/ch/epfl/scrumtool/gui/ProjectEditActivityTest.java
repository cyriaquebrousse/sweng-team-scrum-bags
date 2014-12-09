package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

public class ProjectEditActivityTest extends BaseInstrumentationTestCase<ProjectEditActivity> {

    public ProjectEditActivityTest() {
        super(ProjectEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
