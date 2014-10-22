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
        setQuantity(Float.valueOf(quantity));
        setUnit(unit);
    }
    
    public void setQuantity(float quantity) {
        quantityView.setText(Float.toString(quantity));
    }
    
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
        this.setBackgroundColor(color);
    }
}
