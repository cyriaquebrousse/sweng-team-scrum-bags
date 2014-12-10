package ch.epfl.scrumtool.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static ch.epfl.scrumtool.util.InputVerifiers.verifyNameIsValid;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author AlexVeuthey
 */
public class SprintEditActivity extends BaseEditMenuActivity {

    // Sprint description
    private String name = null;
    private final Calendar today = Calendar.getInstance();
    private long sprintDeadline = today.getTimeInMillis();
    
    // User chosen date
    private Calendar chosen = Calendar.getInstance();
    
    // Views
    private Button sprintDateView;
    private EditText sprintNameView;
    
    // Entities
    // If null, we are in insert mode, otherwise in edit mode.
    private Sprint sprint;
    private Project project;
    private Sprint.Builder sprintBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_edit);
        
        initOriginalAndParentProject();
        initViews();
    }
    
    @Override
    protected void saveElement() {
        saveSprintChanges();
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
        if (sprintDeadline > Calendar.getInstance().getTimeInMillis()) {
            Bundle args = new Bundle();
            args.putLong("long", sprintDeadline);
            newFragment.setArguments(args);
        }
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    private void saveSprintChanges() {
        name = sprintNameView.getText().toString();
        sprintDeadline = chosen.getTimeInMillis();
        
        boolean nameIsValid = verifyNameIsValid(sprintNameView, getResources());
        
        if (nameIsValid) {
            if (dateIsValid()) {
                findViewById(Menu.FIRST).setEnabled(false);
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
            setTitle(R.string.title_activity_sprint_edit_new);
        } else {
            sprintBuilder = new Sprint.Builder(sprint);
            sprintDeadline = sprintBuilder.getDeadline();
            name = sprintBuilder.getTitle();
        }
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent project cannot be null", project);
    }
    
    private void initViews() {
        sprintDateView = (Button) findViewById(R.id.sprint_date_edit);
        sprintNameView = (EditText) findViewById(R.id.sprint_name_edit);

        sprintNameView.setText(sprintBuilder.getTitle());
        
        chosen.setTimeInMillis(sprintBuilder.getDeadline());
        setDeadlineText(chosen);
    }
    
    private void insertSprint() {
        final Sprint sprint = sprintBuilder.build();
        final View next = findViewById(Menu.FIRST);
        sprint.insert(project, new DefaultGUICallback<Sprint>(this, next) {
            @Override
            public void interactionDone(Sprint object) {
                passResult(object);
                SprintEditActivity.this.finish();
            }
        });
    }
    
    private void updateSprint() {
        final Sprint sprint = sprintBuilder.build();
        final View next = findViewById(Menu.FIRST);
        sprint.update(new DefaultGUICallback<Void>(this, next) {
            @Override
            public void interactionDone(Void v) {
                passResult(sprint);
                SprintEditActivity.this.finish();
            }
        });
    }
    
    private void passResult(Sprint sprint) {
        Intent data = new Intent();
        data.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        setResult(RESULT_OK, data);
    }
    
    private void setDeadlineText(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        sprintDateView.setText(sdf.format(date.getTime()));
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
