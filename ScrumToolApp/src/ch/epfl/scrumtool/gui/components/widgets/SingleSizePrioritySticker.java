package ch.epfl.scrumtool.gui.components.widgets;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Priority;

/**
 * Single-size priority sticker. Can be used for example in lists, in which one
 * would want that all stickers have the same width, in order to keep list
 * elements aligned.<br>
 * To maintain this requirement, one must call {@link #updateStickerText()} each
 * time view modifications are applied.
 * 
 * @author Cyriaque Brousse
 * @see PrioritySticker
 */
public class SingleSizePrioritySticker extends PrioritySticker {

    /**
     * @param context
     * @param attrs
     */
    public SingleSizePrioritySticker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public void setPriority(Priority priority) {
        super.setPriority(priority);
        setBackgroundResource(R.drawable.circle);
        GradientDrawable shape = (GradientDrawable) getBackground();
        shape.setColor(getResources().getColor(priority.getColorRef()));
        updateStickerText();
    }
    
    @Override
    public void setColor(int color) {
    }

    /**
     * Updates the bullet sticker text by setting a unique letter, the first of
     * the priority state (e.g. N for normal, H for high, etc.).
     */
    private void updateStickerText() {
        assertTrue(getPriority() != null);
        setStickerText("");
    }
    
}
