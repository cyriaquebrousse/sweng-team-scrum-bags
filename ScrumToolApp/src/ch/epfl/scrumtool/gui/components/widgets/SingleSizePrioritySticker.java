package ch.epfl.scrumtool.gui.components.widgets;

import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
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
        updateStickerText();
    }

    /**
     * Updates the bullet sticker text by setting a unique letter, the first of
     * the priority state (e.g. N for normal, H for high, etc.).
     */
    private void updateStickerText() {
        assert getPriority() != null;
        
        char firstLetter = getPriority().toString().toUpperCase(Locale.ENGLISH).charAt(0);
        setStickerText(Character.toString(firstLetter));
    }
    
}
