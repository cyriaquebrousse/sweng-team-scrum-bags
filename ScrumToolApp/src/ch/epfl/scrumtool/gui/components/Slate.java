package ch.epfl.scrumtool.gui.components;

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
public final class Slate extends RelativeLayout {

    private int color;
    private String title;
    private String text;
    
    private TextView titleView;
    private TextView textView;
    
    public Slate(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = context.getResources();

        // Getting the attributes from the set
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Slate, 0, 0);
        this.color = attributes.getColor(R.styleable.Slate_slate_color,
                res.getColor(R.color.darkgreen));
        this.title = attributes.getString(R.styleable.Slate_slate_title);
        this.text = attributes.getString(R.styleable.Slate_slate_text);
        attributes.recycle();
        
        // Get the view elements
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slate, this, true);
        this.titleView = (TextView) getChildAt(0);
        this.textView = (TextView) getChildAt(1);
        
        // Customize the view
        this.setBackgroundColor(color);
        titleView.setText(title);
        textView.setText(text);
    }
}
