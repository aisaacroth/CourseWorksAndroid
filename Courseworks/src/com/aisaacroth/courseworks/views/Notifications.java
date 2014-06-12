package com.aisaacroth.courseworks.views;

import java.util.ArrayList;

/*******************************************************************************
 * The Notifications class acts as a container for all possible notifications
 * that will be generated and appear within the Main (Homepage) activity.
 * 
 * @author Alexander Roth
 * @date: 2014-06-12
 ******************************************************************************/
public class Notifications {

	private String mTitle;
	private ArrayList<String> mArrayChildren;

	/***************************************************************************
	 * Gets the title of the Notification object.
	 * 
	 * @return the title
	 **************************************************************************/
	public String getTitle() {
		return this.mTitle;
	}

	/***************************************************************************
	 * Gets the information attached to the Notification object.
	 * 
	 * @return the array of notifications
	 **************************************************************************/
	public ArrayList<String> getNotifications() {
		return this.mArrayChildren;
	}

	/***************************************************************************
	 * Sets the title of the Notification object.
	 * 
	 * @param title
	 *            the new title
	 **************************************************************************/
	public void setTitle(String title) {
		this.mTitle = title;
	}

	/***************************************************************************
	 * Sets the Notification object with strings that contain necessary
	 * information.
	 * 
	 * @param mArrayChildren
	 *            the new array children.
	 **************************************************************************/
	public void setNotifications(ArrayList<String> mArrayChildren) {
		this.mArrayChildren = mArrayChildren;
	}
}
