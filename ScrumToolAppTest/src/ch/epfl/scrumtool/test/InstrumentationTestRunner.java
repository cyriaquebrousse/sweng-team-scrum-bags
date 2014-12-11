package ch.epfl.scrumtool.test;

import com.google.android.apps.common.testing.testrunner.GoogleInstrumentationTestRunner;

import android.os.Bundle;

/**
 * 
 * @author 
 *
 */
public class InstrumentationTestRunner extends GoogleInstrumentationTestRunner {
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        /*
         * Fix according to https://code.google.com/p/dexmaker/issues/detail?id=2#c10
         */
        System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());
    }
}