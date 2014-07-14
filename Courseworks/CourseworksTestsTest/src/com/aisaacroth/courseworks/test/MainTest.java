package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.TabsPagerAdapter;
import com.aisaacroth.courseworks.views.Main;

import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * The test suite for the Main Activity. There are not that many tests because
 * most of the actions are handled by respective fragments.
 * 
 * @author Alexander Roth
 * @date 2014-07-10
 */
public class MainTest extends ActivityInstrumentationTestCase2<Main> {
    private ActionBar testActionBar;
    private Main testMainActivity;
    private TabsPagerAdapter testTabsPagerAdapter;
    private ViewPager testViewer;

    private final int LEFT = 0;
    private final int MIDDLE = 1;
    private final int RIGHT = 2;

    public MainTest() {
        super(Main.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        testMainActivity = getActivity();
        testActionBar = testMainActivity.getActionBar();
        testTabsPagerAdapter = new TabsPagerAdapter(
                testMainActivity.getSupportFragmentManager());
        testViewer = (ViewPager) testMainActivity.findViewById(R.id.pager);
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

    @SmallTest
    public void testTabText() {
        assertEquals("Courses", testActionBar.getTabAt(LEFT).getText());
        assertEquals("Home", testActionBar.getTabAt(MIDDLE).getText());
        assertEquals("Calendar", testActionBar.getTabAt(RIGHT).getText());
    }
    
    public void testAnnouncementViewExist() {
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}