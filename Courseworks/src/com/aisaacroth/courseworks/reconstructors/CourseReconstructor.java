package com.aisaacroth.courseworks.reconstructors;

import java.util.ArrayList;

import android.util.Log;

import com.aisaacroth.courseworks.exceptions.FailedConnectionException;
import com.aisaacroth.courseworks.structures.Course;

public class CourseReconstructor extends Reconstructor {

    private Course course;
    private ArrayList<Course> courseList;

    public CourseReconstructor() {
        this.course = null;
        this.dataString = null;
    }

    public ArrayList<Course> constructCourses(String url, String sessionID)
            throws FailedConnectionException {
        courseList = new ArrayList<Course>();
        setDataString(url, sessionID);
        Log.d("DATA STRING", this.dataString);
        return courseList;
    }

}
