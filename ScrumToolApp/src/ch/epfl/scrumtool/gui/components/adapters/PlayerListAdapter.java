package ch.epfl.scrumtool.gui.components.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Player;

/**
 * @author Cyriaque Brousse
 */
public final class PlayerListAdapter extends DefaultAdapter<Player> {
    private LayoutInflater inflater;

    public PlayerListAdapter(final Activity activity, final List<Player> playerList) {
        super(playerList);
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_player, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.player_row_name);
        TextView role = (TextView) convertView.findViewById(R.id.player_row_role);
        TextView status = (TextView) convertView.findViewById(R.id.player_row_status);

        Player player = getList().get(position);
        
        if (player != null) {
            name.setText(player.getUser().fullname());
            role.setText(player.getRole().toString());
            if (player.isAdmin()) {
                status.setText(R.string.admin);
                status.setVisibility(View.VISIBLE);
            } else if (player.isInvited()) {
                status.setText(R.string.invited);
                status.setVisibility(View.VISIBLE);
            }
        } else {
            name.setText(R.string.no_player);
            role.setText("");
        }
        
        return convertView;
    }
}
