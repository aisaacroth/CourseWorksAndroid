package com.aisaacroth.courseworks.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.structures.User;
import com.aisaacroth.courseworks.web.UserReconstructor;

import junit.framework.TestCase;

public class UserReconstructorTester extends TestCase {
    private String url;
    private HttpResponse response;
    private User testUser;
    private UserReconstructor testReconstructor;

    protected void setUp() throws Exception {
        super.setUp();
        url = "https://courseworks.columbia.edu/welcome";
        testUser = null;
        testReconstructor = new UserReconstructor();
    }

    public void testConstructor() {
        assertNotNull(testReconstructor);
    }

    public void testGetCurrentUserResponse() throws ClientProtocolException,
            IOException {
        response = testReconstructor.getResponse(url);
        assertNotNull(response);
    }

    public void testGetXMLFromResponse() throws ClientProtocolException,
            IOException {
        response = testReconstructor.getResponse(url);
        String xml = testReconstructor.getXMLFromResponse(response);
        assertNotNull(xml);
    }

    public void testParseUNIFromString() {
        String testUniXml = "<displayId>air2112</displayId>";
        testReconstructor.xmlString = testUniXml;
        String testUni = testReconstructor.parseFromTag("displayId");
        assertEquals("air2112", testUni);
    }

    public void testParseUserIdFromString() {
        String testIdXml = "<id>cff5b1bc-89d2-4473-b3c5-df79f372acf3</id>";
        String testID = "cff5b1bc-89d2-4473-b3c5-df79f372acf3";
        testReconstructor.xmlString = testIdXml;
        String actualId = testReconstructor.parseFromTag("id");
        assertEquals(testID, actualId);
    }

    public void testParseDisplayNameFromString() {
        String testDisplayNameXml = "<displayName>Alexander Isaac Roth</displayName>";
        String testDisplayName = "Alexander Isaac Roth";
        testReconstructor.xmlString = testDisplayNameXml;
        String actualDisplayName = testReconstructor
                .parseFromTag("displayName");
        assertEquals(testDisplayName, actualDisplayName);
    }

    public void testParseEmailAddressFromString() {
        String testEmailAddressXml = "<email>air2112@columbia.edu</email>";
        String testEmailAddress = "air2112@columbia.edu";
        testReconstructor.xmlString = testEmailAddressXml;
        String actualEmailAddress = testReconstructor.parseFromTag("email");
        assertEquals(testEmailAddress, actualEmailAddress);
    }

    public void testParseAllFromXMLString() {
        testReconstructor.xmlString = prepareXMLString();
        String actualUni = testReconstructor.parseFromTag("displayId");
        String actualId = testReconstructor.parseFromTag("id");
        String actualDisplayName = testReconstructor
                .parseFromTag("displayName");
        String actualEmailAddress = testReconstructor.parseFromTag("email");
        String testUni = "air2112";
        String testID = "cff5b1bc-89d2-4473-b3c5-df79f372acf3";
        String testDisplayName = "Alexander Isaac Roth";
        String testEmailAddress = "air2112@columbia.edu";

        assertEquals(testUni, actualUni);
        assertEquals(testID, actualId);
        assertEquals(testDisplayName, actualDisplayName);
        assertEquals(testEmailAddress, actualEmailAddress);
    }

    private String prepareXMLString() {
        return "<user type=\"bean\" size=\"23\">"
                + "<createdDate type=\"date\" date=\"2012-05-08T08:46:56-04:00\">1336481216570</createdDate>"
                + "<createdTime type=\"bean\" size=\"2\">"
                + "<display>May 8, 2012 8:46 am</display>"
                + "<time type=\"number\">1336481216570</time>"
                + "</createdTime>"
                + "<displayId>air2112</displayId>"
                + "<displayName>Alexander Isaac Roth</displayName>"
                + "<eid>air2112</eid>"
                + "<email>air2112@columbia.edu</email>"
                + "<firstName>Alexander Isaac</firstName>"
                + "<id>cff5b1bc-89d2-4473-b3c5-df79f372acf3</id>"
                + "<lastModified type=\"number\">1336613906393</time>"
                + "<lastName>Roth</lastName>"
                + "<modifiedDate type=\"date\" date=\"2012-05-09T21:38:26-04:00\">1336613906393</modifiedDate>"
                + "<modifiedTime type=\"bean\" size=\"2\">"
                + "<display>May 9, 2012 9:38pm</display>"
                + "<time type=\"number\">1336613906393</time>"
                + "</modifiedTime>"
                + "<owner>/user/admin</owner>"
                + "<password/>"
                + "<props type=\"map\" size=\"2\">"
                + "<columbia.title>COLUMBIA COLLEGE</columbia.title>"
                + "<columbia.source>sis</columbia.source>"
                + "</props>"
                + "<reference>/user/cff5b1bc-89d2-4473-b3c5-df79f372acf3</reference>"
                + "<sortName>Roth, Alexander Isaac</sortname>"
                + "<type/>"
                + "<url>"
                + "https://courseworks.columbia.edu/access/usercff5b1bc-89d2-4473-b3c5-df79f372acf3"
                + "</url>"
                + "<entityReference>/user/cff5b1bc-89d2-4473-b3c5-df79f372acf3</entityReference>"
                + "<entityURL>"
                + "https://courseworks.columbia.edu/direct/user/cff5b1bc-89d2-4473-b3c5-df79f372acf3"
                + "</entityURL>"
                + "<entityId>cff5b1bc-89d2-4473-b3c5-df79f372acf3</entityId>"
                + "<entityTitle>Alexander Isaac Roth</entityTitle>" + "</user>";
    }

    public void testUserReconstructed() {
        testUser = testReconstructor.constructUser(prepareXMLString());
        assertNotNull(testUser);
        String testUni = "air2112";
        String testID = "cff5b1bc-89d2-4473-b3c5-df79f372acf3";
        String testDisplayName = "Alexander Isaac Roth";
        String testEmailAddress = "air2112@columbia.edu";
        
        assertEquals(testUni, testUser.getUni());
        assertEquals(testID, testUser.getUserID());
        assertEquals(testDisplayName, testUser.getDisplayName());
        assertEquals(testEmailAddress, testUser.getEmailAddress());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
