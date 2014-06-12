package com.aisaacroth.courseworks.views;

import java.util.ArrayList;

/*******************************************************************************
 * The Class Notifications.
 ******************************************************************************/
public class Notifications {
	
	private String name;
	private ArrayList<String> mArrayChildren;
	
	/***************************************************************************
	 * Gets the name.
	 *
	 * @return the name
	 **************************************************************************/
	public String getName() {
		return this.name;
	}
	
	/***************************************************************************
	 * Gets the items.
	 *
	 * @return the items
	 **************************************************************************/
	public ArrayList<String> getNotifications() {
		return this.mArrayChildren;
	}
	
	/***************************************************************************
	 * Sets the name.
	 *
	 * @param name the new name
	 **************************************************************************/
	public void setName(String name) {
		this.name = name;
	}
	
	/***************************************************************************
	 * Sets the items.
	 *
	 * @param items the new items
	 **************************************************************************/
	public void setNotifications(ArrayList<String> mArrayChildren) {
		this.mArrayChildren = mArrayChildren;
	}
}
