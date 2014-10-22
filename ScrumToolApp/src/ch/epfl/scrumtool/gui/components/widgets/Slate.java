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
public final class Slate extends RelativeLayout {

    private TextView titleView;
    private TextView textView;
    
    public Slate(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = context.getResources();

        // Getting the attributes from the set
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Slate, 0, 0);
        int color = attributes.getColor(R.styleable.Slate_slate_color,
                res.getColor(R.color.darkgreen));
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
        setTitle(title);
        setText(text);
    }
    
    public void setColor(int color) {
        setBackgroundColor(color);
    }
    
    public void setTitle(String title) {
        titleView.setText(title);
    }
    
    public void setText(String text) {
        textView.setText(text);
    }
}
