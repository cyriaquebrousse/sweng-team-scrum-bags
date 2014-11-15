package ch.epfl.scrumtool.gui;

import java.util.Date;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.widgets.DatePickerFragment;
import ch.epfl.scrumtool.gui.util.InputVerifiers;
import ch.epfl.scrumtool.network.Client;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintActivity extends Activity {

    private int sprintYear = 0;
    private int sprintMonth = 0;
    private int sprintDay = 0;
    
    private Date sprintDeadline;
    private String name = null;
    
    private TextView sprintDate;
    private EditText sprintName;
    
    // if null, we are in insert mode, otherwise in edit mode.
    private Sprint sprint;
    private Project project;
    private Sprint.Builder sprintBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
        sprintDate = (TextView) findViewById(R.id.sprintDate);
        sprintName = (EditText) findViewById(R.id.editName);
    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment() {
            
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                sprintYear = year;
                sprintMonth = monthOfYear + 1; // monthOfYear start at 0
                sprintDay = dayOfMonth;
                sprintDate.setText(sprintDay + "/" + sprintMonth + "/" + sprintYear);
            }
        };
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    @SuppressWarnings("deprecation")
    public void sprintEditDone(View v) {
        InputVerifiers.updateTextViewAfterValidityCheck(sprintName, nameIsValid(), getResources());
        
        if (nameIsValid()) {
            if (dateIsValid()) {
                sprintDeadline.setYear(sprintYear);
                sprintDeadline.setMonth(sprintMonth);
                sprintDeadline.setDate(sprintDay);
                name = sprintName.getText().toString();
                
                sprintBuilder.setDeadline(sprintDeadline);
                sprintBuilder.setTitle(name);
                
                if (sprint == null) {
                    insertSprint();
                } else {
                    updateSprint();
                }
            } else {
                Toast.makeText(SprintActivity.this, "Invalid date", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SprintActivity.this, "Invalid name", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void insertSprint() {
        Sprint sprint = sprintBuilder.build();
        final DefaultGUICallback<Sprint> sprintInserted = new DefaultGUICallback<Sprint>(this) {

            @Override
            public void interactionDone(Sprint object) {
                SprintActivity.this.finish();
            }
        };
        Client.getScrumClient().insertSprint(sprint, project.getKey(),  sprintInserted);
    }
    
    private void updateSprint() {
        Sprint sprint = sprintBuilder.build();
        // TODO really not sure about the null here...
        Client.getScrumClient().updateSprint(sprint, null, new DefaultGUICallback<Boolean>(this) {
            
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    SprintActivity.this.finish();
                } else {
                    Toast.makeText(SprintActivity.this, "Could not update sprint", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private boolean nameIsValid() {
        final int maxLength = 50;
        return name != null && name.length() > 0 && name.length() < maxLength;
    }
    
    private boolean dateIsValid() {
        return sprintDeadline != null && sprintDeadline.after(new Date());
    }
}
