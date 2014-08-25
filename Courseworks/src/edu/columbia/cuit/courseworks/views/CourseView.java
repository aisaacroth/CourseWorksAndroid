package edu.columbia.cuit.courseworks.views;

import edu.columbia.cuit.courseworks.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;

/**
 * A singleton view of any given course. Called from the CourseListView
 * fragment.
 * 
 * @author Alexander Roth
 * @date 2014-08-20
 */
public class CourseView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.course_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}