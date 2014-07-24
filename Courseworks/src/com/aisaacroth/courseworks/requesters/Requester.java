package com.aisaacroth.courseworks.requesters;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Requester {

    public Requester() {
        
    }

    public HttpResponse getRequest(String url)
            throws ClientProtocolException, IOException {
        HttpClient serverClient = new DefaultHttpClient();
        HttpGet getXmlRequest = new HttpGet(url);
        HttpResponse xmlResponse = serverClient.execute(getXmlRequest);
        return xmlResponse;
    };

    public String getXMLFromResponse(HttpResponse response)
            throws ParseException, IOException {
        HttpEntity xmlEntity = response.getEntity();
        return EntityUtils.toString(xmlEntity);
    }
}