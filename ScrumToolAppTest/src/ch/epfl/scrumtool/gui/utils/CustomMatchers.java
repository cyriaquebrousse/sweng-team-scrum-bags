package ch.epfl.scrumtool.gui.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;

public class CustomMatchers {

    public final static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {

          @Override
          public void describeTo(Description description) {
            description.appendText("with class name: ");
            dataMatcher.describeTo(description);
          }

          @Override
          public boolean matchesSafely(View view) {
            if (!(view instanceof AdapterView)) {
              return false;
            }
            @SuppressWarnings("rawtypes")
            Adapter adapter = ((AdapterView) view).getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
              if (dataMatcher.matches(adapter.getItem(i))) {
                return true;
              }
            }
            return false;
          }
        };
      }
    
    public final static Matcher<View> withError(final String expectedError) {
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
