package edu.columbia.cuit.courseworks.views;

import java.text.DateFormat;
import java.util.Date;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.CourseTabsPagerAdapter;

import android.support.v4.view.ViewPager;
import android.app.*;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * TODO: Implement CourseView styling
 * 
 * @author Alexander Roth
 * @data 2014-06-19
 */
public class CourseListHolderView extends Activity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private CourseTabsPagerAdapter tabsPagerAdapter;
    private ActionBar actionBar;
    private TextView updateDate;
    private TextView updateTime;
    private TextView actionBarBackButton;
    private final int MIDDLE = 1;

    private String[] tabTitles = { "Past\nCourses", "Current\nCourses",
            "Next\nSemester" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_holder_view);
        setActionBar();
        setBackButton();
        removeHomeIcon();
        final String sessionCookie = extractSessionCookieFromIntent();

        initializeTabsPagerAdapter();
        setupActivity();
        addTabsToView();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        viewPager.setCurrentItem(MIDDLE);
        setDateAndTime();
    }

    private void setActionBar() {
        actionBar = this.getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        actionBar.setCustomView(R.layout.course_list_actionbar);
    }

    private void setBackButton() {
        actionBarBackButton = (TextView) findViewById(R.id.course_list_actionbar_back);
        setBackButtonCommand();
    }

    private void setBackButtonCommand() {
        actionBarBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
    }

    private void removeHomeIcon() {
        final View homeIcon = findViewById(android.R.id.home);
        ((View) homeIcon.getParent()).setVisibility(View.GONE);
    }

    private String extractSessionCookieFromIntent() {
        Intent sessionIntent = getIntent();
        return sessionIntent.getStringExtra("JSESSION");
    }

    private void initializeTabsPagerAdapter() {
        viewPager = (ViewPager) findViewById(R.id.course_pager);
        actionBar = getActionBar();
        tabsPagerAdapter = new CourseTabsPagerAdapter(getFragmentManager());
    }

    private void setupActivity() {
        viewPager.setAdapter(tabsPagerAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    private void addTabsToView() {
        for (String tabName : tabTitles) {
            actionBar.addTab(actionBar.newTab().setText(tabName)
                    .setTabListener(this));
        }
    }

    private void setDateAndTime() {
        updateDate = (TextView) findViewById(R.id.course_update_date);
        updateTime = (TextView) findViewById(R.id.course_update_time);
        updateDate.setText(setUpdateDate());
        updateTime.setText(setUpdateTime());
    }

    private String setUpdateDate() {
        return DateFormat.getDateInstance().format(new Date());
    }

    private String setUpdateTime() {
        return "UPDATED "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(
                        new Date());
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

}