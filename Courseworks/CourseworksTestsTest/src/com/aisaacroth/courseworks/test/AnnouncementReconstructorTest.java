package com.aisaacroth.courseworks.test;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.reconstructors.AnnouncementReconstructor;
import com.aisaacroth.courseworks.structures.Announcement;
import com.aisaacroth.courseworks.structures.User;

import junit.framework.TestCase;

/**
 * The test suite for the AnnouncementReconstructor class
 * 
 * @author Alexander Roth
 * @date 2014-07-29
 */
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

    public void testParseAnnouncementString() {
        String testAnnouncementsXML = "<announcement_collection entityPrefix=\"announcement\">"
                + "<announcement>Hi</announcement>"
                + "<announcement>Test</announcement>"
                + "<announcement>Final One</announcement>"
                + "</announcement_collection>";
        testAR.xmlString = testAnnouncementsXML;
        String[] actualAnnouncementStrings = testAR.parseAnnouncementStrings();
        assertNotNull(actualAnnouncementStrings);
    }

    public void testParseBody() {
        String testBodyXML = "<body>"
                + "<p> Estimados estudiantes,</p> <p> Aquí les adjunto una hoja"
                + " de vocabulario para la película. También les recuerdo que el"
                + " lunes es la última prueba (la Unidad 19).</p> <p> Saludos y"
                + " buen fin de semana!</p> <p> Jessica</p> </body>";
        testAR.xmlString = testBodyXML;
        String testBody = "<p> Estimados estudiantes,</p> <p> Aquí les adjunto"
                + " una hoja de vocabulario para la película. También les"
                + " recuerdo que el lunes es la última prueba (la Unidad 19)."
                + "</p> <p> Saludos y buen fin de semana!</p> <p> Jessica</p> ";
        String actualBody = testAR.parseFromTag("body");
        assertEquals(testBody, actualBody);
    }

    public void testParseDate() {
        String testDateXML = "<createdOn type=\"date\" date=\"2014-06-30T21:36:30-04:00\">1404178590672</createdOn>";
        testAR.xmlString = testDateXML;
        String testDate = "2014-06-30";
        String actualDate = testAR.parseDateString();
        assertEquals(testDate, actualDate);
    }

    public void testParseDisplayName() {
        String testDisplayNameXML = "<createdByDisplayName>Jessica Gordon-Burroughs</createdByDisplayName>";
        testAR.xmlString = testDisplayNameXML;
        String testDisplayName = "Jessica Gordon-Burroughs";
        String actualDisplayName = testAR.parseFromTag("createdByDisplayName");
        assertEquals(testDisplayName, actualDisplayName);
    }

    public void testParseFullAnnouncements() throws ClientProtocolException, IOException {
        String testAnnouncementsXML = prepareXML();
        testAR.xmlString = testAnnouncementsXML;
        ArrayList<Announcement> results = testAR.constructAnnouncements("dummy");
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    private String prepareXML() {
        return "<announcement_collection entityPrefix=\"announcement\">"
                + "<announcement type=\"bean\" size=\"13\">"
                + "<announcementId>30e50f9d-6e47-4340-b314-f4135cdfc621</announcementId>"
                + "<attachments type=\"collection\" size=\"0\"></attachments>"
                + "<body>"
                + "<p> Dear students:</p> <p> This message will be in English as it is very important. I was just notified that there is a scheduling conflict with our classroom for the final exam. They moved us to the fourth floor. </p> <p> HAM 411 SPAN 1201.001</p> <p> Call me if you have any problems.</p> <p> Cell: 610-420-3822</p> <p> Saludos,</p> <p> Jessica</p>"
                + "</body>" 
                + "<createdByDisplayName>Jessica Gordon-Burroughs</createdByDisplayName>"
                + "<createdOn type=\"date\" date=\"2014-07-02T19:39:24-04:00\">1404344364809</createdOn>"
                + "<id>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</id>"
                + "<siteId>SPANS1201_001_2014_2</siteId>"
                + "<siteTitle>My Workspace</siteTitle>"
                + "<title>FINAL EXAM LOCATION: URGENT</title>"
                + "<entityReference>"
                + "/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityReference>" 
                + "<entityURL>"
                + "https://courseworks.columbia.edu/direct/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityURL>"
                + "<entityId>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityId>"
                + "<entityTitle>FINAL EXAM LOCATION: URGENT</entityTitle>"
                + "</announcement>"
                + "<announcement type=\"bean\" size=\"13\">"
                + "<announcementId>30e50f9d-6e47-4340-b314-f4135cdfc621</announcementId>"
                + "<attachments type=\"collection\" size=\"0\"></attachments>"
                + "<body>"
                + "<p> Dear students:</p> <p> This message will be in English as it is very important. I was just notified that there is a scheduling conflict with our classroom for the final exam. They moved us to the fourth floor. </p> <p> HAM 411 SPAN 1201.001</p> <p> Call me if you have any problems.</p> <p> Cell: 610-420-3822</p> <p> Saludos,</p> <p> Jessica</p>"
                + "</body>" 
                + "<createdByDisplayName>Jessica Gordon-Burroughs</createdByDisplayName>"
                + "<createdOn type=\"date\" date=\"2014-07-02T19:39:24-04:00\">1404344364809</createdOn>"
                + "<id>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</id>"
                + "<siteId>SPANS1201_001_2014_2</siteId>"
                + "<siteTitle>My Workspace</siteTitle>"
                + "<title>FINAL EXAM LOCATION: URGENT</title>"
                + "<entityReference>"
                + "/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityReference>" 
                + "<entityURL>"
                + "https://courseworks.columbia.edu/direct/announcement/SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityURL>"
                + "<entityId>"
                + "SPANS1201_001_2014_2:main:30e50f9d-6e47-4340-b314-f4135cdfc621"
                + "</entityId>"
                + "<entityTitle>FINAL EXAM LOCATION: URGENT</entityTitle>"
                + "</announcement>"
                + "</announcement_collection>";
    }

    public void testParseTitle() {
        String testTitleXML = "<title>COMPOSICION 4.2</title>";
        testAR.xmlString = testTitleXML;
        String testTitle = "COMPOSICION 4.2";
        String actualTitle = testAR.parseFromTag("title");
        assertEquals(testTitle, actualTitle);
    }

    public void testParseSiteId() {
        String testSiteIdXML = "<siteId>SPANS1201_001_2014_2</siteId>";
        testAR.xmlString = testSiteIdXML;
        String testSiteId = "SPANS1201_001_2014_2";
        String actualSiteId = testAR.parseFromTag("siteId");
        assertEquals(testSiteId, actualSiteId);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
