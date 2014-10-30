package edu.columbia.cuit.courseworks.reconstructors;

import java.util.ArrayList;
import java.util.Locale;

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
        String[] xmlArray = prepareXML(url, sessionID);
        courseList = fetchCourses(xmlArray);
        return courseList;
    }

    private String[] prepareXML(String url, String sessionID)
            throws FailedConnectionException {
        setDataString(url, sessionID);
        String[] xmlArray = parseCourseStrings();
        return xmlArray;
    }

    private String[] parseCourseStrings() {
        removeCollectionTag();
        String[] courseXMLs = splitCourses();
        removeCourseTag(courseXMLs);
        return courseXMLs;
    }

    private void removeCollectionTag() {
        int startIndex = this.dataString.indexOf(">") + 1;
        int endIndex = this.dataString.indexOf("</my_courses_collection");
        this.dataString = this.dataString.substring(startIndex, endIndex);
    }

    private String[] splitCourses() {
        return dataString.split("</my_courses>");
    }

    private void removeCourseTag(String[] courses) {
        for (int i = 0; i < courses.length; i++) {
            int startIndex = courses[i].indexOf(">") + 1;
            courses[i] = courses[i].substring(startIndex);
        }
    }

    private ArrayList<Course> fetchCourses(String[] xmlArray) {
        ArrayList<Course> courses = new ArrayList<Course>();

        for (int i = 0; i < xmlArray.length - 1; i++) {
            this.course = new Course();
            this.dataString = xmlArray[i];
            setCourse();
            courses.add(course);
        }
        return courses;
    }

    private void setCourse() {
        course.setCourseID(parseFromXMLTag("siteId"));
        course.setTitle(reformatTitle(parseFromXMLTag("siteTitle")));
        Log.d("COURSE ID", course.getCourseID());
        Log.d("COURSE NAME", course.getTitle());
    }

    private String reformatTitle(String title) {
        String[] words = title.split(" ");
        String cleanTitle = "";
        for (String word : words) {
            cleanTitle += word.substring(0, 1).toUpperCase(Locale.US)
                    + word.substring(1).toLowerCase(Locale.US) + " ";
        }
        return cleanTitle;
    }

}
