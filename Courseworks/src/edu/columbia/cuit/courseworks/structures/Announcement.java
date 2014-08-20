package edu.columbia.cuit.courseworks.structures;

import java.io.Serializable;

/**
 * Acts as a container for all possible announcements that will be generated and
 * appear within the Main (Homepage) activity.
 * 
 * @author Alexander Roth
 * @date 2014-06-12
 */


@SuppressWarnings("serial")
public class Announcement implements Serializable {

    private String title;
    private String bodyText;
    private String classId;
    private String postedDate;
    private String dueDate;
    private String professorName;
    private String id;

    public Announcement() {
        this.title = null;
        this.bodyText = null;
        this.classId = null;
        this.postedDate = null;
        this.dueDate = null;
        this.professorName = null;
        this.id = null;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBodyText() {
        return this.bodyText;
    }

    public String getClassId() {
        return this.classId;
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

    public String getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public void setId(String id) {
        this.id = id;
    }
}