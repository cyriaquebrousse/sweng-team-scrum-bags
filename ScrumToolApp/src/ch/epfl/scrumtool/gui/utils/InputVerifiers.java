package ch.epfl.scrumtool.gui.utils;

import android.content.res.Resources;
import android.widget.EditText;
import ch.epfl.scrumtool.R;

/**
 * @author Cyriaque Brousse
 */
public final class InputVerifiers {
    private InputVerifiers() { }
    
    /**
     * Warn the user that incorrect input was entered in the specified text
     * field
     * 
     * @param view
     *            the text field in which the error sign will be displayed
     */
    public static void updateTextViewAfterValidityCheck(EditText view, boolean inputValid, Resources res) {
        if (!inputValid) {
            view.setError(res.getString(R.string.error_field_required));
        } else {
            view.setError(null);
        }
    }
}
