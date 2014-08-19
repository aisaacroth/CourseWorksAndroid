package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.reconstructors.UserReconstructor;

import junit.framework.TestCase;

public class UserReconstructorTest extends TestCase {
    private UserReconstructor testUR;

    protected void setUp() throws Exception {
        super.setUp();

        testUR = new UserReconstructor();
    }

    public void testConstructor() {
        assertNotNull(testUR);
    }

    public void testParseAllFromXMLString() throws Exception {
        testUR.dataString = prepareXMLString();
        String actualUni = testUR.parseFromTag("displayId");
        String actualId = testUR.parseFromTag("id");
        String actualDisplayName = testUR.parseFromTag("displayName");
        String actualEmailAddress = testUR.parseFromTag("email");
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

    public void testParseDisplayNameFromString() {
        String testDisplayNameXml = "<displayName>Alexander Isaac Roth</displayName>";
        String testDisplayName = "Alexander Isaac Roth";
        testUR.dataString = testDisplayNameXml;
        String actualDisplayName = testUR.parseFromTag("displayName");
        assertEquals(testDisplayName, actualDisplayName);
    }

    public void testParseEmailAddressFromString() {
        String testEmailAddressXml = "<email>air2112@columbia.edu</email>";
        String testEmailAddress = "air2112@columbia.edu";
        testUR.dataString = testEmailAddressXml;
        String actualEmailAddress = testUR.parseFromTag("email");
        assertEquals(testEmailAddress, actualEmailAddress);
    }

    public void testParseUNIFromString() {
        String testUniXml = "<displayId>air2112</displayId>";
        testUR.dataString = testUniXml;
        String testUni = testUR.parseFromTag("displayId");
        assertEquals("air2112", testUni);
    }

    public void testParseUserIdFromString() {
        String testIdXml = "<id>cff5b1bc-89d2-4473-b3c5-df79f372acf3</id>";
        String testID = "cff5b1bc-89d2-4473-b3c5-df79f372acf3";
        testUR.dataString = testIdXml;
        String actualId = testUR.parseFromTag("id");
        assertEquals(testID, actualId);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
