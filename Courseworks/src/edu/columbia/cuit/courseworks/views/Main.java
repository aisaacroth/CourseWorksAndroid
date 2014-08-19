package edu.columbia.cuit.courseworks.views;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.TabsPagerAdapter;
import edu.columbia.cuit.courseworks.feeds.UserFeed;
import edu.columbia.cuit.courseworks.structures.User;

import android.app.*;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.TextView;

/**
 * The Homepage activity for the Courseworks app. Acts as a container for all
 * the subsequent fragments.
 * 
 * @author Alexander Roth
 * @date 2014-02-24
 */
public class Main extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private ActionBar actionBar;
    private TextView currentDate;
    private TextView updatedTime;
    private final int MIDDLE = 1;
    private User currentUser;

    private String[] tabTitles = { "Courses", "Home", "Calendar" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        removeHomeIcon();

        String sessionCookie = extractSessionCookieFromIntent();
        UserFeed userFeed = new UserFeed();
        try {
            currentUser = userFeed.execute(sessionCookie).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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

    private void removeHomeIcon() {
        final View homeIcon = findViewById(android.R.id.home);
        ((View) homeIcon.getParent()).setVisibility(View.GONE);
    }

    private void initializeTabsPagerAdapter() {
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        actionBar = getActionBar();
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    }

    private void setupActivity() {
        viewPager.setAdapter(tabsPagerAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    private String extractSessionCookieFromIntent() {
        Intent sessionIntent = getIntent();
        return sessionIntent.getStringExtra("JSESSION");
    }

    private void setDateAndTime() {
        currentDate = (TextView) findViewById(R.id.main_current_date);
        updatedTime = (TextView) findViewById(R.id.main_update_time);
        currentDate.setText(setCurrentDate());
        updatedTime.setText(setUpdatedTime());
    }

    private String setCurrentDate() {
        return DateFormat.getDateInstance().format(new Date());
    }

    private String setUpdatedTime() {
        return "UPDATED "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(
                        new Date());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
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

    private void addTabsToView() {
        for (String tab_name : tabTitles) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));

        }
    }

    public User getUser() {
        return currentUser;
    }

}