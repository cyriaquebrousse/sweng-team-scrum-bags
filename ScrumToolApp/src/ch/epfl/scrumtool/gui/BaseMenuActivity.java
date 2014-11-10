package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.network.Session;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * Abstracts the concept of our menus, which are similar throughout the
 * application
 * 
 * @author Cyriaque Brousse
 * @param <E>
 *            type of the entity we are mainly dealing with in this activity
 *            (e.g. Project)
 */
public abstract class BaseMenuActivity<E> extends Activity implements OnMenuItemClickListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                openCreateElementActivity();
                return true;
            case R.id.action_overflow:
                View v = (View) findViewById(R.id.action_overflow);
                showPopup(v);
                return true;
            case R.id.action_logout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutAndOpenLoginActivity();
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
    
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popup.getMenu());
        popup.show();
    }
    
    private void logoutAndOpenLoginActivity() {
        Session.destroyCurrentSession(this);
        Intent openLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(openLoginIntent);
        this.finish();
    }
    
}
