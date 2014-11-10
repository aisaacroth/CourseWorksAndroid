package edu.columbia.cuit.courseworks.feeds;

import java.util.ArrayList;

import edu.columbia.cuit.courseworks.reconstructors.CourseInfoReconstructor;
import edu.columbia.cuit.courseworks.structures.Course;

import android.os.AsyncTask;

public class CourseInfoFeed extends AsyncTask<String, Void, ArrayList<Course>> {
    private ArrayList<Course> courseList;
    private String url;
    private final String COURSEWORKS_DIRECT = "https://courseworks.columbia.edu/direct";
    private final String SITE = "/site/";
    private final String JSON_TAG = ".json";
    private ArrayList<String> urlList;
    private CourseInfoReconstructor courseInfoReconstructor;

    public CourseInfoFeed(ArrayList<Course> courseList) {
        this.courseList = courseList;
        this.urlList = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.urlList = new ArrayList<String>();
        for (Course course : courseList) {
            url = COURSEWORKS_DIRECT + SITE + course.getCourseID() + JSON_TAG;
            this.urlList.add(url);
        }
        courseInfoReconstructor = new CourseInfoReconstructor();
    }

    @Override
    protected ArrayList<Course> doInBackground(String... params) {
        return null;
    }

}
