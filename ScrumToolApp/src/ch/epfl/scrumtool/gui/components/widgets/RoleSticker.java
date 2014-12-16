package ch.epfl.scrumtool.gui.components.widgets;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Role;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Sticker that represents a Scrum Role
 * 
 * @author vincent
 */
public class RoleSticker extends Sticker {

    private Role role;
    
    public RoleSticker(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Adjusting the padding
        int smallSpace = getResources().getDimensionPixelSize(R.dimen.small_space);
        int space = getResources().getDimensionPixelSize(R.dimen.space);
        setPaddingRelative(space, smallSpace, space, smallSpace);
    }
    
    /**
     * Sets the sticker's role
     * 
     * @param role
     *            the new role
     */
    public void setRole(Role role) {
        assertTrue(role != null);
        
        this.role = role;
        super.setStickerText(role.toString());
        super.setColor(getResources().getColor(R.color.blue));
    }
    
    /**
     * @return the role
     */
    public Role getRole() {
        return this.role;
    }

    @Override
    public final void setStickerText(String text) {
        super.setStickerText(text);
    }
    
    @Override
    public void setColor(int color) {
        super.setColor(color);
    }
}
