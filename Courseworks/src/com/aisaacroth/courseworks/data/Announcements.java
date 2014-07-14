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

    public String getTitle() {
        return this.title;
    }

    public String getBodyText() {
        return this.bodyText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

}