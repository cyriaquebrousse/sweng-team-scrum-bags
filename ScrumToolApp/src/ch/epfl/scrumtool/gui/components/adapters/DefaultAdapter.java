package ch.epfl.scrumtool.gui.components.adapters;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.widget.BaseAdapter;

/**
 * @author Cyriaque Brousse
 * @param <E> entity type
 */
public abstract class DefaultAdapter<E extends Comparable<E>> extends BaseAdapter {
    
    private List<E> list;
    
    public DefaultAdapter(final List<E> list) {
        Collections.sort(list);
        this.list = list;
    }
    
    @Override
    public final int getCount() {
        return list.size();
    }

    @Override
    public final E getItem(int position) {
        return list.get(position);
    }

    @Override
    public final long getItemId(int position) {
        return position;
    }
    
    /**
     * @return the adapter's internal list
     */
    public final List<E> getList() {
        return list;
    }
    
    /**
     * Adds an element to the adapter's internal list
     * 
     * @param elem
     *            the element to add
     */
    public final void add(E elem) {
        list.add(elem);
        Collections.sort(list);
        notifyDataSetChanged();
    }
    
    /**
     * Add a collection of elements to the adapter's internal list
     * 
     * @param elems
     *            the collection of elements to add
     */
    public final void addAll(Collection<E> elems) {
        list.addAll(elems);
        Collections.sort(list);
        notifyDataSetChanged();
    }
    
    /**
     * Removes an element from the adapter's internal list
     * 
     * @param elem
     *            the element to remove
     */
    public final void remove(E elem) {
        list.remove(elem);
        notifyDataSetChanged();
    }
    
    /**
     * Replaces the old element by the new element in the adapter's internal
     * list
     * 
     * @param oldElem
     *            the element to be replaced
     * @param newElem
     *            the new element
     */
    public final void replace(E oldElem, E newElem) {
        list.set(list.indexOf(oldElem), newElem);
        notifyDataSetChanged();
    }
    
    /**
     * Clears the adapter's internal list
     */
    public final void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
