package edu.columbia.cuit.courseworks.views;

import edu.columbia.cuit.courseworks.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;

public class CourseListView extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_course_list_view, container, false);
        return rootView;
    }
}