package ch.epfl.scrumtool.gui.components;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Sprint;

/**
 * @author AlexVeuthey
 */
public class SprintListAdapter extends DefaultAdapter<Sprint> {
    private LayoutInflater inflater;

    public SprintListAdapter(final Activity activity, List<Sprint> list) {
        super(list);
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_player, parent, false);
        }
        
        TextView name = (TextView) convertView.findViewById(R.id.sprint_row_name);
        TextView date = (TextView) convertView.findViewById(R.id.sprint_row_date);
        
        Sprint sprint = getList().get(position);
        name.setText(sprint.getTitle());
        date.setText(date.getText().toString());
        
        return convertView;
    }

}
