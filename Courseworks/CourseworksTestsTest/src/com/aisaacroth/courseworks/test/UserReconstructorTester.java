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
        testReconstructor = new UserReconstructor(testUser);
    }
    
    public void testConstructor() {
        assertNotNull(testReconstructor);
    }
    
    public void testGetCurrentUserResponse() throws ClientProtocolException, IOException {
        response = testReconstructor.getResponse(url);
        assertNotNull(response);
    }
    
    public void testGetXMLFromResponse() throws ClientProtocolException, IOException {
        response = testReconstructor.getResponse(url);
        String xml = testReconstructor.getXMLFromResponse(response);
        assertNotNull(xml);
    }
    
    public void testUserReconstructed() {
        testReconstructor.constructUser(url);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
