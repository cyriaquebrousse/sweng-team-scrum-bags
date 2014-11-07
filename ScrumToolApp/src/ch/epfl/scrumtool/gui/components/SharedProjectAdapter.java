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
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author ketsio
 */
public final class SharedProjectAdapter extends BaseAdapter {
    
    @SuppressWarnings("unused")
    private Activity activity;
    
    private LayoutInflater inflater;
    private List<Project> sharedProjects;
    private User user;

    public SharedProjectAdapter(final Activity activity, List<Project> sharedProjects, User user) {
        this.activity = activity;
        this.sharedProjects = sharedProjects;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.user = user;
    }

    @Override
    public int getCount() {
        return sharedProjects.size();
    }

    @Override
    public Object getItem(int position) {
        return sharedProjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_shared_project, null);
        }

        TextView projectName = (TextView) convertView.findViewById(R.id.shared_project_row_name);
        TextView projectRole = (TextView) convertView.findViewById(R.id.shared_project_row_role);

        Project project = sharedProjects.get(position);
        projectName.setText(project.getName());
        try {
            projectRole.setText(project.getRoleFor(user).toString());
        } catch (NotAPlayerOfThisProjectException e) {
            projectRole.setText("Could not retrieve the role");
        }

        return convertView;
    }
}
