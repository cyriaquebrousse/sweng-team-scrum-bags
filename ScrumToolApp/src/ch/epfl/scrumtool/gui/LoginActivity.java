package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.GoogleSession;

/**
 * A login screen that offers login via OAuth 2.0 using the phones Google Accounts
 * 
 * @author Cyriaque Brousse, aschneuw, zenhaeus
 */
public class LoginActivity extends Activity {
    public static final int REQUEST_ACCOUNT_PICKER = 2;
    private GoogleSession.Builder sessionBuilder;
    private ProgressDialog pd = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
    }
    
    public void openAccountPicker(View view) {
        sessionBuilder = new GoogleSession.Builder();
        Intent googleAccountPicker = sessionBuilder.getIntent(this);
        this.startActivityForResult(googleAccountPicker, REQUEST_ACCOUNT_PICKER);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            this.pd = ProgressDialog.show(this, "Signing in..", "", true, false);
            sessionBuilder.authenticate(data, new Callback<Boolean>() {

                @Override
                public void interactionDone(Boolean object) {
                    if (LoginActivity.this.pd != null) {
                        LoginActivity.this.pd.dismiss();
                    }
                    if (object.booleanValue()) {
                        LoginActivity.this.openMenuActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                    }
                    
                }
            });
        } else {
            Toast.makeText(this, "Please choose an Account", Toast.LENGTH_LONG).show();
        }
    }

    public void openMenuActivity() {
        Log.d("LoginActivity", "openMenus");
        Intent openMenuIntent = new Intent(this, MenuActivity.class);
        startActivity(openMenuIntent);
    }
}
