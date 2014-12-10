package ch.epfl.scrumtool.gui;

public class SprintOverviewActivityTest extends BaseInstrumentationTestCase<SprintOverviewActivity> {

    public SprintOverviewActivityTest() {
        super(SprintOverviewActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
