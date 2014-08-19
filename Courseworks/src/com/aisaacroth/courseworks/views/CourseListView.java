package com.aisaacroth.courseworks.views;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.CourseAdapter;
import com.aisaacroth.courseworks.feeds.CourseFeed;
import com.aisaacroth.courseworks.structures.Course;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;

/**
 * TODO: Implement CourseView styling
 * 
 * @author Alexander Roth
 * @data 2014-06-19
 */
public class CourseListView extends ListFragment {
    private ArrayList<Course> courseList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        getCourses();
        setHasOptionsMenu(true);
        CourseAdapter adapter = new CourseAdapter(getActivity(), courseList);
        setListAdapter(adapter);
        View view = inflater.inflate(R.layout.fragment_course_list_view, null);
        return view;
    }
    
    public void getCourses() {
        String sessionID = getSessionID();
        CourseFeed courseFeed = new CourseFeed();
        try {
            courseList = courseFeed.execute(sessionID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    private String getSessionID() {
        Intent sessionIntent = getActivity().getIntent();
        return sessionIntent.getStringExtra("JSESSION");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}