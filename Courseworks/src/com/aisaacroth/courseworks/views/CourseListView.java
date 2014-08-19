package com.aisaacroth.courseworks.views;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.CourseAdapter;
import com.aisaacroth.courseworks.feeds.CourseFeed;
import com.aisaacroth.courseworks.structures.Course;
import com.aisaacroth.courseworks.utils.LogoutUtil;

import android.support.v4.app.ListFragment;
import android.app.ActionBar;
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
    private final int LEFT = 0;
    private final int MIDDLE = 1;
    private final int RIGHT = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        renameTabs();
        getCourses();
        setHasOptionsMenu(true);
        
        // Implement a switch method to change off the different courses with regards
        // to when they were offered.
        CourseAdapter currentAdapter = new CourseAdapter(getActivity(), courseList);
        setListAdapter(currentAdapter);
        View view = inflater.inflate(R.layout.fragment_course_list_view, null);
        return view;
    }
    
    private void renameTabs() {
        ActionBar tabBar = getActivity().getActionBar();
        tabBar.getTabAt(LEFT).setText("Past Courses");
        tabBar.getTabAt(MIDDLE).setText("Current Courses");
        tabBar.getTabAt(RIGHT).setText("Next Semester");
        tabBar.setSelectedNavigationItem(MIDDLE);
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
        if (id == R.id.course_list_signout_option) {
            getActivity().finish();
            LogoutUtil.logout(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater infalter) {
        infalter.inflate(R.menu.course_list_view, menu);
        super.onCreateOptionsMenu(menu, infalter);
    }

}