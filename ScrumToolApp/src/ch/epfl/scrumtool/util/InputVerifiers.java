package ch.epfl.scrumtool.util;

import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;

import android.content.res.Resources;
import android.widget.EditText;
import ch.epfl.scrumtool.R;

/**
 * @author Cyriaque Brousse
 * @author sylb
 */
public final class InputVerifiers {
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_ESTIMATION_VALUE = 100;

    /**
     * 
     * @param view
     * @return
     */
    public static boolean textEditNullOrEmpty(EditText view) {
        return (view == null) || (view.getText().length() <= 0);
    }

    /**
     * 
     * @param view
     * @return
     */
    public static boolean nameTooLong(EditText view) {
        return view.getText().length() >= MAX_NAME_LENGTH;
    }

    /**
     * Estimation verifier
     * @param view
     * @return
     */
    public static boolean estimationTooBig(EditText view) {
        final float estimation = sanitizeFloat(view.getText().toString());
        return Float.compare(estimation, MAX_ESTIMATION_VALUE) >= 0; 
    }

    /**
     * Name verifier
     * @param view
     * @param res
     * @return
     */
    public static boolean verifyNameIsValid(EditText view, Resources res) {
        if (textEditNullOrEmpty(view)) {
            view.setError(res.getString(R.string.error_field_required));
        } else if (nameTooLong(view)) {
            view.setError(res.getString(R.string.error_name_too_long));
        } else {
            view.setError(null);
        }
        return !textEditNullOrEmpty(view) && !nameTooLong(view);
    }

    /**
     * Description verifier
     * @param view
     * @param res
     * @return
     */
    public static boolean verifyDescriptionIsValid(EditText view, Resources res) {
        if (textEditNullOrEmpty(view)) {
            view.setError(res.getString(R.string.error_field_required));
        } else {
            view.setError(null);
        }
        return !textEditNullOrEmpty(view);
    }

    /**
     * Issue Estimation verifier
     * @param view
     * @param res
     * @return
     */
    public static boolean verifyEstimationIsValid(EditText view, Resources res) {
        if (textEditNullOrEmpty(view)) {
            view.setError(res.getString(R.string.error_field_required));
        } else if (estimationTooBig(view)) {
            view.setError(res.getString(R.string.error_estimation_too_big));
        } else {
            view.setError(null);
        }
        return !textEditNullOrEmpty(view) && !estimationTooBig(view);
    }

    /**
     * verifies the e-mail address for a given editText element
     * @param view
     * @param res
     * @return
     */
    public static boolean verifyEmailIsValid(EditText view, Resources res) {
        if (textEditNullOrEmpty(view)) {
            view.setError(res.getString(R.string.error_field_required));
        } else if (!emailIsValid(view.getText().toString())) {
            view.setError(res.getString(R.string.error_email_not_valid));
        } else {
            view.setError(null);
        }
        return !textEditNullOrEmpty(view) && emailIsValid(view.getText().toString());
    }

    /**
     * capitalized the first character
     * @param s
     * @return
     */
    public static String capitalize(String s) {
        s = s.toLowerCase(Locale.ENGLISH);
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * E-Mail-address validator
     * @param email
     * @return
     */
    public static boolean emailIsValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Checks whether the float value contained in the string is valid, and
     * returns it. If it is invalid (Java meaning), the return value will be
     * forced to be {@code 0f}.
     * 
     * @param string
     *            the String to try parsing
     * @return the float value contained in the string if it is valid,
     *         {@code 0f} otherwise
     */
    public static float sanitizeFloat(String string) {
        try {
            return Float.valueOf(string);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }
}