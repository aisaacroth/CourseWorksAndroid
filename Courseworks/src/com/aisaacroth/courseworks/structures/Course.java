package com.aisaacroth.courseworks.structures;

/**
 * Serves as a container for a course within the Courseworks system.
 * 
 * @author Alexander Roth
 * @date 2014-08-19
 */
public class Course {

    private String title;
    private String professor;
    private String meetingTime;
    private String meetingPlace;
    private String courseID;

    public Course() {
        this.title = null;
        this.professor = null;
        this.meetingTime = null;
        this.meetingPlace = null;
        this.courseID = null;
    }

    public String getTitle() {
        return this.title;
    }

    public String getProfessor() {
        return this.professor;
    }

    public String getMeetingTime() {
        return this.meetingTime;
    }

    public String getMeetingPlace() {
        return this.meetingPlace;
    }

    public String getCourseID() {
        return this.courseID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
}