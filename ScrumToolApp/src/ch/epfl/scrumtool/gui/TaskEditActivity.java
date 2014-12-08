package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.verifyNameIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.verifyDescriptionIsValid;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;
import ch.epfl.scrumtool.util.gui.Dialogs;
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
        throwIfNull("Parent project cannot be null", parentProject);
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
        
        Resources resources = getResources();
        boolean nameIsValid = verifyNameIsValid(taskNameView, resources);
        boolean descriptionIsValid = verifyDescriptionIsValid(taskDescriptionView, resources);
        
        if (nameIsValid && descriptionIsValid) {
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
        final MainTask task = taskBuilder.build();
        final Button next = (Button) findViewById(R.id.task_edit_button_next);
        task.insert(parentProject, new DefaultGUICallback<MainTask>(this, next) {
            @Override
            public void interactionDone(MainTask object) {
                TaskEditActivity.this.finish();
            }
        });
    }
    
    private void updateTask() {
        final MainTask task = taskBuilder.build();
        final Button next = (Button) findViewById(R.id.task_edit_button_next);
        task.update(original, new DefaultGUICallback<Void>(this, next) {
            @Override
            public void interactionDone(Void v) {
                TaskEditActivity.this.finish();
            }
        });
    }
}
