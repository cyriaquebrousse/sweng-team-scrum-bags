package ch.epfl.scrumtool.gui;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.settings.ApplicationSettings;

/**
 * A login screen that offers login via OAuth 2.0 using the phones Google Accounts
 * 
 * @author Cyriaque Brousse
 * @author aschneuw
 * @author zenhaeus
 */
public class LoginActivity extends Activity {

    public static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final Class<? extends Activity> FIRST_ACTIVITY = DashboardActivity.class;
    private GoogleSession.Builder sessionBuilder;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login);
        this.setTitle("Welcome");

        sessionBuilder = new GoogleSession.Builder(this);
        String accName = ApplicationSettings.getCachedUser(this);
        if (accName != null) {
            findViewById(R.id.button_login).setEnabled(false);
            login(accName, FIRST_ACTIVITY);
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
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String accName = (String) data.getExtras().get(AccountManager.KEY_ACCOUNT_NAME);
            this.progressDialog = ProgressDialog.show(this, "Signing in...", accName, true, false);
            
            ApplicationSettings.saveCachedUser(this, accName);
            
            this.login(accName, FIRST_ACTIVITY);
        } else {
            Toast.makeText(this, "Please choose an account", Toast.LENGTH_LONG).show();
        }
    }

    private void openFirstActivityAndFinish(final Class<? extends Activity> nextActivity) {
        if (getCallingActivity() == null) {
            Intent intent = new Intent(this, nextActivity);
            startActivity(intent);
        }
        finish();
    }
    
    private void login(final String accName, final Class<? extends Activity> nextActivity) {
        Button loginButton = (Button) findViewById(R.id.button_login);
        sessionBuilder.build(accName, new DefaultGUICallback<Boolean>(this, loginButton) {
            @Override
            public void interactionDone(Boolean success) {
                if (LoginActivity.this.progressDialog != null) {
                    LoginActivity.this.progressDialog.dismiss();
                }
                LoginActivity.this.openFirstActivityAndFinish(nextActivity);
            }
        });

    }
}
