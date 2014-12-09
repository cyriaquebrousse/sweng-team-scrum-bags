package ch.epfl.scrumtool.gui;

import android.view.Menu;
import android.view.MenuItem;
import ch.epfl.scrumtool.R;

/**
 * This class is an extension of our {@link BaseMenuActivity} that adds
 * further functionalities to the action bar which are needed in activities
 * that display overviews of entities.
 * 
 * @author zenhaeus
 */
public abstract class BaseOverviewMenuActivity extends BaseMenuActivity {
    private static final int EDIT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(Menu.NONE, EDIT_ID, Menu.NONE, R.string.action_edit)
            .setIcon(R.drawable.ic_menu_edit)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.action_delete)
            .setIcon(R.drawable.ic_menu_delete)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        
        switch (item.getItemId()) {
            case EDIT_ID:
                openEditElementActivity();
                return true;
            case DELETE_ID:
                deleteElement();
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
    abstract void openEditElementActivity();
    
    
    /**
     * Deletes the element in the overview activity
     */
    abstract void deleteElement();
}
