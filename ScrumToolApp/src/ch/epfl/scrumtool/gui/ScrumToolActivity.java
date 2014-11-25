package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.Session;

/**
 * @author zenhaeus
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
        if (Client.getScrumClient() == null) {
            Toast.makeText(this, "Session lost! Attempting new login.", Toast.LENGTH_SHORT).show();
            Session.relogin(this);
        }
    }
}
