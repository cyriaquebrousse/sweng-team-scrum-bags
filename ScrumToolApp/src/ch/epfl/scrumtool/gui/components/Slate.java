package ch.epfl.scrumtool.gui.components;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import ch.epfl.scrumtool.R;

/**
 * @author Cyriaque Brousse
 */
public class Slate extends RelativeLayout {

    /**
     * @param context
     * @param attrs
     */
    public Slate(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = context.getResources();

        // Getting the attributes from the set
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Slate, 0, 0);
        int color = attributes.getColor(R.styleable.Slate_slate_color,
                res.getColor(R.color.darkgreen));
        String quantity = attributes.getString(R.styleable.Slate_slate_content_quantity);
        String unit = attributes.getString(R.styleable.Slate_slate_content_unit);
        attributes.recycle();
        
        // Customize view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slate, this, true);

        // TODO Auto-generated constructor stub
    }

}
