package ch.epfl.scrumtool.gui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import ch.epfl.scrumtool.R;

/**
 * This class is an extension of our {@link BaseMenuActivity} that adds
 * further functionalities to the action bar which are needed in activities
 * that display lists of entities.
 * 
 * @author zenhaeus
 * @param <E>
 *            type of the entity that child activity will list
 */
public abstract class BaseListMenuActivity<E> extends BaseMenuActivity {

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
