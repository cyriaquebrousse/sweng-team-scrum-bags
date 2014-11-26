package ch.epfl.scrumtool.util.gui;

import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;

import android.content.res.Resources;
import android.widget.EditText;
import ch.epfl.scrumtool.R;

/**
 * @author Cyriaque Brousse
 */
public final class InputVerifiers {
    private static final int MAX_NAME_LENGTH = 50;

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
    
    public static boolean textEditNonNullNotEmpty(EditText view) {
        return view != null
            && view.getText().length() > 0;
    }
    
    public static String capitalize(String s) {
        s = s.toLowerCase(Locale.ENGLISH);
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
    
    public static boolean entityNameIsValid(EditText view) {
        return textEditNonNullNotEmpty(view) && view.getText().length() <= MAX_NAME_LENGTH;
    }
    
    public static boolean emailIsValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
