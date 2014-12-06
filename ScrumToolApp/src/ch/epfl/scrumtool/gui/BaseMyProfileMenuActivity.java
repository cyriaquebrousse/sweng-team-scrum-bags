package ch.epfl.scrumtool.gui;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.network.Session;

/**
 * This class is an extension of our {@link BaseMenuActivity} that adds
 * further functionalities to the action bar which are needed in activities
 * that display overviews of entities.
 * 
 * @author zenhaeus
 */
public abstract class BaseMyProfileMenuActivity extends ScrumToolActivity implements OnMenuItemClickListener {
    private static final int EDIT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                this.logoutAndOpenLoginActivity();
                return true;
            default:
                return false;
        }
    }
    
    
    /**
     * Shows the overflow menu that contains the standard
     * overflow menu items
     * @param v
     *          view that the popup menu is attached to
     */
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow_logout, popup.getMenu());
        popup.show();
    }
    
    /**
     * Logs out the current user by destroying the current 
     * session and redirecting to the LoginActivity
     */
    private void logoutAndOpenLoginActivity() {
        Session.destroyCurrentSession(this);
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(Menu.NONE, EDIT_ID, Menu.NONE, R.string.action_edit)
            .setIcon(R.drawable.ic_menu_edit)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.action_delete)
            .setIcon(R.drawable.ic_menu_delete)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        
        switch (item.getItemId()) {
            case R.id.action_overflow:
                View v = (View) findViewById(R.id.action_overflow);
                showPopup(v);
                return true;
            case EDIT_ID:
                openEditElementActivity();
                return true;
            case DELETE_ID:
                item.setEnabled(false);
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
