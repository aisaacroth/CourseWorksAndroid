package com.aisaacroth.courseworks.data;

/**
 * Acts as a container for all possible announcements that will be generated and
 * appear within the Main (Homepage) activity.
 * 
 * @author Alexander Roth
 * @date 2014-06-12
 */
public class Announcements {

    private String title;
    private String bodyText;

    /**
     * Gets the title of the Announcement object.
     * 
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the information attached to the Announcement object.
     * 
     * @return the information attached to the announcement.
     */
    public String getBodyText() {
        return this.bodyText;
    }

    /**
     * Sets the title of the Notification object.
     * 
     * @param title
     *            the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the Notification object with strings that contain necessary
     * information.
     * 
     * @param bodyText
     *            the body text that will be attached to the announcement.
     */
    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

}