package edu.columbia.cuit.courseworks.test;


import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;


import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.MainTabsPagerAdapter;
import edu.columbia.cuit.courseworks.views.Main;
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
    private MainTabsPagerAdapter testTabsPagerAdapter;
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
        initTestObjects();
    }

    private void initTestObjects() {
        testMainActivity = getActivity();
        testActionBar = testMainActivity.getActionBar();
        testTabsPagerAdapter = new MainTabsPagerAdapter(
                testMainActivity.getSupportFragmentManager());
        testViewer = (ViewPager) testMainActivity.findViewById(R.id.main_pager);
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