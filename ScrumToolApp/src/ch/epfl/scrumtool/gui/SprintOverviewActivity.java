package ch.epfl.scrumtool.gui;

import java.util.Calendar;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.R.id;
import ch.epfl.scrumtool.R.layout;
import ch.epfl.scrumtool.entity.Sprint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author AlexVeuthey
 *
 */
public class SprintOverviewActivity extends BaseOverviewMenuActivity {

    private Sprint sprint;
    
    private TextView name;
    private TextView deadline;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_overview);
        
        initActivity();
        initViews();
        
        // TODO sprint.loadIssues(callback...)
    }
    
    private void initViews() {
        name = (TextView) findViewById(R.id.sprint_overview_name);
        deadline = (TextView) findViewById(R.id.sprint_overview_deadline);
        
        name.setText(sprint.getTitle());
        deadline.setText(getDeadline(sprint.getDeadline()));
    }
    
    // TODO delete sprint via the toolbar
    
    private void initActivity() {
        sprint = (Sprint) getIntent().getSerializableExtra(Sprint.SERIALIZABLE_NAME);
        if (sprint == null) {
            throw new NullPointerException("Clicked Sprint cannot be null");
        }
    }
    
    private String getDeadline(long deadline) {
        Calendar deadlineAsCal = Calendar.getInstance();
        deadlineAsCal.setTimeInMillis(deadline);
        return "" + deadlineAsCal.get(Calendar.DAY_OF_MONTH) + "/" 
            + deadlineAsCal.get(Calendar.MONTH) + "/" + deadlineAsCal.get(Calendar.YEAR);
    }

    @Override
    void openEditElementActivity(Object optionalElementToEdit) {
        // TODO edit sprint via the toolbar
    }
}
