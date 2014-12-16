package ch.epfl.scrumtool.gui;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * @author ketsio
 */
public class OthersProfileOverviewActivity extends BaseMenuActivity {

    private TextView nameView;
    private TextView jobTitleView;
    private TextView companyNameView;
    private TextView dateOfBirthView;
    private TextView emailView;
    private TextView genderView;
    
    private User userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    private void init() {
        setContentView(R.layout.activity_profile_overview);
        userProfile = (User) getIntent().getSerializableExtra(User.SERIALIZABLE_NAME);
        Preconditions.throwIfNull("User cannot be null", userProfile);
       
        initViews();
    }
    
    private void initViews() {

        // Get Views
        nameView = (TextView) findViewById(R.id.profile_name);
        jobTitleView = (TextView) findViewById(R.id.profile_jobtitle);
        companyNameView = (TextView) findViewById(R.id.profile_company);
        dateOfBirthView = (TextView) findViewById(R.id.profile_date_of_birth);
        emailView = (TextView) findViewById(R.id.profile_email);
        genderView = (TextView) findViewById(R.id.profile_gender);

        // Set Views
        nameView.setText(userProfile.fullname());
        emailView.setText(userProfile.getEmail());
        if (userProfile.getJobTitle().length() > 0) {
            jobTitleView.setText(userProfile.getJobTitle());
        } else {
            findViewById(R.id.profile_field_jobtitle).setVisibility(View.GONE);
        }
        if (userProfile.getCompanyName().length() > 0) {
            companyNameView.setText(userProfile.getCompanyName());
        } else {
            findViewById(R.id.profile_field_company).setVisibility(View.GONE);
        }
        if (userProfile.getDateOfBirth() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                    .getString(R.string.format_date), Locale.ENGLISH);
            dateOfBirthView.setText(sdf.format(userProfile.getDateOfBirth()));
        } else {
            findViewById(R.id.profile_field_date_of_birth).setVisibility(View.GONE);
        }
        switch (userProfile.getGender()) {
            case MALE:
            case FEMALE:
                genderView.setText(userProfile.getGender().toString().toLowerCase(Locale.ENGLISH));
                break;
            default:
                findViewById(R.id.profile_field_gender).setVisibility(View.GONE);
                break;
        }
    }
}
