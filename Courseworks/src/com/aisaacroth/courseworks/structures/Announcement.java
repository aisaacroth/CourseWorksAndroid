package com.aisaacroth.courseworks.structures;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Acts as a container for all possible announcements that will be generated and
 * appear within the Main (Homepage) activity.
 * 
 * @author Alexander Roth
 * @date 2014-06-12
 */
public class Announcement implements Parcelable {

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

    public Announcement(Parcel in) {
        String[] data = new String[7];

        this.title = data[0];
        this.bodyText = data[1];
        this.classId = data[2];
        this.postedDate = data[3];
        this.dueDate = data[4];
        this.professorName = data[5];
        this.id = data[6];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(transformToStringArray());
    }

    private String[] transformToStringArray() {
        return new String[] { this.title, this.bodyText, this.classId,
                this.postedDate, this.dueDate, this.professorName, this.id };
    }

    public static final Parcelable.Creator<Announcement> CREATOR = new Creator<Announcement>() {

        @Override
        public Announcement[] newArray(int size) {
            return new Announcement[size];
        }

        @Override
        public Announcement createFromParcel(Parcel source) {
            return new Announcement(source);
        }
    };

}