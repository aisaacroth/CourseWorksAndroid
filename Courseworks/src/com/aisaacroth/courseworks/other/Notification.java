package com.aisaacroth.courseworks.other;

/*******************************************************************************
 * The Notifications class acts as a container for all possible notifications
 * that will be generated and appear within the Main (Homepage) activity.
 * 
 * @author Alexander Roth
 * @date: 2014-06-12
 ******************************************************************************/
public class Notification {

	private String mTitle;
	private String info;

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
	 * @return the information attached to the notification.
	 **************************************************************************/
	public String getInformiation() {
		return this.info;
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
	 * @param info
	 *            the information that will be attached.
	 **************************************************************************/
	public void setInformation(String info) {
		this.info = info;
	}
}
