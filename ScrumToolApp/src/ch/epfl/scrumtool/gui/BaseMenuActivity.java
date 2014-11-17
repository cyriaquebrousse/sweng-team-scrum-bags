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
 * The BaseMenuActivity class is an Activity that has a standard action bar 
 * which provides functionalities which should be accessible from most 
 * activities inside the ScrumToolApp.
 * 
 * @author Cyriaque Brousse, zenhaeus
 */
public abstract class BaseMenuActivity extends Activity implements OnMenuItemClickListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_overflow:
                View v = (View) findViewById(R.id.action_overflow);
                showPopup(v);
                return true;
            default:
                return false;
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
     * Shows the overflow menu that contains the standard
     * overflow menu items
     * @param v
     *          view that the popup menu is attached to
     */
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popup.getMenu());
        popup.show();
    }
    
    /**
     * Logs out the current user by destroying the current 
     * session and redirecting to the LoginActivity
     */
    private void logoutAndOpenLoginActivity() {
        Session.destroyCurrentSession(this);
        Intent openLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(openLoginIntent);
        this.finish();
    }
    
}
