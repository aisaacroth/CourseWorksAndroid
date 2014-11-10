package edu.columbia.cuit.courseworks.reconstructors;

import org.json.*;

import edu.columbia.cuit.courseworks.structures.Course;

public class CourseInfoReconstructor {
    private final String MEETING_PLACE_TAG = "columbia-sis-meeting-location";
    private final String MEETING_TIME_TAG = "columbia-sis-meeting-time";
    
    public Course readCourseInfoFromJSON(String string, Course course)
            throws JSONException {
        JSONObject fullJSON = readStringToJSONObject(string);
        JSONObject courseProperties = extractCoursePropertiesFromJSON(fullJSON);
        Course updatedCourse = updateCourseInformation(courseProperties, course);
        return updatedCourse;
    }
    
    private JSONObject readStringToJSONObject(String string) throws JSONException {
        return new JSONObject(string);
    }
    
    private JSONObject extractCoursePropertiesFromJSON(JSONObject object)
            throws JSONException {
        return object.getJSONObject("props");
    }
    
    private Course updateCourseInformation(JSONObject properties, Course course)
            throws JSONException {
        String courseMeetingPlace = properties.getString(MEETING_PLACE_TAG);
        String courseMeetingTime = properties.getString(MEETING_TIME_TAG);
        course.setMeetingPlace(courseMeetingPlace);
        course.setMeetingTime(courseMeetingTime);
        return course;
    }
}
