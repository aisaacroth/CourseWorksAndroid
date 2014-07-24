package com.aisaacroth.courseworks.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.structures.User;
import com.aisaacroth.courseworks.web.AnnouncementReconstructor;

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

    public void testConstructor() {
        assertNotNull(testAR);
    }

    public void testConnection() throws ClientProtocolException, IOException {
        testAR.response = testAR.getResponse(url);
        assertNotNull(testAR.response);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
