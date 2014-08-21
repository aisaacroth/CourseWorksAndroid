package edu.columbia.cuit.courseworks.adapters;

import edu.columbia.cuit.courseworks.views.*;

import android.support.v4.app.*;

/**
 * Extends the FragmentPagerAdapter class in order to
 * allow for the Tab interface within the Main activity of the Courseworks
 * application.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 */
public class MainTabsPagerAdapter extends FragmentPagerAdapter {

    public MainTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
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