package edu.columbia.cuit.courseworks.adapters;

import edu.columbia.cuit.courseworks.*;
import android.support.v4.app.*;

public class CourseTabsPagerAdapter extends FragmentPagerAdapter {

    public CourseTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return new PastCourseListView();
        case 1:
            return new CurrentCourseListView();
        case 2:
            return new NextCourseListView();
        }
        throw new NullPointerException();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
