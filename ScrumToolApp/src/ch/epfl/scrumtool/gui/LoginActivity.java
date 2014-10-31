package ch.epfl.scrumtool.gui;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.network.Session;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

/**
 * A login screen that offers login via email/password.
 * @author Cyriaque Brousse
 */
public class LoginActivity extends Activity {

    // UI references.
    private GoogleAccountCredential googleCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleCredential = GoogleAccountCredential.usingAudience(this, "server:client_id:" + Session.CLIENT_ID);
        Intent googleAccountPicker = googleCredential.newChooseAccountIntent();
        this.startActivityForResult(googleAccountPicker, Session.REQUEST_ACCOUNT_PICKER);


        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.d("LoginActivity", (String) data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME));
            googleCredential.setSelectedAccountName((String) data.getExtras().get(AccountManager.KEY_ACCOUNT_NAME));
            Session.authenticate(googleCredential, this);
        }
    }

    public void openMenu(View view) {
        Intent openMenuIntent = new Intent(this, MenuActivity.class);
        startActivity(openMenuIntent);
    }
}
