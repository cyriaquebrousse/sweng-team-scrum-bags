package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.gui.components.widgets.DatePickerFragment;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        dobDateDisplay = (TextView) findViewById(R.id.profile_edit_dateofbirth);
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
    
    public void submitChanges(View view) {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
    }
    


}
