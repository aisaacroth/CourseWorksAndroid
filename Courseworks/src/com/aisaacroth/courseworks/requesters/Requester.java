package com.aisaacroth.courseworks.requesters;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * COMMAND-style class that launches commands to the given server.
 * 
 * @author Alexander Roth
 * @date 2014-07-29
 */
public class Requester {

    public Requester() {

    }

    public HttpResponse getRequest(String url) throws ClientProtocolException,
            IOException {
        HttpClient serverClient = new DefaultHttpClient();
        HttpGet getXmlRequest = new HttpGet(url);
        for (Header header : getXmlRequest.getAllHeaders()) {
            Log.d("REQUEST HEADER", header.getName() + ": " + header.getValue());
        }
        HttpResponse xmlResponse = serverClient.execute(getXmlRequest);
        return xmlResponse;
    };

    public String getXMLFromResponse(HttpResponse response)
            throws ParseException, IOException {
        HttpEntity xmlEntity = response.getEntity();
        return EntityUtils.toString(xmlEntity);
    }
    
}