package ch.epfl.scrumtool.gui.test;

import ch.epfl.scrumtool.gui.ProjectEditActivity;
import android.test.ActivityInstrumentationTestCase2;

public class ProjectEditActivityTest extends ActivityInstrumentationTestCase2<ProjectEditActivity> {

    public ProjectEditActivityTest() {
        super(ProjectEditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}