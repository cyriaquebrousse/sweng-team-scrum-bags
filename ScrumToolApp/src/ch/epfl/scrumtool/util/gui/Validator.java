/**
 * 
 */
package ch.epfl.scrumtool.util.gui;

import android.widget.TextView;

/**
 * @author ketsio
 * 
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
    
    public static void check(TextView view, Validator v, Validator... validators) {
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
        if (view == null) {
            throw new NullPointerException();
        }
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