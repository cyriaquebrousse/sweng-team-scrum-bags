package ch.epfl.scrumtool.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.util.gui.InputVerifiers;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author AlexVeuthey
 */
public class SprintEditActivity extends BaseMenuActivity {

    // Sprint description
    private String name = null;
    private final Calendar today = Calendar.getInstance();
    private long sprintDeadline = today.getTimeInMillis();
    
    // User chosen date
    private Calendar chosen = Calendar.getInstance();
    
    // Views
    private TextView sprintDateView;
    private EditText sprintNameView;
    
    // Entities
    // If null, we are in insert mode, otherwise in edit mode.
    private Sprint sprint;
    private Project project;
    private Sprint.Builder sprintBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
        
        initOriginalAndParentProject();
        initViews();
    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment() {
            
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chosen.set(Calendar.YEAR, year);
                chosen.set(Calendar.MONTH, monthOfYear);
                chosen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sprintDeadline = chosen.getTimeInMillis();
                setDeadlineText(chosen);
            }
        };
        if (sprintDeadline < Calendar.getInstance().getTimeInMillis()) {
            Bundle args = new Bundle();
            args.putLong("long", sprintDeadline);
            newFragment.setArguments(args);
        }
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    public void sprintEditDone(View v) {
        name = sprintNameView.getText().toString();
        sprintDeadline = chosen.getTimeInMillis();
        InputVerifiers.updateTextViewAfterValidityCheck(sprintNameView, nameIsValid(), getResources());
        
        if (nameIsValid()) {
            if (dateIsValid()) {
                
                sprintBuilder.setDeadline(sprintDeadline);
                sprintBuilder.setTitle(name);
                
                if (sprint == null) {
                    insertSprint();
                } else {
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
    
    private void initOriginalAndParentProject() {
        sprint = (Sprint) getIntent().getSerializableExtra(Sprint.SERIALIZABLE_NAME);
        if (sprint == null) {
            sprintBuilder = new Sprint.Builder();
            sprintBuilder.setDeadline(sprintDeadline);
            setTitle(R.string.title_activity_sprint);
        } else {
            sprintBuilder = new Sprint.Builder(sprint);
            sprintDeadline = sprintBuilder.getDeadline();
            name = sprintBuilder.getTitle();
            setTitle(name);
        }
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent project cannot be null", project);
    }
    
    private void initViews() {
        sprintDateView = (TextView) findViewById(R.id.sprintDate);
        sprintNameView = (EditText) findViewById(R.id.editName);

        sprintNameView.setText(sprintBuilder.getTitle());
        
        final Calendar date = Calendar.getInstance();
        date.setTimeInMillis(sprintBuilder.getDeadline());
        setDeadlineText(date);
    }
    
    // =========== INSERTION ===========
    private void insertSprint() {
        Sprint sprint = sprintBuilder.build();
        final DefaultGUICallback<Sprint> sprintInserted = new DefaultGUICallback<Sprint>(this) {

            @Override
            public void interactionDone(Sprint object) {
                passResult(object);
                SprintEditActivity.this.finish();
            }
        };
        sprint.insert(project, sprintInserted);
    }
    
    // =========== UPDATE ============
    private void updateSprint() {
        final Sprint sprint = sprintBuilder.build();
        sprint.update(null, new DefaultGUICallback<Boolean>(this) {
            
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    passResult(sprint);
                    SprintEditActivity.this.finish();
                } else {
                    Toast.makeText(SprintEditActivity.this, "Could not update sprint", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void passResult(Sprint sprint) {
        Intent data = new Intent();
        data.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        setResult(1, data);
    }
    
    private void setDeadlineText(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        sprintDateView.setText(sdf.format(date.getTime()));
    }
    
    // CHECKS
    private boolean nameIsValid() {
        final int maxLength = 50;
        return name != null && name.length() > 0 && name.length() < maxLength;
    }
    
    private boolean dateIsValid() {
        final int maxH = 23;
        final int maxMAndS = 59;
        final Calendar yesterday = (Calendar) today.clone();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        yesterday.set(Calendar.HOUR, maxH);
        yesterday.set(Calendar.MINUTE, maxMAndS);
        yesterday.set(Calendar.SECOND, maxMAndS);
        return yesterday.getTimeInMillis() < sprintDeadline;
    }
}
