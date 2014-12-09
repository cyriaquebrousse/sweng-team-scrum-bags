package ch.epfl.scrumtool.gui.components.adapters;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.util.gui.EstimationFormating;

/**
 * @author ketsio
 */
public final class DashboardIssueListAdapter extends DefaultAdapter<TaskIssueProject> {
    private Activity activity;
    private LayoutInflater inflater;

    public DashboardIssueListAdapter(final Activity activity, final List<TaskIssueProject> containerList) {
        super(containerList);
        this.activity = activity;
        this.inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_issue, parent,
                    false);
        }

        TextView estimation = (TextView) convertView.findViewById(R.id.issue_row_quantity);
        TextView unit = (TextView) convertView.findViewById(R.id.issue_row_unit);
        TextView name = (TextView) convertView.findViewById(R.id.issue_row_name);
        TextView task = (TextView) convertView.findViewById(R.id.issue_row_desc_task);
        TextView deadline = (TextView) convertView.findViewById(R.id.issue_row_assignee);
        View divider = (View) convertView.findViewById(R.id.issue_row_divider);

        TaskIssueProject container = getList().get(position);
        Issue issue = container.getIssue();
        if (issue == null) {
            name.setText("No issue selected");
            divider.setVisibility(View.GONE);
            estimation.setVisibility(View.GONE);
            task.setVisibility(View.GONE);
            deadline.setVisibility(View.GONE);
            unit.setVisibility(View.GONE);
        } else {
            name.setText(issue.getName());
            task.setText("task : " + container.getMainTask().getName());
            Sprint optionSprint = issue.getSprint();
            SimpleDateFormat sdf = new SimpleDateFormat(activity.getResources()
                    .getString(R.string.format_date), Locale.ENGLISH);
            deadline.setText(optionSprint != null ? "- " + sdf.format(optionSprint
                    .getDeadline()) : "- No sprint assigned");
            float estim = issue.getEstimatedTime();
            estimation.setText(issue.getEstimatedTime() == 0 ? "-" 
                    : EstimationFormating.estimationAsHourFormat(estim));
            if (1.0 >= estim) {
                unit.setText(activity.getResources().getString(R.string.project_single_unit));
            } else {
                unit.setText(activity.getResources().getString(
                        R.string.project_default_unit));
            }
            divider.setBackgroundColor(activity.getResources().getColor(
                    issue.getStatus().getColorRef()));
        }

        return convertView;
    }

}
