package com.aisaacroth.courseworks.test;


import com.aisaacroth.courseworks.reconstructors.AnnouncementReconstructor;
import com.aisaacroth.courseworks.structures.User;

import junit.framework.TestCase;

public class AnnouncementReconstructorTest extends TestCase {
    private User testUser;
    private AnnouncementReconstructor testAR;
    private String url;

    protected void setUp() throws Exception {
        super.setUp();
        testUser = new User();
        testAR = new AnnouncementReconstructor(testUser);
        url = "https://courseworks.columbia.edu/welcome/";
        setUpUser();
    }

    private void setUpUser() {
        testUser.setDisplayName("Alexander Isaac Roth");
        testUser.setEmailAddress("air2112@columbia.edu");
        testUser.setUni("air2112");
        testUser.setUserID("cff5b1bc-89d2-4473-b3c5-df79f372acf3");
    }
    
    public void testAnnouncementRequest() {
        url = "https://courseworks.columbia.edu/direct/announcement"
                + testUser.getReference() + ".xml?n=20&d=100";
        String testURL = "https://courseworks.columbia.edu/direct/announcement/"
                + "user/cff5b1bc-89d2-4473-b3c5-df79f372acf3.xml?n=20&d=100";
        assertEquals(testURL, url);
    }
    
    public void testConstructor() {
        assertNotNull(testAR);
    }
    
    public void testParseDate() {
        String testDateXML = "<createdOn type=\"date\" date=\"2014-06-30T21:36:30-04:00\">1404178590672</createdOn>";
        testAR.xmlString = testDateXML;
        String testDate = "2014-06-30";
        String actualDate = testAR.parseDateString();
        assertEquals(testDate, actualDate);
    }


    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
