package ch.epfl.scrumtool.gui.components;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;

/**
 * @author Cyriaque Brousse
 */
public final class ProjectListAdapter extends DefaultAdapter<Project> {

    private LayoutInflater inflater;
    
    public ProjectListAdapter(final Activity activity, final List<Project> projectList) {
        super(projectList);
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_project, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.project_row_name);
        TextView desc = (TextView) convertView.findViewById(R.id.project_row_description);
        
        final ViewGroup headerBlock = (ViewGroup) convertView.findViewById(R.id.project_row_header_block);
        final ViewGroup detailsBlock = (ViewGroup) convertView.findViewById(R.id.project_row_expanded_block);
        
        ImageButton backlogButton = (ImageButton) convertView.findViewById(R.id.project_row_backlog);
        ImageButton sprintsButton = (ImageButton) convertView.findViewById(R.id.project_row_sprints);
        ImageButton playersButton = (ImageButton) convertView.findViewById(R.id.project_row_players);

        Project project = getList().get(position);
        name.setText(project.getName());
        desc.setText(project.getDescription());
        backlogButton.setFocusable(false);
        sprintsButton.setFocusable(false);
        playersButton.setFocusable(false);
        
        detailsBlock.setVisibility(View.GONE);
        headerBlock.setLongClickable(true);
        headerBlock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsBlock.setVisibility(detailsBlock.isShown() ? View.GONE : View.VISIBLE);
            }
        });

        return convertView;
    }
}
