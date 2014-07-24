package com.aisaacroth.courseworks.adapters;

import java.util.ArrayList;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.structures.Announcement;

import android.content.Context;
import android.view.*;
import android.widget.*;

/**
 * Extends the BaseExpandableListAdapter class in order to run an
 * ExpandableListVIew for the notification center within the "Courseworks"
 * application.
 * 
 * @author Alexander Roth
 * @date 2014-06-12
 */
public class AnnouncementAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private ArrayList<Announcement> parentArrayList;

    public AnnouncementAdapter(Context context, ArrayList<Announcement> parent) {
        parentArrayList = parent;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return parentArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parentArrayList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentArrayList.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentArrayList.get(groupPosition).getBodyText();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        holder.groupPosition = getGroupCount();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_parent, parent,
                    false);
        }

        TextView textView = (TextView) convertView
                .findViewById(R.id.list_item_text_view);
        textView.setText(getGroup(groupPosition).toString());

        convertView.setTag(holder);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isVisible, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        holder.childPosition = getChildTypeCount();
        holder.groupPosition = getGroupTypeCount();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_child, parent,
                    false);
        }

        TextView textView = (TextView) convertView
                .findViewById(R.id.list_item_child);
        textView.setText(parentArrayList.get(groupPosition).getBodyText());
        convertView.setTag(holder);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    protected class ViewHolder {
        protected int childPosition;
        protected int groupPosition;
        protected Button button;
    }

}