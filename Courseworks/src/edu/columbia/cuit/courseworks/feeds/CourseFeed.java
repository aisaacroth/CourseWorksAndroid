package edu.columbia.cuit.courseworks.feeds;

import java.util.ArrayList;


import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.reconstructors.CourseReconstructor;
import edu.columbia.cuit.courseworks.structures.Course;

import android.os.AsyncTask;

public class CourseFeed extends AsyncTask<String, Void, ArrayList<Course>>{

    private String url;
    private CourseReconstructor courseReconstructor;
    private ArrayList<Course> courseList;
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // TODO Switch to production servers
        url = "https://sakaidev.cc.columbia.edu/portal";
        courseReconstructor = new CourseReconstructor();
    }

    @Override
    protected ArrayList<Course> doInBackground(String... params) {
        try {
            courseList = courseReconstructor.constructCourses(url, params[0]);
        } catch (FailedConnectionException e) {
            e = new FailedConnectionException(
                    "There appears to be a connection error.");
            e.printStackTrace();
        }
        
        return courseList;
        
    }
    
    
}
