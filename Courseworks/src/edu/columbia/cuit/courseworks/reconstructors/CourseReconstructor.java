package edu.columbia.cuit.courseworks.reconstructors;

import java.util.ArrayList;

import android.util.Log;

import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.structures.Course;

/**
 * Reconstructs the current User's Courses XML file returned from the
 * Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-08-20
 */
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
