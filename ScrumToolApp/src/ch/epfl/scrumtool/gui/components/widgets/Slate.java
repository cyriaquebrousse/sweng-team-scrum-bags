package ch.epfl.scrumtool.gui.components.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ch.epfl.scrumtool.R;

/**
 * GUI element that represents a slate, typically used to display a state title
 * and its value. A typical example of use is:<br>
 * {@code | Estimation} <br>
 * {@code | 3 days}<br>
 * The first part of the example is called the title, and the second one is the
 * text. A slate is a two-line block of text, hence the title and the text are
 * at most one line long each. All non-fitting words are dropped. Please see the
 * {@link #setTitle(String)} and {@link #setText(String)} javadoc. <br>
 * The slate also has a background color.
 * 
 * @author Cyriaque Brousse
 */
public final class Slate extends RelativeLayout {

    private TextView titleView;
    private TextView textView;
    
    public Slate(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = context.getResources();

        // Getting the attributes from the set
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Slate, 0, 0);
        Drawable color = attributes.getDrawable(R.styleable.Slate_slate_color);
        int textColor = attributes.getColor(R.styleable.Slate_slate_text_color,
                res.getColor(R.color.White));
        String title = attributes.getString(R.styleable.Slate_slate_title);
        String text = attributes.getString(R.styleable.Slate_slate_text);
        attributes.recycle();
        
        // Get the view elements
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slate, this, true);
        this.titleView = (TextView) getChildAt(0);
        this.textView = (TextView) getChildAt(1);
        
        // Customize the view
        int padding = (int) res.getDimension(R.dimen.slate_padding);
        setPadding(padding, padding, padding, padding);
        
        setColor(color);
        setTextColor(textColor);
        setTitle(title);
        setText(text);
    }

    /**
     * @param color
     *            MUST be a R color, not a reference to it! This means you have
     *            to call {@code context.getResources.getColor(int resid)} at
     *            some point and pass the result to this method
     */
    public void setTextColor(int color) {
        titleView.setTextColor(color);
        textView.setTextColor(color);
    }

    /**
     * @param color
     *            MUST be a R color, not a reference to it! This means you have
     *            to call {@code context.getResources.getColor(int resid)} at
     *            some point and pass the result to this method
     */
    public void setColor(int color) {
        setBackgroundColor(color);
    }
    
    /**
     * @param drawable
     *            MUST be a R drawable, not a reference to it! This means you have
     *            to call {@code context.getResources.getDrawable(int resid)} at
     *            some point and pass the result to this method
     */
    public void setColor(Drawable drawable) {
        setBackground(drawable);
    }
    
    /**
     * Sets the title of the slate. Keep in mind that the title must fit in a
     * single line. Additional words are truncated.
     * 
     * @param title
     *            the title. See requirements above
     */
    public void setTitle(String title) {
        titleView.setText(title);
    }
    
    /**
     * Sets the text of the slate. Keep in mind that this text must fit in a
     * single line. Additional words are truncated.
     * 
     * @param title
     *            the text. See requirements above
     */
    public void setText(String text) {
        textView.setText(text);
    }
}
