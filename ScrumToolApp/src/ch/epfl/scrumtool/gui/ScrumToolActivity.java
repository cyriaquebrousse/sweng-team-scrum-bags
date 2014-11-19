package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.Session;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author zenhaeus
 *
 */
public abstract class ScrumToolActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSession();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        checkSession();
    }
    
    private void checkSession() {
        try {
            Session.getCurrentSession();
        } catch (NotAuthenticatedException e) {
            Toast.makeText(this, "Authentication failed! Attempting new login.", Toast.LENGTH_SHORT).show();
            Session.relogin(this);
        }
    }
}
