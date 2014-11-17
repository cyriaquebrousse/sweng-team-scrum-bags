package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.util.Validator;
import ch.epfl.scrumtool.network.Session;

/**
 * 
 * @author ketsio
 * 
 */
public class ProfileEditActivity extends Activity {

    private int dobYear = -1;
    private int dobMonth = -1;
    private int dobDay = -1;

    private TextView dobDateDisplay;
    private EditText firstNameView;
    private EditText lastNameView;
    private EditText jobTitleView;
    private EditText companyNameView;
    private EditText genderView;

    private User connectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        try {
            connectedUser = Session.getCurrentSession().getUser();
        } catch (NotAuthenticatedException e) {
            // TODO Redirection to login page
        }

        initViews();
    }

    private void initViews() {
        firstNameView = (EditText) findViewById(R.id.profile_edit_firstname);
        lastNameView = (EditText) findViewById(R.id.profile_edit_lastname);
        jobTitleView = (EditText) findViewById(R.id.profile_edit_jobtitle);
        companyNameView = (EditText) findViewById(R.id.profile_edit_company);
        genderView = (EditText) findViewById(R.id.profile_edit_gender);
        dobDateDisplay = (TextView) findViewById(R.id.profile_edit_dateofbirth);

        if (connectedUser.getName().length() > 0) {
            firstNameView.setText(connectedUser.getName());
        }
        if (connectedUser.getLastName().length() > 0) {
            lastNameView.setText(connectedUser.getLastName());
        }
        if (connectedUser.getJobTitle().length() > 0) {
            jobTitleView.setText(connectedUser.getJobTitle());
        }
        if (connectedUser.getCompanyName().length() > 0) {
            companyNameView.setText(connectedUser.getCompanyName());
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dobYear = year;
                dobMonth = monthOfYear + 1; // monthOfYear start at 0
                dobDay = dayOfMonth;
                dobDateDisplay.setText(dobDay + "/" + dobMonth + "/" + dobYear);
            }
        };
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void saveUserChanges(View view) {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();

        checkNullableMinAndMax(firstNameView);
        checkNullableMinAndMax(lastNameView);
        checkNullableMinAndMax(jobTitleView);
        checkNullableMinAndMax(companyNameView);
        // TODO : gender not a member of user yet
        
        if (firstNameView.getError() == null 
                && lastNameView.getError() == null
                && jobTitleView.getError() == null
                && companyNameView.getError() == null) {
            
            findViewById(R.id.profile_edit_submit_button).setEnabled(false);
            
            User.Builder userBuilder = new User.Builder(connectedUser);
            userBuilder.setName(firstNameView.getText().toString());
            userBuilder.setLastName(lastNameView.getText().toString());
            userBuilder.setJobTitle(jobTitleView.getText().toString());
            userBuilder.setCompanyName(companyNameView.getText().toString());
            // TODO : transforme day/month/year to (long) time
            
            User userToUpdate = userBuilder.build();
            userToUpdate.update(new DefaultGUICallback<Boolean>(this) {
                @Override
                public void interactionDone(Boolean success) {
                    if (success.booleanValue()) {
                        ProfileEditActivity.this.finish();
                    } else {
                        Toast.makeText(ProfileEditActivity.this, "Could not edit profile", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    
    // Shortcut
    private void checkNullableMinAndMax(EditText view) {
        Validator.check(view, 
                Validator.NULLABLE,
                Validator.MIN_SIZE.setParam(2),
                Validator.MAX_SIZE.setParam(250));
    }

}
