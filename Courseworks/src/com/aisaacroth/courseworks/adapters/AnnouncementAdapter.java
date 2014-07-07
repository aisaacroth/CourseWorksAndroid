package com.aisaacroth.courseworks.adapters;

import java.util.ArrayList;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.data.Announcements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * The NotificationAdapter class extends the BaseExpandableListAdapter class in
 * order to run an ExpandableListVIew for the notification center within the
 * "Courseworks" application.
 * 
 * @author: Alexander Roth
 * @date: 2014-06-12
 */
public class AnnouncementAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private ArrayList<Announcements> parentArrayList;

	/**
	 * Instantiates a new notification adapter to be used by the Main activity.
	 * 
	 * @param context
	 *            the context (activity).
	 * @param parent
	 *            the parent objects in the list.
	 */
	public AnnouncementAdapter(Context context, ArrayList<Announcements> parent) {
		parentArrayList = parent;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * Counts the number of group/parent items so the list knows how many times
	 * to call the getGroupView() method. (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		return parentArrayList.size();
	}

	/**
	 * Counts the number of children items so the list knows how many times to
	 * call getChildView() method.
	 * 
	 * @param groupPosition
	 *            the index of the group.
	 * 
	 *            (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return parentArrayList.size();
	}

	/**
	 * Gets the title of each parent/group using the groupPosition index.
	 * 
	 * @param groupPosition
	 *            the index of the group.
	 * 
	 *            (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return parentArrayList.get(groupPosition).getTitle();
	}

	/**
	 * Gets the child at the specified location using the groupPosition index
	 * and the childPosition index.
	 * 
	 * @param groupPosition
	 *            the index of the group.
	 * @param childPosition
	 *            the index of the child of the group.
	 * 
	 *            (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return parentArrayList.get(groupPosition).getBodyText();
	}

	/**
	 * Returns the group's position
	 * 
	 * @param groupPosition
	 *            the index of the group.
	 * 
	 *            (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * Returns the position of the child within a group.
	 * 
	 * @param groupPosition
	 *            the index of the group.
	 * @param childPosition
	 *            the index of the child.
	 * 
	 *            (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**
	 * Sets the textView so that the group becomes visible to the UI.
	 * 
	 * @param groupPosition
	 *            the index of the group.
	 * @param isExpanded
	 *            the visibility of the view.
	 * @param converView
	 *            the view in question.
	 * @param parent
	 *            the parent view.
	 * 
	 *            (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean,
	 *      android.view.View, android.view.ViewGroup)
	 */
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

	/**
	 * Sets the textView to allow a child of a group to become visible to the
	 * UI.
	 * 
	 * @param groupPosition
	 *            the index of the group.
	 * @param childPosition
	 *            the index of the child within the group.
	 * @param isVisible
	 *            the visibility of the view.
	 * @param convertView
	 *            the view in question.
	 * @param parent
	 *            the parent view.
	 * 
	 *            (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean,
	 *      android.view.View, android.view.ViewGroup)
	 */
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

		// Return the entire view.
		return convertView;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/**
	 * The Class ViewHolder.
	 */
	protected class ViewHolder {

		protected int childPosition;
		protected int groupPosition;
		protected Button button;
	}

}