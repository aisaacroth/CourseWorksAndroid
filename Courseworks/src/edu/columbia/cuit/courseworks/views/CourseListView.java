package edu.columbia.cuit.courseworks.views;

import java.util.ArrayList;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.CourseAdapter;
import edu.columbia.cuit.courseworks.structures.Course;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;

public class CourseListView extends ItemListView<Course> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        setActionBar(R.layout.course_list_actionbar);
        getCourses();
        setHasOptionsMenu(true);
        adapter = new CourseAdapter(this.getActivity(), itemList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState,
                R.layout.fragment_course_list_view);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent course = new Intent(getActivity(), CourseView.class);
        course.putExtra("Course", itemList.get(position));
        startActivity(course);
    }
    
    private void getCourses() {
        itemList = new ArrayList<Course>();
    }
}