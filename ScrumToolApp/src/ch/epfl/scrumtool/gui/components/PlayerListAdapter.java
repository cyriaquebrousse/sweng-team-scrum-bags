package ch.epfl.scrumtool.gui.components;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Role;

/**
 * @author Cyriaque Brousse
 */
public final class PlayerListAdapter extends DefaultAdapter<Player> {
    private LayoutInflater inflater;

    public PlayerListAdapter(final Activity activity, final List<Player> playerList) {
        super(playerList);
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setRole(Role newRole, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_player, parent, false);
        }
        TextView role = (TextView) convertView.findViewById(R.id.player_row_role);
        role.setText(newRole.toString());
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_player, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.player_row_name);
        TextView role = (TextView) convertView.findViewById(R.id.player_row_role);

        Player player = getList().get(position);
        
        if (player != null) {
            name.setText(player.getUser().fullname());
            role.setText(player.getRole().toString());
        } else {
            name.setText(R.string.no_player);
            role.setText("");
        }
        
        return convertView;
    }
}
