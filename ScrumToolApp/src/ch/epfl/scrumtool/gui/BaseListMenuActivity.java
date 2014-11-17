package ch.epfl.scrumtool.gui;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu.OnMenuItemClickListener;
import ch.epfl.scrumtool.R;

/**
 * Abstracts the concept of our menus, which are similar throughout the
 * application
 * 
 * This class 
 * 
 * @author zenhaeus
 * @param <E>
 *            type of the entity we are mainly dealing with in this activity
 *            (e.g. Project)
 */
public abstract class BaseListMenuActivity<E> extends BaseMenuActivity implements OnMenuItemClickListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, Menu.FIRST, Menu.NONE, "New")
            .setIcon(R.drawable.ic_action_new)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        
        switch (item.getItemId()) {
            case Menu.FIRST:
                openCreateElementActivity();
                return true;
            default:
                return false;
        }
    }
    
    
    /**
     * Opens an activity to edit an element of type <E>
     * 
     * @param optionToEdit
     *            the element to edit
     */
    abstract void openEditElementActivity(E optionalElementToEdit);
    
    /**
     * Opens an activity to create a new element of type <E>
     */
    void openCreateElementActivity() {
        openEditElementActivity(null);
    }
}
