package ch.epfl.scrumtool.gui.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.adapters.IssueListAdapter;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

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
    
    public final static Matcher<View> withPlayer(final Player expectedPlayer) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof Spinner)) {
                    return false;
                }
                Player player = null;
                if (((Spinner) view).getSelectedItem() != null) {
                     player = (Player) ((Spinner) view).getSelectedItem();
                } else {
                    player = null;
                }
               
                if (expectedPlayer == null && player == null) {
                    return true;
                } else {
                    return expectedPlayer.equals(player);
                }
            }

            @Override
            public void describeTo(Description description) {
            }

        };
    }
    
    public final static Matcher<View> withSprint(final Sprint expectedSprint) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof Spinner)) {
                    return false;
                }
                Sprint sprint = null;
                if (((Spinner) view).getSelectedItem() != null) {
                     sprint = (Sprint) ((Spinner) view).getSelectedItem();
                } else {
                    sprint = null;
                }
               

                return expectedSprint.equals(sprint);
            }

            @Override
            public void describeTo(Description description) {
            }

        };
    }
    
    public final static Matcher<View> withPriority(final Priority expectedPriority) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof PrioritySticker)) {
                    return false;
                }
                Priority priority = null;
                if (((PrioritySticker) view).getPriority() != null) {
                     priority = (Priority) ((PrioritySticker) view).getPriority();
                } else {
                    priority = null;
                }
               

                return expectedPriority.equals(priority);
            }

            @Override
            public void describeTo(Description description) {
            }

        };
    }
    
    
}
