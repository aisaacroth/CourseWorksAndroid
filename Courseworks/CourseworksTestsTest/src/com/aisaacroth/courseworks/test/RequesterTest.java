package com.aisaacroth.courseworks.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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

    
    public void testGetPage() throws ClientProtocolException, IOException {
        String url = "https://sakaidev.cc.columbia.edu/";
        HttpResponse response = requester.getRequest(url, "dummy");
        Log.d("TESTING CLASSES", EntityUtils.toString(response.getEntity()));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
