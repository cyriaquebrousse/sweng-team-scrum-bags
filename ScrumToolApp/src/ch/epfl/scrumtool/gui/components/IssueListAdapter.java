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
import ch.epfl.scrumtool.entity.IssueInterface;
import ch.epfl.scrumtool.gui.components.widgets.Stamp;

/**
 * @author cyriaquebrousse
 */
public final class IssueListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<IssueInterface> issuesList;

    public IssueListAdapter(final Activity activity, List<IssueInterface> issuesList) {
        this.activity = activity;
        this.issuesList = issuesList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        return issuesList.size();
    }

    @Override
    public Object getItem(int position) {
        return issuesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_issue, null);
        }
        
        Stamp estimation = (Stamp) convertView.findViewById(R.id.issue_row_estimation);
        TextView name = (TextView) convertView.findViewById(R.id.issue_row_name);
        TextView assignee = (TextView) convertView.findViewById(R.id.issue_row_assignee);
        
        IssueInterface issue = issuesList.get(position);
        name.setText(issue.getName());
        assignee.setText(issue.getAssignedPlayer().getAccount().getName());
        estimation.setQuantity(Float.toString(issue.getEstimation()));
        estimation.setUnit(activity.getResources().getString(R.string.project_default_unit));
        estimation.setColor(activity.getResources().getColor(issue.getStatus().getColorRef()));
        
        return convertView;
    }

}
