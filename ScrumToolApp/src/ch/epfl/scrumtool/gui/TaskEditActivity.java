package ch.epfl.scrumtool.gui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;
import ch.epfl.scrumtool.util.gui.Dialogs;
import ch.epfl.scrumtool.util.gui.InputVerifiers;
import ch.epfl.scrumtool.util.gui.Dialogs.DialogCallback;

/**
 * @author Cyriaque Brousse
 */
public class TaskEditActivity extends BaseMenuActivity {
    
    private EditText taskNameView;
    private EditText taskDescriptionView;
    private PrioritySticker taskPriorityView;
    
    /** If {@code null} then we are in create mode, otherwise in edit mode*/
    private MainTask original;
    private Project parentProject;
    private MainTask.Builder taskBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        
        initOriginalAndParentProject();
        initViews();
    }

    private void initOriginalAndParentProject() {
        original = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);
        if (original == null) {
            taskBuilder = new MainTask.Builder();
            setTitle(R.string.title_activity_task_edit_new);
        } else {
            taskBuilder = new MainTask.Builder(original);
        }
        
        parentProject = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (parentProject == null) {
            throw new NullPointerException("Parent project cannot be null");
        }
    }
    
    private void initViews() {
        taskNameView = (EditText) findViewById(R.id.task_name_edit);
        taskDescriptionView = (EditText) findViewById(R.id.task_description_edit);
        taskPriorityView = (PrioritySticker) findViewById(R.id.task_priority_edit);
        
        taskNameView.setText(taskBuilder.getName());
        taskDescriptionView.setText(taskBuilder.getDescription());
        taskPriorityView.setPriority(taskBuilder.getPriority());
        
        taskPriorityView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showTaskPriorityEditDialog(TaskEditActivity.this, new DialogCallback<Priority>() {
                    @Override
                    public void onSelected(Priority selected) {
                        taskBuilder.setPriority(selected);
                        taskPriorityView.setPriority(selected);
                    }
                });
            }
        });
    }
    
    public void saveTaskChanges(View view) {
        InputVerifiers.updateTextViewAfterValidityCheck(taskNameView, nameIsValid(), getResources());
        InputVerifiers.updateTextViewAfterValidityCheck(taskDescriptionView, descriptionIsValid(), getResources());
        
        if (nameIsValid() && descriptionIsValid()) {
            findViewById(R.id.task_edit_button_next).setEnabled(false);
            String newName = taskNameView.getText().toString();
            String newDescription = taskDescriptionView.getText().toString();
            
            taskBuilder.setName(newName);
            taskBuilder.setDescription(newDescription);
            taskBuilder.setStatus(Status.READY_FOR_ESTIMATION);
            taskBuilder.setPriority(taskPriorityView.getPriority());
            
            if (original == null) {
                insertTask();
            } else {
                updateTask();
            }
        }
    }

    private void insertTask() {
        MainTask task = taskBuilder.build();
        task.insert(parentProject, new DefaultGUICallback<MainTask>(this) {
            @Override
            public void interactionDone(MainTask object) {
                TaskEditActivity.this.finish();
            }
        });
    }
    
    private void updateTask() {
        MainTask task = taskBuilder.build();
        task.update(null, new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    TaskEditActivity.this.finish();
                } else {
                    Toast.makeText(TaskEditActivity.this, "Could not update task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean nameIsValid() {
        String name = taskNameView.getText().toString();
        return name != null && name.length() > 0
                && name.length() < 50; // TODO put a meaningful value (cyriaque)
    }
    
    private boolean descriptionIsValid() {
        String description = taskDescriptionView.getText().toString();
        return description != null && description.length() > 0;
    }
}
