package com.aisaacroth.courseworks.test;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.aisaacroth.courseworks.requesters.Requester;

import junit.framework.TestCase;

public class RequesterTest extends TestCase {
    private Requester requester;

    protected void setUp() throws Exception {
        super.setUp();
        requester = new Requester();
    }

    
    public void testGetPage() throws ParseException, IOException {
        String url = "https://sakaidev.cc.columbia.edu/portal";
        HttpResponse response = requester.getRequest(url, "JSESSIONID=ac2edec0-5d5b-4e7a-9c9b-92ea1ef38a9f.sakaidev1-ci");
        Log.d("TESTING CLASSES", EntityUtils.toString(response.getEntity()));
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
