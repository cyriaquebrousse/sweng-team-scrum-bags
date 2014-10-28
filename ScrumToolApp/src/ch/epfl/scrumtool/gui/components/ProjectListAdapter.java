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
import ch.epfl.scrumtool.entity.Entity;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.widgets.Sticker;

/**
 * @author Cyriaque Brousse
 */
public final class ProjectListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Project> projectsList;

    public ProjectListAdapter(final Activity activity, List<Project> projectsList) {
        this.activity = activity;
        this.projectsList = projectsList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_project, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.project_row_name);
        TextView desc = (TextView) convertView.findViewById(R.id.project_row_description);
        Sticker newElemCount = (Sticker) convertView.findViewById(R.id.project_row_newElemCount);

        Project project = projectsList.get(position);
        name.setText(project.getName());
        desc.setText(project.getDescription());
        
        // Sticker
        int changesCount = project.getChangesCount(Entity.CONNECTED_USER);
        newElemCount.setText(Integer.toString(changesCount));

        if (changesCount == 0) {
            newElemCount.setVisibility(View.INVISIBLE);
        } else {
            newElemCount.setVisibility(View.VISIBLE);
            newElemCount.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
        }

        return convertView;
    }
}
