package ch.epfl.scrumtool.gui.components;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.TaskInterface;
import ch.epfl.scrumtool.gui.components.widgets.Sticker;

/**
 * @author Cyriaque Brousse
 */
public final class TaskListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<TaskInterface> taskList;

    public TaskListAdapter(final Activity activity, List<TaskInterface> taskList) {
        this.activity = activity;
        this.taskList = taskList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_task, null);
        }
        
        // Get the views
        Sticker priority = (Sticker) convertView.findViewById(R.id.task_row_priority_sticker);
        TextView name = (TextView) convertView.findViewById(R.id.task_row_name);
        TextView description = (TextView) convertView.findViewById(R.id.task_row_desc);
        TextView status = (TextView) convertView.findViewById(R.id.task_row_status);
        View percentDoneBackgrd = (View) convertView.findViewById(R.id.task_row_percent_backgrd);
        
        // Set views properties
        TaskInterface task = taskList.get(position);
        priority.setStickerText(task.getPriority());
        priority.setColor(activity.getResources().getColor(task.getPriority().getColorRef()));
        name.setText(task.getName());
        description.setText(task.getDescription());
        status.setText(task.getStatus().toString());
        
        // Set background width according to # issues finished
        DisplayMetrics metrics = convertView.getContext().getResources().getDisplayMetrics();
        int screenWidthPixel = metrics.widthPixels;
        ViewGroup.LayoutParams params = percentDoneBackgrd.getLayoutParams();
        params.width = (screenWidthPixel * task.getIssuesFinishedCount()) / task.getIssues().size();
        percentDoneBackgrd.setLayoutParams(params);
        
        return convertView;
    }
    
}
