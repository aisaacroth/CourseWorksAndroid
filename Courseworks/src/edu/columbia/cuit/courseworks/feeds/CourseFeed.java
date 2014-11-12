package edu.columbia.cuit.courseworks.feeds;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.*;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.reconstructors.CourseReconstructor;
import edu.columbia.cuit.courseworks.requesters.Requester;
import edu.columbia.cuit.courseworks.structures.Course;

import android.os.AsyncTask;

/**
 * Accesses the Courseworks API and pulls in all information relating to the
 * current user's courses
 * 
 * @author Alexander Roth
 * @date 2014-08-20
 */
public class CourseFeed extends AsyncTask<String, Void, ArrayList<Course>> {

    private ArrayList<Course> courseList;
    private CourseReconstructor courseReconstructor;
    private String url;
    private final String COURSEWORKS_DIRECT = "https://courseworks.columbia.edu/direct/";
    private String uni;
    private String semester;

    public CourseFeed(String uni, String semester) {
        super();
        this.uni = uni;
        this.semester = semester;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        url = COURSEWORKS_DIRECT + "my_courses/access/" + semester + "/" + uni
                + ".json";
        courseReconstructor = new CourseReconstructor();
    }

    @Override
    protected ArrayList<Course> doInBackground(String... params) {
        Requester requester = new Requester();
        try {
            HttpResponse response = requester.getRequest(url, params[0]);
            courseList = courseReconstructor
                    .readCoursesFromJSON(ResponseToString(response));
        } catch (FailedConnectionException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return courseList;
    }

    private String ResponseToString(HttpResponse response)
            throws ParseException, IOException {
        return EntityUtils.toString(response.getEntity());
    }

}
