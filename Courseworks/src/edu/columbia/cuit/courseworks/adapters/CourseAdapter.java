package edu.columbia.cuit.courseworks.adapters;

import java.util.List;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.structures.Course;

import android.content.Context;
import android.graphics.Typeface;
import android.view.*;
import android.widget.*;

/**
 * Extends the ArrayAdapter class in order to run an ListView for a student's
 * Courses.
 * 
 * @author Alexander Roth
 * @date 2014-08-22
 */
public class CourseAdapter extends ItemAdapter<Course> {


    private TextView titleText;
    private TextView courseIDText;
    private TextView professorText;
    private TextView meetingTimeText;
    private TextView meetingPlaceText;
    
    public CourseAdapter(Context context, List<Course> itemList) {
        super(context, itemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = itemList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.course_list_item, null);
        }

        setListItemFormat(convertView, course);
        return convertView;
    }

    @Override
    protected void setListItemFormat(View view, Course course) {
        setListItemViews(view);
        setTextInViews(course);
        setFontsForText();
    }

    @Override
    protected void setListItemViews(View view) {
        titleText = (TextView) view.findViewById(R.id.course_item_title);
        courseIDText = (TextView) view.findViewById(R.id.course_item_id);
        professorText = (TextView) view
                .findViewById(R.id.course_item_professor);
        meetingTimeText = (TextView) view
                .findViewById(R.id.course_item_meeting_time);
        meetingPlaceText = (TextView) view
                .findViewById(R.id.course_item_meeting_place);
    }

    @Override
    protected void setTextInViews(Course course) {
        titleText.setText(course.getTitle());
        courseIDText.setText(course.getCourseID());
        professorText.setText(course.getProfessor());
        meetingTimeText.setText(course.getMeetingTime());
        meetingPlaceText.setText(course.getMeetingPlace());
    }

    @Override
    protected void setFontsForText() {
        setRegularFonts();
        setMediumFonts();
    }

    private void setRegularFonts() {
        Typeface regularFont = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Regular.ttf");
        titleText.setTypeface(regularFont);
    }

    private void setMediumFonts() {
        Typeface mediumFont = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Medium.ttf");
        courseIDText.setTypeface(mediumFont);
        professorText.setTypeface(mediumFont);
        meetingTimeText.setTypeface(mediumFont);
        meetingPlaceText.setTypeface(mediumFont);
    }

}