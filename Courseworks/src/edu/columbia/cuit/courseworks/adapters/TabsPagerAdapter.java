package edu.columbia.cuit.courseworks.adapters;

import android.app.*;
import android.support.v13.app.FragmentPagerAdapter;
import edu.columbia.cuit.courseworks.views.*;


/**
 * Extends the FragmentPagerAdapter class in order to
 * allow for the Tab interface within the Main activity of the Courseworks
 * application.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return new CourseListView();
        case 1:
            return new AnnouncementListView();
        case 2:
            return new CalendarView();
        }

        throw new NullPointerException();
    }

    @Override
    public int getCount() {
        return 3;
    }

}