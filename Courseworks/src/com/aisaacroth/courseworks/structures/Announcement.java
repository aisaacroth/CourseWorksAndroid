package com.aisaacroth.courseworks.structures;

/**
 * Acts as a container for all possible announcements that will be generated and
 * appear within the Main (Homepage) activity.
 * 
 * @author Alexander Roth
 * @date 2014-06-12
 */
public class Announcement {

    private String title;
    private String bodyText;
    private String postedDate;
    private String dueDate;
    private String professorName;

    public Announcement() {
        this.title = null;
        this.bodyText = null;
        this.postedDate = null;
        this.dueDate = null;
        this.professorName = null;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBodyText() {
        return this.bodyText;
    }

    public String getPostedDate() {
        return this.postedDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public String getProfessorName() {
        return this.professorName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

}