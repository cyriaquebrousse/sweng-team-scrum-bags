package ch.epfl.scrumtool.gui.components;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author ketsio
 */
public final class SharedProjectAdapter extends DefaultAdapter<Project> {
    
    private LayoutInflater inflater;
    private User user;

    public SharedProjectAdapter(final Activity activity, final List<Project> sharedProjects, final User user) {
        super(sharedProjects);
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.user = user;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_shared_project, parent, false);
        }

        TextView projectName = (TextView) convertView.findViewById(R.id.shared_project_row_name);
        TextView projectRole = (TextView) convertView.findViewById(R.id.shared_project_row_role);

        Project project = getList().get(position);
        projectName.setText(project.getName());

        return convertView;
    }
}
