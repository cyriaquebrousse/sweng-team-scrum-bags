package ch.epfl.scrumtool.gui.components.widgets;

import ch.epfl.scrumtool.entity.Priority;
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
