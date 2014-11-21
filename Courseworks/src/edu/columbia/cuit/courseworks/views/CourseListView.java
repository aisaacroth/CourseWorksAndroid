package edu.columbia.cuit.courseworks.views;

import java.util.concurrent.ExecutionException;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.CourseAdapter;
import edu.columbia.cuit.courseworks.feeds.CourseFeed;
import edu.columbia.cuit.courseworks.feeds.CourseInfoFeed;
import edu.columbia.cuit.courseworks.structures.Course;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ListView;

/**
 * Maintains a dynamic list of courses that are pushed to the user each time
 * he/she accesses the "Courses" tab on the application.
 * 
 * @author Alexander Roth
 * @date 2014-08-22
 */
public class CourseListView extends ItemListView<Course> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        String uni = intent.getStringExtra("USER");
        String sessionCookie = intent.getStringExtra("JSESSION");
        String semester = this.getArguments().getString("SEMESTER");
        loggingStatements(uni, sessionCookie, semester);
        getCourses(uni, sessionCookie, semester);
        setHasOptionsMenu(true);
        adapter = new CourseAdapter(this.getActivity(), itemList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState,
                R.layout.fragment_course_list_view);
    }

    private void getCourses(String user, String sessionCookie, String semester) {
        CourseFeed courseFeed = new CourseFeed(user, semester);
        try {
            itemList = courseFeed.execute(sessionCookie).get();
            Log.d("TEST", semester);
            CourseInfoFeed courseInfoFeed = new CourseInfoFeed(itemList);
            itemList = courseInfoFeed.execute(sessionCookie).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent course = new Intent(getActivity(), CourseView.class);
        course.putExtra("Course", itemList.get(position));
        // startActivity(course);
    }

    private void loggingStatements(String uni, String cookie, String semester) {
        Log.d("USER", uni);
        Log.d("SESSION ID", cookie);
        Log.d("SEMESTER", semester);
    }

}
