package com.aisaacroth.courseworks.adapters;

import com.aisaacroth.courseworks.views.*;

import android.support.v4.app.*;

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

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}