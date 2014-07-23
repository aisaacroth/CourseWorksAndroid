package com.aisaacroth.courseworks.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.web.UserReconstructor;

import junit.framework.TestCase;

public class UserReconstructorTester extends TestCase {
    private String url;
    private HttpResponse response;

    protected void setUp() throws Exception {
        super.setUp();
        url = "https://courseworks.columbia.edu/welcome";
    }
    
    public void testGetCurrentUserResponse() throws ClientProtocolException, IOException {
        response = UserReconstructor.getCurrentUserResponse(url);
        assertNotNull(response);
    }
    
    public void testGetXMLFromResponse() throws ClientProtocolException, IOException {
        response = UserReconstructor.getCurrentUserResponse(url);
        String xml = UserReconstructor.getXMLFromResponse(response);
        assertNotNull(xml);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
