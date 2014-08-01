package com.aisaacroth.courseworks.views;


import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.TabsPagerAdapter;
import com.aisaacroth.courseworks.feeds.UserFeed;
import com.aisaacroth.courseworks.structures.User;

import android.app.*;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.*;

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
    private final int MIDDLE = 1;
    private User currentUser;

    private String[] tabTitles = { "Courses", "Home", "Calendar" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent sessionIntent = getIntent();
        String sessionCookie = sessionIntent.getStringExtra("JSESSION");
        UserFeed userFeed = new UserFeed();
        userFeed.execute(sessionCookie);
       
        initializeTabsPagerAdapter();
        setupActivity();
        addTabsToView();

        // Linking the tabs to their displays.
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

        // Set the initial fragment as the middle tab.
        viewPager.setCurrentItem(MIDDLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

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

    private void initializeTabsPagerAdapter() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    }

    private void setupActivity() {
        viewPager.setAdapter(tabsPagerAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    public User getUser() {
        return currentUser;
    }

}