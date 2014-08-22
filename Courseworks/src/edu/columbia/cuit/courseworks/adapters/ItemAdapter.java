package edu.columbia.cuit.courseworks.adapters;

import java.util.List;

import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;

public abstract class ItemAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> itemList;
    
    public ItemAdapter(Context context, List<T> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    
    public abstract View getView(int position, View convertView, ViewGroup parent);
    
    protected void setListItemFormat(View view, T item) {
        setListItemViews(view);
        setTextInViews(item);
        setFontsForText();
    }
    
    protected abstract void setListItemViews(View view);
    protected abstract void setTextInViews(T item);
    protected abstract void setFontsForText();
    
    public int getCount() {
        return itemList.size();
    }
    
    public Object getItem(int position) {
        return itemList.get(position);
    }
    
    public long getItemId(int position) {
        return position;
    }
}
