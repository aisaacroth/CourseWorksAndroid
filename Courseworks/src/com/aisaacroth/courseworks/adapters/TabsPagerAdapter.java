package com.aisaacroth.courseworks.adapters;

import com.aisaacroth.courseworks.views.AnnouncementView;
import com.aisaacroth.courseworks.views.CalendarView;
import com.aisaacroth.courseworks.views.CourseView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

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

	@Override
	public int getCount() {
		return 3;
	}
}
