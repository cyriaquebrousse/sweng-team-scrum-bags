package ch.epfl.scrumtool.gui;

import android.view.Menu;
import android.view.MenuItem;
import ch.epfl.scrumtool.R;

/**
 * This class is an extension of our {@link BaseMenuActivity} that adds
 * further functionalities to the action bar which are needed in activities
 * that display overviews of entities.
 * 
 * @author zenhaeus
 * @param <E>
 *            type of the entity that is displayed in the child activitie's overview
 */
public abstract class BaseOverviewMenuActivity<E> extends BaseMenuActivity {
    private E overviewElement = null;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, Menu.FIRST, Menu.NONE, "Edit")
            .setIcon(R.drawable.ic_menu_edit)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        
        switch (item.getItemId()) {
            case Menu.FIRST:
                openEditElementActivity(overviewElement);
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Sets object that should be edited when clicking 
     * the edit menu button
     * @param overviewElement
     */
    public void setOverviewElement(E overviewElement) {
        this.overviewElement = overviewElement;
    }
    
    /**
     * Opens an activity to edit an element of type <E>
     * 
     * @param optionToEdit
     *            the element to edit
     */
    abstract void openEditElementActivity(E optionalElementToEdit);
    
}
