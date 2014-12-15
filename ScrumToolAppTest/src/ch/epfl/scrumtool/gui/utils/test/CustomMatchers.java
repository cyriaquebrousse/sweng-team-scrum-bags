package ch.epfl.scrumtool.gui.utils.test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.adapters.DefaultAdapter;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;
import ch.epfl.scrumtool.gui.components.widgets.RoleSticker;
/**
 * 
 * @author
 *
 */
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

    public final static Matcher<View> withHint(final String expectedHint) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AutoCompleteTextView)) {
                    return false;
                }
                String hint = "";
                if (((AutoCompleteTextView) view).getHint() != null) {
                    hint = ((AutoCompleteTextView) view).getHint().toString();
                }
                return expectedHint.equals(hint);
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

    public final static Matcher<View> withRole(final Role expectedRole) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof RoleSticker)) {
                    return false;
                }
                Role role = null;
                if (((RoleSticker) view).getRole() != null) {
                    role = (Role) ((RoleSticker) view).getRole();
                } else {
                    role = null;
                }


                return expectedRole.equals(role);
            }

            @Override
            public void describeTo(Description description) {
            }

        };
    }

    public final static Matcher<View> withStatusValue(final String expectedStatusValue) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView)) {
                    return false;
                }
                String statusValue = null;
                if (((TextView) view).getText() != null) {
                    statusValue = ((TextView) view).getText().toString();
                } else {
                    statusValue = null;
                }


                return expectedStatusValue.equals(statusValue);
            }

            @Override
            public void describeTo(Description description) {
            }

        };
    }


}
