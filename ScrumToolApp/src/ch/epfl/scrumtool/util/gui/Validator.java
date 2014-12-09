package ch.epfl.scrumtool.util.gui;

import android.widget.EditText;
import android.widget.TextView;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

/**
 * @author ketsio
 */
public enum Validator {
    NULLABLE, NOT_EMPTY, MAX_SIZE, MIN_SIZE, EMAIL;

    private int parameter = 0;

    private Validator() {
    }

    public Validator setParam(int parameter) {
        this.parameter = parameter;
        return this;
    }

    public int getParam() {
        return this.parameter;
    }
    
    // Constants
    
    public final static int SHORT_TEXT = 0;
    public final static int LONG_TEXT = 1;

    private final static int MIN_SHORT_TEXT = 2;
    private final static int MAX_SHORT_TEXT = 250;
    
    private final static int MIN_LONG_TEXT = 5;
    private final static int MAX_LONG_TEXT = 500;

    // Shortcuts

    public static void checkNullableMinAndMax(EditText view, int text) {
        int min = MIN_SHORT_TEXT;
        int max = MAX_SHORT_TEXT;
        
        switch (text) {
            case SHORT_TEXT:
                min = MIN_SHORT_TEXT;
                max = MAX_SHORT_TEXT;
                break;
            case LONG_TEXT:
                min = MIN_LONG_TEXT;
                max = MAX_LONG_TEXT;
                break;
            default:
                break;
        }
        
        Validator.check(view, Validator.NULLABLE,
                Validator.MIN_SIZE.setParam(min),
                Validator.MAX_SIZE.setParam(max));
    }

    // Checkers

    public static void check(TextView view, Validator v,
            Validator... validators) {
        boolean nullable = false;

        check(view, v);
        nullable = v.equals(NULLABLE);

        for (int i = 0; i < validators.length; i++) {
            check(view, validators[i]);
            nullable = validators[i].equals(NULLABLE) ? true : nullable;
        }

        if (nullable && view.getText().length() == 0) {
            view.setError(null);
        }
    }

    private static void check(TextView view, Validator v) {
        throwIfNull("Cannot check null view", view);

        CharSequence text = view.getText();

        switch (v) {

            case NOT_EMPTY:
                if (text.length() == 0) {
                    view.setError("This field cannot be empty");
                }
                break;
    
            case MAX_SIZE:
                int maxSize = v.getParam();
                if (text.length() > maxSize) {
                    view.setError("The text is too big : maximum size is "
                            + maxSize + "character" + (maxSize > 1 ? "s" : ""));
                }
                break;
    
            case MIN_SIZE:
                int minSize = v.getParam();
                if (text.length() < minSize) {
                    view.setError("The text is too small : minimum size is "
                            + minSize + "character" + (minSize > 1 ? "s" : ""));
                }
                break;
    
            case EMAIL:
                throw new RuntimeException("Not Yet Implemented");
    
            default:
                // We ignore validators that are not relevant for TextView
                break;
        }
    }
}