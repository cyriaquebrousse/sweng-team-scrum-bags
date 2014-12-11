package ch.epfl.scrumtool.gui.components.adapters;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.ScrumToolActivity;
import ch.epfl.scrumtool.util.gui.EstimationFormating;

/**
 * @author ketsio
 */
public final class DashboardIssueListAdapter extends
        DefaultAdapter<TaskIssueProject> {
    private ScrumToolActivity activity;
    private LayoutInflater inflater;

    public DashboardIssueListAdapter(final ScrumToolActivity activity,
            final List<TaskIssueProject> containerList) {
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

        TextView estimation = (TextView) convertView
                .findViewById(R.id.issue_row_quantity);
        TextView unit = (TextView) convertView
                .findViewById(R.id.issue_row_unit);
        TextView name = (TextView) convertView
                .findViewById(R.id.issue_row_name);
        TextView task = (TextView) convertView
                .findViewById(R.id.issue_row_desc_task);
        TextView deadline = (TextView) convertView
                .findViewById(R.id.issue_row_assignee);
        final View divider = (View) convertView.findViewById(R.id.issue_row_divider);
        CheckBox checkbox = (CheckBox) convertView
                .findViewById(R.id.issue_row_checkbox);

        TaskIssueProject container = getList().get(position);
        final Issue issue = container.getIssue();
        if (issue == null) {
            name.setText("No issue selected");
            divider.setVisibility(View.GONE);
            checkbox.setVisibility(View.GONE);
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
            deadline.setText(optionSprint != null ? "- "
                    + sdf.format(optionSprint.getDeadline())
                    : "- No sprint assigned");
            float estim = issue.getEstimatedTime();
            estimation.setText(issue.getEstimatedTime() == 0 ? "-"
                    : EstimationFormating.estimationAsHourFormat(estim));
            if (1.0 >= estim) {
                unit.setText(activity.getResources().getString(
                        R.string.project_single_unit));
            } else {
                unit.setText(activity.getResources().getString(
                        R.string.project_default_unit));
            }
            divider.setBackgroundColor(activity.getResources().getColor(
                    issue.getStatus().getColorRef()));
            checkbox.setChecked(issue.getStatus() == Status.FINISHED);
            checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                        boolean isChecked) {
                    final boolean done = isChecked;
                    issue.markAsDone(done, new Callback<Void>() {
                        @Override
                        public void interactionDone(Void v) {
                            Status status = done ? Status.FINISHED : issue.getStatus();
                            divider.setBackgroundColor(activity.getResources()
                                    .getColor(status.getColorRef()));
                        }

                        @Override
                        public void failure(String errorMessage) {
                            Toast.makeText(activity,
                                    "Could not mark as done/undone",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        return convertView;
    }

}
