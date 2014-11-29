package ch.epfl.scrumtool.gui.components.widgets;

import android.content.Context;
import android.util.AttributeSet;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Priority;

/**
 * Sticker that represents a Scrum priority (and nothing else)
 * 
 * @author Cyriaque Brousse
 * @see Sticker
 * @see Priority
 */
public class PrioritySticker extends Sticker {
    
    private Priority priority;
    
    /**
     * @param context
     * @param attrs
     */
    public PrioritySticker(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Adjusting the padding
        int smallSpace = getResources().getDimensionPixelSize(R.dimen.small_space);
        int space = getResources().getDimensionPixelSize(R.dimen.space);
        setPaddingRelative(space, smallSpace, space, smallSpace);
    }
    
    public void setPriority(Priority priority) {
        assert priority != null;
        
        this.priority = priority;
        super.setStickerText(priority.toString());
        super.setColor(getResources().getColor(priority.getColorRef()));
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public final void setStickerText(String text) {
        super.setStickerText(text);
    }
    
    @Override
    public final void setColor(int color) {
        super.setColor(color);
    }
}
