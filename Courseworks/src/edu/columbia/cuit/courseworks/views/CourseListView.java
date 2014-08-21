package edu.columbia.cuit.courseworks.views;

import java.util.ArrayList;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.structures.Course;
import edu.columbia.cuit.courseworks.utils.LogoutUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.ListView;

public class CourseListView extends ListFragment {
    private ArrayList<Course> courseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_course_list_view,
                null);
        return view;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.list_signout_option) {
            getActivity().finish();
            LogoutUtil.logout(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent course = new Intent(getActivity(), AnnouncementView.class);
        course.putExtra("Course", courseList.get(position));
        startActivity(course);
    }
    
}