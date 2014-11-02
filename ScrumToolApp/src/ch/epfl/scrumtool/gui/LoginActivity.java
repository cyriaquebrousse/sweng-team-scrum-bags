package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.network.GoogleSession;

/**
 * A login screen that offers login via OAuth 2.0 using the phones Google Accounts
 * 
 * @author Cyriaque Brousse, aschneuw, zenhaeus
 */
public class LoginActivity extends Activity {
    public static final int REQUEST_ACCOUNT_PICKER = 2;
    private GoogleSession.Builder sessionBuilder;

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

    public void openMenuActivity() {
        Log.d("LoginActivity", "openMenus");
        Intent openMenuIntent = new Intent(this, MenuActivity.class);
        startActivity(openMenuIntent);
    }
}
