/**
 * 
 */
package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

/**
 * @author LeoWirz
 *
 */
public class ProjectListActivityTest extends
        ActivityInstrumentationTestCase2<ProjectListActivity> {

    /**
     * @param activityClass
     */
    public ProjectListActivityTest() {
        super(ProjectListActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

}
