package ch.epfl.scrumtool.gui.components;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;

/**
 * @author Cyriaque Brousse
 */
public final class TaskListAdapter extends DefaultAdapter<MainTask>  {
    
    private static final int ONE_HUNDRED = 100;
    
    private Activity activity;
    private LayoutInflater inflater;

    public TaskListAdapter(final Activity activity, final List<MainTask> taskList) {
        super(taskList);
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_task, parent, false);
        }
        
        // Get the views
        PrioritySticker priorityView = (PrioritySticker) convertView.findViewById(R.id.task_row_priority_sticker);
        TextView name = (TextView) convertView.findViewById(R.id.task_row_name);
        TextView description = (TextView) convertView.findViewById(R.id.task_row_desc);
        TextView status = (TextView) convertView.findViewById(R.id.task_row_status);
        View percentDoneBackgrd = (View) convertView.findViewById(R.id.task_row_percent_backgrd);
        
        // Set views properties
        final MainTask task = getList().get(position);
        //double percentageDone = (double) task.getIssuesFinishedCount() / (double) issues.size();//FIXME
        double percentageDone = 0.0;
        
        priorityView.setPriority(task.getPriority());
        priorityView.setColor(activity.getResources().getColor(task.getPriority().getColorRef()));
        name.setText(task.getName());
        description.setText(task.getDescription());
        status.setText((int) (percentageDone * ONE_HUNDRED) + " %  -  "+ task.getStatus().toString());
        
        // Set background width according to # issues finished
        DisplayMetrics metrics = convertView.getContext().getResources().getDisplayMetrics();
        int screenWidthPixel = metrics.widthPixels;
        ViewGroup.LayoutParams params = percentDoneBackgrd.getLayoutParams();
        params.width = (int) (screenWidthPixel * percentageDone);
        percentDoneBackgrd.setLayoutParams(params);
        
        return convertView;
    }
}
