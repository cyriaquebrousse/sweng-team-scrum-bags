/**
 * 
 */
package ch.epfl.scrumtool.gui.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.view.View;
import android.widget.EditText;

/**
 * @author sylb
 *
 */
public final class Matchers {
    
    public static Matcher<View> withError(final String expectedError) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }
                String error = "";
                if (((EditText) view).getError() != null) {
                     error = ((EditText) view).getError().toString();
                } else {
                    error = "no error";
                }
               

                return expectedError.equals(error);
            }

            @Override
            public void describeTo(Description description) {
            }

        };
    }

}
