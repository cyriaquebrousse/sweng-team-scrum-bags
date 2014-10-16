package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.epfl.scrumtool.R;

/**
 * @author cyriaquebrousse
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openBacklog(View view) {
        Intent openBacklogIntent = new Intent(this, BacklogActivity.class);
        startActivity(openBacklogIntent);
    }
    
    public void openLogin(View view) {
        Intent openLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(openLoginIntent);
    }
}
