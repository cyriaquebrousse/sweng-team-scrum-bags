package ch.epfl.scrumtool.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.util.Preconditions;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.FieldType;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.PopupCallback;

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
    
    private Calendar calendar = Calendar.getInstance();
    private long dateOfBirthChosen = calendar.getTimeInMillis();
    
    private User userProfile;
    private User.Builder userBuilder;

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
        this.setTitle(userProfile.getName());
       
        initViews();
        initializeListeners();
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
    
    
    private void initializeListeners() {
        
        jobTitleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(OthersProfileOverviewActivity.this, FieldType.OTHER.setText("job title"),
                        jobTitleView.getText().toString(), new PopupCallback<String>() {
                            @Override
                            public void onModified(String userInput) {
                                userBuilder = new User.Builder(userProfile);
                                userBuilder.setJobTitle(userInput);
                                jobTitleView.setText(userInput);
                                updateUser();
                            }
                        });
            }
        });
        
        companyNameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(OthersProfileOverviewActivity.this,
                        FieldType.OTHER.setText("compagny name"),
                        companyNameView.getText().toString(), new PopupCallback<String>() {
                            @Override
                            public void onModified(String userInput) {
                                userBuilder = new User.Builder(userProfile);
                                userBuilder.setCompanyName(userInput);
                                companyNameView.setText(userInput);
                                updateUser();
                            }
                        });
            }
        });
        
        dateOfBirthView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        
    }
    
    private void updateUser() {
        userProfile = userBuilder.build();
        userProfile.update(new DefaultGUICallback<Void>(this) {
            @Override
            public void interactionDone(Void v) {
                try {
                    Session.getCurrentSession().setUser(userProfile);
                } catch (NotAuthenticatedException e) {
                    Session.relogin(OthersProfileOverviewActivity.this);
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                dateOfBirthChosen = calendar.getTimeInMillis();
                updateDateOfBirth();
            }
        };
        Bundle args = new Bundle();
        args.putLong("long", dateOfBirthChosen);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    private void updateDateOfBirth() {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        dateOfBirthView.setText(sdf.format(dateOfBirthChosen));
        userBuilder = new User.Builder(userProfile);
        userBuilder.setDateOfBirth(dateOfBirthChosen);
        updateUser();
        
    }
}
