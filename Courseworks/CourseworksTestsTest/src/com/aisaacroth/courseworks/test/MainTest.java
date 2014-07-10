package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.TabsPagerAdapter;
import com.aisaacroth.courseworks.views.Main;

import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

public class MainTest extends ActivityInstrumentationTestCase2<Main> {
    private ActionBar testActionBar;
    private Main testMainActivity;
    private TabsPagerAdapter testTabsPagerAdapter;
    private ViewPager testViewer;
    
    private final int MIDDLE = 1;

    public MainTest() {
        super(Main.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        testMainActivity = getActivity();
        testViewer = (ViewPager) testMainActivity.findViewById(R.id.pager);
        testActionBar = testMainActivity.getActionBar();
        testTabsPagerAdapter = new TabsPagerAdapter(
                testMainActivity.getSupportFragmentManager());
    }

    @SmallTest
    public void testLayoutExist() {
        assertNotNull(testActionBar);
        assertNotNull(testTabsPagerAdapter);
        assertNotNull(testViewer);
    }

    @SmallTest
    public void testActionBarContainsTabs() {
        assertEquals(3, testActionBar.getTabCount());
    }
    
    @SmallTest
    public void testInitialTab() {
        assertEquals("Home", testActionBar.getTabAt(MIDDLE).getText());
    }

    

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}