package ch.epfl.scrumtool.gui;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;
import ch.epfl.scrumtool.gui.util.InputVerifiers;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class TaskEditActivity extends Activity {
    
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
    }
    
    public void saveTaskChanges(View view) {
        InputVerifiers.updateTextViewAfterValidityCheck(taskNameView, nameIsValid(), getResources());
        InputVerifiers.updateTextViewAfterValidityCheck(taskDescriptionView, descriptionIsValid(), getResources());
        
        if (nameIsValid() && descriptionIsValid()) {
            String newName = taskNameView.getText().toString();
            String newDescription = taskDescriptionView.getText().toString();
            
            taskBuilder.setName(newName);
            taskBuilder.setDescription(newDescription);
            taskBuilder.setKey("random task id "+ new Random().nextInt()); // FIXME task id
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
        
        DefaultGUICallback<MainTask> taskInserted = new DefaultGUICallback<MainTask>(this) {

            @Override
            public void interactionDone(MainTask object) {
                TaskEditActivity.this.finish();
            }
            
        };
        Client.getScrumClient().insertMainTask(task, parentProject, taskInserted);
    }
    
    private void updateTask() {
        MainTask task = taskBuilder.build();
        
        DefaultGUICallback<Boolean> updatedTask = new DefaultGUICallback<Boolean>(this) {
            
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    TaskEditActivity.this.finish();
                } else {
                    Toast.makeText(TaskEditActivity.this, "Could not update task", Toast.LENGTH_SHORT).show();
                }
            }
        };
        
        Client.getScrumClient().updateMainTask(task, original, updatedTask);
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
