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
     * @return true if the edit text is null or is empty, false otherwise
     */
    public static boolean textEditNullOrEmpty(EditText view) {
        return (view == null) || (view.getText().length() <= 0);
    }

    /**
     * @return true if the name contained in the view is too long, i.e. its
     *         length is greater or equal to {@link #MAX_NAME_LENGTH}, false
     *         otherwise
     */
    public static boolean nameTooLong(EditText view) {
        return view.getText().length() >= MAX_NAME_LENGTH;
    }

    /**
     * @return true if the estimation contained in the view is too big, i.e. is
     *         bigger than {@link #MAX_ESTIMATION_VALUE}, false otherwise
     */
    public static boolean estimationTooBig(EditText view) {
        final float estimation = sanitizeFloat(view.getText().toString());
        return Float.compare(estimation, MAX_ESTIMATION_VALUE) >= 0; 
    }

    /**
     * Returns the name validity decision on the provided view, with
     * side-effects on the view's error appearence
     * 
     * @param view
     *            view to evaluate, and to set error on if need be
     * @param res
     *            resources to get error strings from
     * @return true if the name contained in the view is valid, false otherwise
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
     * Returns the description validity decision on the provided view, with
     * side-effects on the view's error appearence
     * 
     * @param view
     *            view to evaluate, and to set error on if need be
     * @param res
     *            resources to get error strings from
     * @return true if the description contained in the view is valid, false
     *         otherwise
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
     * Returns the estimation validity decision on the provided view, with
     * side-effects on the view's error appearence
     * 
     * @param view
     *            view to evaluate, and to set error on if need be
     * @param res
     *            resources to get error strings from
     * @return true if the estimation contained in the view is valid, false
     *         otherwise
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
     * Returns the email address validity decision on the provided view, with
     * side-effects on the view's error appearence
     * 
     * @param view
     *            view to evaluate, and to set error on if need be
     * @param res
     *            resources to get error strings from
     * @return true if the email address contained in the view is valid, false
     *         otherwise
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
     * Sets the first character of the provided string to a capital letter,
     * leaves the rest of the string unchanged
     * 
     * @param s
     *            the string to capitalize
     * @return the capitalized string
     */
    public static String capitalize(String s) {
        s = s.toLowerCase(Locale.ENGLISH);
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * @param email
     *            email address to validate
     * @return true if email address is valid, false otherwise
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