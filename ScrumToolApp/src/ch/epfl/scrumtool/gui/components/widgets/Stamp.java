package ch.epfl.scrumtool.gui.components.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ch.epfl.scrumtool.R;

/**
 * GUI element that represents a stamp (as in post stamp). It is used to display
 * a quantity and a unit, for instance "3 days". The quantity is displayed in
 * the first line, in bigger font than the unit, that lies on the second line.<br>
 * The stamp could typically be used in a ListView or in a Activity header. The
 * stamp is a two-line block of text, and has a small width, so it can really be
 * used only for small texts (e.g. "3 days"). Any non-fitting text is truncated.<br>
 * Please see {@link #setQuantity(String)} and {@link #setUnit(String)} javadoc.<br>
 * The stamp also has a background color.
 * 
 * @author Cyriaque Brousse
 */
public final class Stamp extends RelativeLayout {

    private TextView quantityView;
    private TextView unitView;
    
    public Stamp(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = context.getResources();

        // Getting the attributes from the set
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Stamp, 0, 0);
        int color = attributes.getColor(R.styleable.Stamp_stamp_color,
                res.getColor(R.color.darkgreen));
        String quantity = attributes.getString(R.styleable.Stamp_stamp_content_quantity);
        String unit = attributes.getString(R.styleable.Stamp_stamp_content_unit);
        attributes.recycle();
        
        // Get the view elements
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.stamp, this, true);
        this.quantityView = (TextView) getChildAt(0);
        this.unitView = (TextView) getChildAt(1);
        
        // Customize the view
        setColor(color);
        setQuantity(quantity);
        setUnit(unit);
    }
    
    /**
     * Sets the quantity text of the stamp. Example: the "3" in "3 days".
     * 
     * @param quantity
     *            it should not exceed one word. Non-fitting text is not
     *            displayed
     */
    public void setQuantity(String quantity) {
        quantityView.setText(quantity);
    }
    
    /**
     * Sets the unit text of the stamp. Example: the "days" in "3 days".
     * 
     * @param unit
     *            it should not exceed one word. Non-fitting text is not
     *            displayed
     */
    public void setUnit(String unit) {
        unitView.setText(unit);
    }
    
    /**
     * @param color
     *            MUST be a R color, not a reference to it! Which means you have
     *            to call {@code context.getResources.getColor(int resid)} at
     *            some point and pass the result to this method
     */
    public void setColor(int color) {
        setBackgroundColor(color);
    }
}
