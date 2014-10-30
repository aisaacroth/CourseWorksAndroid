package edu.columbia.cuit.courseworks.adapters;

import edu.columbia.cuit.courseworks.views.CourseListView;
import android.app.*;
import android.os.Bundle;
import android.support.v13.app.*;
import android.util.Log;

public class CourseTabsPagerAdapter extends FragmentPagerAdapter {

    public CourseTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return newInstance("past");
        case 1:
            return newInstance("current");
        case 2:
            return newInstance("current");
        }
        throw new NullPointerException();
    }

    @Override
    public int getCount() {
        return 3;
    }
    
    private static final CourseListView newInstance(String semester) {
        CourseListView courseListView = new CourseListView();
        Bundle bdl = new Bundle(1);
        Log.d("BUNDLE", semester);
        bdl.putString("SEMESTER", semester);
        courseListView.setArguments(bdl);
        return courseListView;
    }
}
