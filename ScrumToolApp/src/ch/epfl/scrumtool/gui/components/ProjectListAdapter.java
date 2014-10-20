package ch.epfl.scrumtool.gui.components;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.scrumtool.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ProjectListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<DummyProject> projectsList;
	
	public ProjectListAdapter(final Activity activity, List<DummyProject> projectsList) {
		this.activity = activity;
		//this.projectsList = new ArrayList<>(projectsList);
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
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.project_list_row, null);
		}
		
		TextView name = (TextView) convertView.findViewById(R.id.project_name);
		TextView desc = (TextView) convertView.findViewById(R.id.project_desc);
		TextView newElemCount = (TextView) convertView.findViewById(R.id.project_newElemCount);
		
		DummyProject project = projectsList.get(position);
		name.setText(project.getName());
		desc.setText(project.getDesc());
		newElemCount.setText(Integer.toString(project.getChangesCount()));
		
		if (project.getChangesCount() == 0) {
			newElemCount.setVisibility(View.INVISIBLE);
		} else {
			newElemCount.setVisibility(View.VISIBLE);
			newElemCount.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
		}
		
		return convertView;
	}
}
