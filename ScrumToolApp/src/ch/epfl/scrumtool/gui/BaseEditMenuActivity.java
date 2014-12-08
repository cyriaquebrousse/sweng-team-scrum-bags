package ch.epfl.scrumtool.gui;

import android.view.Menu;
import android.view.MenuItem;
import ch.epfl.scrumtool.R;

/**
 * This class is an extension of our {@link BaseMenuActivity} that adds
 * further functionalities to the action bar which are needed in activities
 * which edit or create our entities.
 * 
 * @author zenhaeus
 *
 */
public abstract class BaseEditMenuActivity extends ScrumToolActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, Menu.FIRST, Menu.NONE, "save")
            .setIcon(R.drawable.ic_cab_done_holo_light)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                saveElement();
                return true;
            default:
                return false;
        }
    }

    protected abstract void saveElement();
}
