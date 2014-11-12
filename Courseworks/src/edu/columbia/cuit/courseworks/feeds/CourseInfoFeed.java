package edu.columbia.cuit.courseworks.feeds;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.reconstructors.CourseInfoReconstructor;
import edu.columbia.cuit.courseworks.requesters.Requester;
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
        Requester requester = new Requester();
        for (int i = 0; i < this.urlList.size(); i++) {
            String url = this.urlList.get(i);
            Course course = this.courseList.get(i);
            try {
                HttpResponse response = requester.getRequest(url, params[0]);
                Course updatedCourse = courseInfoReconstructor
                        .readCourseInfoFromJSON(ResponseToString(response),
                                course);
                this.courseList.remove(i);
                this.courseList.add(i, updatedCourse);
            } catch (FailedConnectionException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return courseList;
    }

    private String ResponseToString(HttpResponse response)
            throws ParseException, IOException {
        return EntityUtils.toString(response.getEntity());
    }
}
