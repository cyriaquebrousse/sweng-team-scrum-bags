package ch.epfl.scrumtool.gui.components.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Priority;

/**
 * <p>
 * GUI element that represents a sticker, i.e. a box in which one word is
 * present, on a colored background. Examples of use are: "URGENT", "SUCCESS",
 * "FAILURE", etc.
 * </p>
 * <b>Possible arguments:</b>
 * <ul>
 * <li>bold (boolean): whether the text will appear in bold. Should NOT be white
 * or equivalent</li>
 * <li>color (int): background color</li>
 * <li>text (String): the sticker's text. Should be very short</li>
 * </ul>
 * 
 * @author Cyriaque Brousse
 */
public final class Sticker extends TextView {
    
    public Sticker(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        Resources res = context.getResources();

        // Getting the attributes from the set
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Sticker, 0, 0);
        boolean bold = attributes.getBoolean(R.styleable.Sticker_sticker_bold, false);
        int color = attributes.getColor(R.styleable.Sticker_sticker_color, res.getColor(R.color.darkgreen));
        String text = attributes.getString(R.styleable.Sticker_sticker_text);
        attributes.recycle();

        // Customizing the text view
        int padding = (int) res.getDimension(R.dimen.sticker_padding);
        setPadding(padding, padding, padding, padding);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, res.getDimension(R.dimen.sticker_text_size));
        setTextColor(res.getColor(R.color.White));
        
        setStickerText(text);
        setColor(color);
        setBold(bold);
    }
    
    public void setColor(int color) {
        setBackgroundColor(color);
    }
    
    public void setStickerText(String text) {
        setText(text);
    }
    
    public void setStickerText(Priority priority) {
        setText(priority.toString());
    }
    
    public void setBold(boolean bold) {
        setTypeface(null, bold ? Typeface.BOLD : Typeface.NORMAL);
    }
}
