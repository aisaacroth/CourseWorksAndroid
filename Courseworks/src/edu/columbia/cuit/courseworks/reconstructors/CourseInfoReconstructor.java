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

    private JSONObject readStringToJSONObject(String string)
            throws JSONException {
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
        course.setMeetingPlace(sanitizeMeetingPlace(courseMeetingPlace));
        course.setMeetingTime(sanitizeMeetingTime(courseMeetingTime));
        return course;
    }

    private String sanitizeMeetingPlace(String meetingPlace) {
        String building = "";
        String roomNumber = "";
        roomNumber = meetingPlace.substring(meetingPlace.lastIndexOf(" "));
        building = getBuildingName(meetingPlace);

        return building + " " + roomNumber;
    }

    private String getBuildingName(String meetingPlace) {
        String building = "";
        if (meetingPlace.contains("FAIRCHILD")) {
            building = "Fairchild";
        } else if (meetingPlace.contains("SEELEY")) {
            building = "Mudd";
        } else if (meetingPlace.contains("PUPIN")) {
            building = "Pupin";
        } else if (meetingPlace.contains("INTERNATION")) {
            building = "IAB";
        } else if (meetingPlace.contains("ALFRED")) {
            building = "Lerner Hall";
        } else if (meetingPlace.contains("HAVEMEYER")) {
            building = "Havemeyer";
        } else if (meetingPlace.contains("HAMILTON")) {
            building = "Hamilton";
        } else if (meetingPlace.contains("SCHERMERHORN")) {
            building = "Schermerhorn";
        } else if (meetingPlace.contains("NORTHWEST")) {
            building = "Northwest Corner";
        } else if (meetingPlace.contains("BARNARD")) {
            building = "Barnard";
        } else if (meetingPlace.contains("MATHEMATICS")) {
            building = "Mathematics";
        } else if (meetingPlace.contains("PHILO")) {
            building = "Philosophy";
        } else if (meetingPlace.contains("DODGE BUILDI")) {
            building = "Dodge Hall";
        } else if (meetingPlace.contains("KNOX")) {
            building = "Knox Hall";
        }
        return building;
    }
    
    private String sanitizeMeetingTime(String meetingTime) {
        int timeIndex = meetingTime.indexOf(" ");
        String time = meetingTime.substring(timeIndex).trim();
        String days = meetingTime.substring(0, timeIndex);
        return days + " " + cleanTime(time);
    }
    
    private String cleanTime(String time) {
        String cleanTime = "";
        if (time.contains("P") && time.contains("A")) {
            cleanTime = time.replace("P", "PM");
            cleanTime = cleanTime.replace("A", "AM");
        } else if (time.contains("P")) {
            cleanTime = time.replace("P", "PM");
        } else if (time.contains("A")) {
            cleanTime = time.replace("A", "AM");
        }
        return cleanTime;
    }
}
