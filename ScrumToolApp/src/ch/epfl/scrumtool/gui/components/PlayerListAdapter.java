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
import ch.epfl.scrumtool.entity.Player;

/**
 * @author Cyriaque Brousse
 */
public final class PlayerListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Player> playerList;

    public PlayerListAdapter(final Activity activity, List<Player> playerList) {
        this.playerList = playerList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Player getItem(int position) {
        return playerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_player, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.player_row_name);
        TextView role = (TextView) convertView.findViewById(R.id.player_row_role);

        Player player = playerList.get(position);
        name.setText(player.getUser().getName());
        role.setText(player.getRole().toString());
        
        return convertView;
    }
}
