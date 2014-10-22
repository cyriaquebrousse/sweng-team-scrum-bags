package ch.epfl.scrumtool.gui.components;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.ProjectInterface;

/**
 * @author cyriaquebrousse
 */
public class ProjectListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProjectInterface> projectsList;

    public ProjectListAdapter(final Activity activity, List<ProjectInterface> projectsList) {
        this.activity = activity;
        this.projectsList = projectsList;
    }

    @Override
    public int getCount() {
        return projectsList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_project, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.project_row_name);
        TextView desc = (TextView) convertView.findViewById(R.id.project_row_description);
        Sticker newElemCount = (Sticker) convertView.findViewById(R.id.project_row_newElemCount);

        ProjectInterface project = projectsList.get(position);
        name.setText(project.getName());
        desc.setText(project.getDescription());
        newElemCount.setText(Integer.toString(project.getChangesCount(null)));

        if (project.getChangesCount(null) == 0) {
            newElemCount.setVisibility(View.INVISIBLE);
        } else {
            newElemCount.setVisibility(View.VISIBLE);
            newElemCount.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
        }

        return convertView;
    }
}
