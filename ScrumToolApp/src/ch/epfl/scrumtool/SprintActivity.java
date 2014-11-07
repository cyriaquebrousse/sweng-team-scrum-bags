package ch.epfl.scrumtool;

import ch.epfl.scrumtool.gui.components.widgets.DatePickerFragment;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    public void sprintEditDone(View v) {
        finish();
    }
}
