package com.aisaacroth.courseworks.adapters;

import com.aisaacroth.courseworks.views.AnnouncementView;
import com.aisaacroth.courseworks.views.CalendarView;
import com.aisaacroth.courseworks.views.CourseView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * The TabsPagerAdapter class extends the FragmentPagerAdapter class in order to
 * allow for the Tab interface within the Main activity of the Courseworks
 * application.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    /**
     * Instantiates a new TabsPagerAdapter, which adapts the Fragments for the
     * any tab interface.
     * 
     * @param fm
     *            the FragmentManager object that will interact with the
     *            activity.
     */
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return new CourseView();
        case 1:
            return new AnnouncementView();
        case 2:
            return new CalendarView();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return 3;
    }

}