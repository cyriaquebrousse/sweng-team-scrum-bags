package ch.epfl.scrumtool.gui;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.settings.ApplicationSettings;

/**
 * A login screen that offers login via OAuth 2.0 using the phones Google Accounts
 * 
 * @author Cyriaque Brousse, aschneuw, zenhaeus
 */
public class LoginActivity extends Activity {
    
    public static final int REQUEST_ACCOUNT_PICKER = 2;
    private GoogleSession.Builder sessionBuilder;
    private ProgressDialog progDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionBuilder = new GoogleSession.Builder(this);
        String accName = ApplicationSettings.getCachedUser(this);
        if (accName != null) {
            findViewById(R.id.button_login).setVisibility(View.INVISIBLE);
            login(accName);
        }
    }
    
    /**
     * Opens the account picker from where the user can 
     * chose the account he wants to log in with.
     * @param view
     */
    public void openAccountPicker(View view) {
        Intent googleAccountPicker = sessionBuilder.getIntent();
        this.startActivityForResult(googleAccountPicker, REQUEST_ACCOUNT_PICKER);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            this.progDialog = ProgressDialog.show(this, "Signing in...", "", true, false);
            String accName = (String) data.getExtras().get(AccountManager.KEY_ACCOUNT_NAME);
            
            ApplicationSettings.saveCachedUser(this, accName);
            
            this.login(accName);
        } else {
            Toast.makeText(this, "Please choose an acccount", Toast.LENGTH_LONG).show();
        }
    }

    private void openProjectListActivityAndFinish() {
        Intent openProjectListIntent = new Intent(this, ProjectListActivity.class);
        startActivity(openProjectListIntent);
        finish();
    }
    
    private void login(String accName) {
        DefaultGUICallback<Boolean> loginOK = new DefaultGUICallback<Boolean>(this) {
            
            @Override
            public void interactionDone(Boolean object) {
                if (LoginActivity.this.progDialog != null) {
                    LoginActivity.this.progDialog.dismiss();
                }
                if (object.booleanValue()) {
                    LoginActivity.this.openProjectListActivityAndFinish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
                
            }
        };
        
        sessionBuilder.build(accName, loginOK);
    }
}
