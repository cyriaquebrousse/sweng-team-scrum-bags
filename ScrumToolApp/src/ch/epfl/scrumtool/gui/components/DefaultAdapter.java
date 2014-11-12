package ch.epfl.scrumtool.gui.components;

import java.util.Collection;
import java.util.List;

import android.widget.BaseAdapter;

/**
 * @author Cyriaque Brousse
 * @param <E> entity type
 */
public abstract class DefaultAdapter<E> extends BaseAdapter {
    
    private List<E> list;
    
    public DefaultAdapter(final List<E> list) {
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
    
    public final List<E> getList() {
        return list;
    }
    
    public final void add(E elem) {
        list.add(elem);
        notifyDataSetChanged();
    }
    
    public final void addAll(Collection<E> elems) {
        list.addAll(elems);
        notifyDataSetChanged();
    }
    
    public final void remove(E elem) {
        list.remove(elem);
        notifyDataSetChanged();
    }
    
    public final void replace(E oldElem, E newElem) {
        list.set(list.indexOf(oldElem), newElem);
        notifyDataSetChanged();
    }
    
    public final void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
