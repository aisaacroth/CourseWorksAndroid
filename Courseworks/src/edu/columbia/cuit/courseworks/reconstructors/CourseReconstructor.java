package edu.columbia.cuit.courseworks.reconstructors;

import java.util.*;

import org.json.*;

import edu.columbia.cuit.courseworks.structures.Course;

/**
 * Reconstructs the current User's Courses XML file returned from the
 * Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-08-20
 */
public class CourseReconstructor {

    public ArrayList<Course> readCoursesFromJSON(String string)
            throws JSONException {
        JSONObject fullJSON = readStringToJSONObject(string);
        JSONArray courseArray = readArrayFromJsonObject(fullJSON);
        ArrayList<Course> courses = readCourseArray(courseArray);
        return courses;
    }

    private JSONObject readStringToJSONObject(String string)
            throws JSONException {
        return new JSONObject(string);
    }

    private JSONArray readArrayFromJsonObject(JSONObject object)
            throws JSONException {
        return object.getJSONArray("my_courses_collection");
    }

    private ArrayList<Course> readCourseArray(JSONArray courseArray)
            throws JSONException {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < courseArray.length(); i++) {
            JSONObject values = courseArray.getJSONObject(i);
            JSONObject data = values.getJSONObject("data");
            String courseTitle = data.getString("siteTitle");
            String courseID = data.getString("siteId");
            if (courseTitle.equals("null") && courseID.equals("null")) {
            } else {
                String cleanTitle = santizeTitle(courseTitle);
                courses.add(new Course(cleanTitle, courseID));
            }
        }
        return courses;
    }

    private String santizeTitle(String title) {
        String[] words = title.split(" ");
        String cleanTitle = "";
        for (String word : words) {
            String segment = "";
            if (word.equals("II") || word.equals("I") || word.equals("III")
                    || word.equals("IV")) {
                segment = word;
            } else {
                segment = word.substring(0, 1)
                        + word.substring(1).toLowerCase(Locale.US);
            }
            cleanTitle += segment + " ";
        }
        return cleanTitle;
    }

}
