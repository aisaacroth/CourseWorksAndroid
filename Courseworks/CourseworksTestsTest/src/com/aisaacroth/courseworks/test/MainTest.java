package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.views.Main;

import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

public class MainTest extends ActivityInstrumentationTestCase2<Main> {
    private Main testMainActivity;
    private ViewPager testViewer;

    public MainTest() {
        super(Main.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        testMainActivity = getActivity();
        testViewer = (ViewPager) testMainActivity.findViewById(R.id.pager);
    }
    
    @SmallTest
    public void testLayoutExist() {
        assertNotNull(testViewer);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
