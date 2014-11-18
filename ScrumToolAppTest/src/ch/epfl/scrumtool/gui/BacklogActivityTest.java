/**
 * 
 */
package ch.epfl.scrumtool.gui;

import android.test.ActivityInstrumentationTestCase2;

/**
 * @author LeoWirz
 * 
 */
public class BacklogActivityTest extends
        ActivityInstrumentationTestCase2<BacklogActivity> {

    public BacklogActivityTest() {
        super(BacklogActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

}
