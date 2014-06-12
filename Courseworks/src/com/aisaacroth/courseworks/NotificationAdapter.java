package com.aisaacroth.courseworks;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NotificationAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private ArrayList<Notifications> mParent;

	public NotificationAdapter(Context context, ArrayList<Notifications> parent) {
		mParent = parent;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getGroupCount() {
		return mParent.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mParent.get(groupPosition).getNotifications().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mParent.get(groupPosition).getNotifications();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mParent.get(groupPosition).getNotifications().get(childPosition);
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

		// Return the entire view.
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		holder.childPosition = getChildTypeCount();
		holder.groupPosition = getGroupTypeCount();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_child, parent,
					false);
		}

		TextView textView = (TextView) convertView
				.findViewById(R.id.list_item_child);
		textView.setText(mParent.get(groupPosition).getNotifications().get(childPosition));
		convertView.setTag(holder);
		
		// Return the entire view.
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
