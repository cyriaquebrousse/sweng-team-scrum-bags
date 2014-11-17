package ch.epfl.scrumtool.gui;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu.OnMenuItemClickListener;
import ch.epfl.scrumtool.R;

/**
 * Abstracts the concept of our menus, which are similar throughout the
 * application
 * 
 * This class 
 * 
 * @author zenhaeus
 * @param <E>
 *            type of the entity we are mainly dealing with in this activity
 *            (e.g. Project)
 */
public abstract class BaseOverviewMenuActivity<E> extends BaseMenuActivity implements OnMenuItemClickListener {
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
    
    public void setOverviewElement(E overviewElement) {
        this.overviewElement = overviewElement;
    }
    
    public E getOverviewElement() {
        return this.overviewElement;
    }
    
    /**
     * Opens an activity to edit an element of type <E>
     * 
     * @param optionToEdit
     *            the element to edit
     */
    abstract void openEditElementActivity(E optionalElementToEdit);
    
}
