package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.network.GoogleSession;

/**
 * A login screen that offers login via email/password.
 * 
 * @author Cyriaque Brousse, aschneuw, zenhaeus
 */
public class LoginActivity extends Activity {

    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private GoogleSession.Builder sessionBuilder;

    // UI references.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionBuilder = new GoogleSession.Builder();
        Intent googleAccountPicker = sessionBuilder.getIntent(this);
        this.startActivityForResult(googleAccountPicker, REQUEST_ACCOUNT_PICKER);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            sessionBuilder.authenticate(data);
        }
    }


    public void openMenu(View view) {
        Intent openMenuIntent = new Intent(this, MenuActivity.class);
        startActivity(openMenuIntent);
    }
}
