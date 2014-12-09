package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * @author zenhaeus
 *
 * @param <T>
 */
public abstract class BaseInstrumentationTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    public BaseInstrumentationTestCase(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
    }
}