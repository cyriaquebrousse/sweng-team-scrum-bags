package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author LeoWirz
 * 
 */
public class UserListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ListView listview = (ListView) findViewById(R.id.userList);

        // TODO Get users of project
        String[] names = null;

        names = new String[] {"Loris", "Joey", "Cyriaque", "Alex ", "Arno", "Vincent", "Sylvain", "Leo"};

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, names);
        listview.setAdapter(adapter);

    }

    /**
     * 
     * @author LeoWirz
     * 
     */
    private class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView nameTextView = (TextView) rowView
                    .findViewById(R.id.userName);
            TextView roleTextView = (TextView) rowView
                    .findViewById(R.id.userRole);
            ImageView imageView = (ImageView) rowView
                    .findViewById(R.id.userPicture);
            nameTextView.setText(values[position]);
            roleTextView.setText("developer");
            imageView.setImageResource(R.drawable.ic_launcher);
            return rowView;
        }
    }

}
