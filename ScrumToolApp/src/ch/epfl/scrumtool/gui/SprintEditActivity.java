package ch.epfl.scrumtool.gui;

import java.util.Calendar;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.util.InputVerifiers;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintEditActivity extends BaseMenuActivity {

    private int sprintYear = 0;
    private int sprintMonth = 0;
    private int sprintDay = 0;
    
    private final Calendar today = Calendar.getInstance();
    private long sprintDeadline = today.getTimeInMillis();
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
        
        initOriginalAndParentTask();
        initViews();
    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment() {
            
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                updateDate(view);
                sprintYear = year;
                sprintMonth = monthOfYear + 1; // monthOfYear start at 0
                sprintDay = dayOfMonth;
                sprintDate.setText(sprintDay + "/" + sprintMonth + "/" + sprintYear);
            }
        };
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    public void sprintEditDone(View v) {
        name = sprintName.getText().toString();
        final Calendar chosen = (Calendar) today.clone();
        if (sprintYear != chosen.get(Calendar.YEAR)) {
            chosen.set(Calendar.YEAR, sprintYear);
        }
        if (sprintMonth != chosen.get(Calendar.MONTH)) {
            chosen.set(Calendar.MONTH, sprintMonth);
        }
        if (sprintDay != chosen.get(Calendar.DAY_OF_MONTH)) {
            chosen.set(Calendar.DAY_OF_MONTH, sprintDay);
        }
        sprintDeadline = chosen.getTimeInMillis();
        InputVerifiers.updateTextViewAfterValidityCheck(sprintName, nameIsValid(), getResources());
        
        if (nameIsValid()) {
            if (dateIsValid()) {
                
                sprintBuilder.setDeadline(sprintDeadline);
                sprintBuilder.setTitle(name);
                
                if (sprint == null) {
                    insertSprint();
                } else {
                    Log.d("test", "test");
                    updateSprint();
                }
            } else {
                Toast.makeText(SprintEditActivity.this, "Invalid date", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SprintEditActivity.this, "Invalid name", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entitylist_context, menu);
    }
    
    private void insertSprint() {
        Sprint sprint = sprintBuilder.build();
        final DefaultGUICallback<Sprint> sprintInserted = new DefaultGUICallback<Sprint>(this) {

            @Override
            public void interactionDone(Sprint object) {
                SprintEditActivity.this.finish();
            }
        };
        sprint.insert(project, sprintInserted);
    }
    
    private void updateDate(DatePicker view) {
        Calendar oldDate = Calendar.getInstance();
        oldDate.setTimeInMillis(sprintDeadline);
        view.updateDate(oldDate.get(Calendar.YEAR), oldDate.get(Calendar.MONTH), oldDate.get(Calendar.DAY_OF_MONTH));
    }
    
    private void updateSprint() {
        Sprint newSprint = sprintBuilder.build();
        newSprint.update(null, new DefaultGUICallback<Boolean>(this) {
            
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    SprintEditActivity.this.finish();
                } else {
                    Toast.makeText(SprintEditActivity.this, "Could not update sprint", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private boolean nameIsValid() {
        final int maxLength = 50;
        return name != null && name.length() > 0 && name.length() < maxLength;
    }
    
    private boolean dateIsValid() {
        final int maxH = 23;
        final int maxMAndS = 59;
        final Calendar yesterday = (Calendar) today.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        yesterday.set(Calendar.HOUR, maxH);
        yesterday.set(Calendar.MINUTE, maxMAndS);
        yesterday.set(Calendar.SECOND, maxMAndS);
        return yesterday.getTimeInMillis() < sprintDeadline;
    }
    
    private void initOriginalAndParentTask() {
        sprint = (Sprint) getIntent().getSerializableExtra(Sprint.SERIALIZABLE_NAME);
        if (sprint == null) {
            sprintBuilder = new Sprint.Builder();
            sprintBuilder.setDeadline(sprintDeadline);
            setTitle(R.string.title_activity_sprint);
        } else {
            sprintBuilder = new Sprint.Builder(sprint);
            setTitle(R.string.title_activity_sprint_edit);
        }
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project == null) {
            throw new NullPointerException("Parent project cannot be null");
        }
    }
    
    private void initViews() {
        sprintDate = (TextView) findViewById(R.id.sprintDate);
        sprintName = (EditText) findViewById(R.id.editName);
        
        sprintDate.setText(getDate(sprintBuilder));
        sprintName.setText(sprintBuilder.getTitle());
    }
    
    private String getDate(Sprint.Builder builder) {
        final Calendar d = Calendar.getInstance();
        d.setTimeInMillis(builder.getDeadline());
        return d.get(Calendar.DAY_OF_MONTH) + "/" + d.get(Calendar.MONTH) + "/" + d.get(Calendar.YEAR);
    }
}
