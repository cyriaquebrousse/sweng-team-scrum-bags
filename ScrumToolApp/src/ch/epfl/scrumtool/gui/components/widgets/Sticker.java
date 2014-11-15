package ch.epfl.scrumtool.gui.components.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import ch.epfl.scrumtool.R;

/**
 * <p>
 * GUI element that represents a sticker, i.e. a small box in which one word is
 * present, on a colored background. Examples of use are: "URGENT", "SUCCESS",
 * "FAILURE", etc.
 * </p>
 * <b>Possible arguments:</b>
 * <ul>
 * <li>bold (boolean): whether the text will appear in bold</li>
 * <li>color (int): background color. Should NOT be white or equivalent</li>
 * <li>text (String): the sticker's text. Should be very short</li>
 * </ul>
 * 
 * The default background color is gray.
 * 
 * @author Cyriaque Brousse
 */
public class Sticker extends TextView {
    
    public Sticker(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        Resources res = context.getResources();

        // Getting the attributes from the set
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Sticker, 0, 0);
        boolean bold = attributes.getBoolean(R.styleable.Sticker_sticker_bold, false);
        int color = attributes.getColor(R.styleable.Sticker_sticker_color, res.getColor(R.color.Gray));
        String text = attributes.getString(R.styleable.Sticker_sticker_text);
        attributes.recycle();

        // Customizing the text view
        int padding = (int) res.getDimension(R.dimen.sticker_padding);
        setPadding(padding, padding, padding, padding);
        setTextColor(res.getColor(R.color.White));
        
        setStickerText(text);
        setColor(color);
        setBold(bold);
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
     * Sets the sticker text
     * 
     * @param text
     *            really should not exceed one word
     */
    public void setStickerText(String text) {
        setText(text);
    }
    
    /**
     * Sets the sticker in bold typeface, or restores it to normal appearance
     * 
     * @param bold
     *            true if the text should be in bold, false otherwise
     */
    public void setBold(boolean bold) {
        setTypeface(null, bold ? Typeface.BOLD : Typeface.NORMAL);
    }
}
