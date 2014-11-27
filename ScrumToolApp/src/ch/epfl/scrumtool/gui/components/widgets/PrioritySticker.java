package ch.epfl.scrumtool.gui.components.widgets;

import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

/**
 * @author Cyriaque Brousse
 */
public class PrioritySticker extends Sticker {
    
    private Priority priority;
    private Resources res;
    
    /**
     * @param context
     * @param attrs
     */
    public PrioritySticker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.res = context.getResources();

        // Adjusting the padding
        int smallSpace = res.getDimensionPixelSize(R.dimen.small_space);
        int space = res.getDimensionPixelSize(R.dimen.space);
        setPaddingRelative(space, smallSpace, space, smallSpace);
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
        setStickerText(priority.toString());
        setColor(res.getColor(priority.getColorRef()));
    }
    
    public Priority getPriority() {
        return priority;
    }
    
}
