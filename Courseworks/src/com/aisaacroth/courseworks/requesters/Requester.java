package com.aisaacroth.courseworks.requesters;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public abstract class Requester {

    public abstract void postRequest();

    public HttpResponse getRequest(String url) throws ClientProtocolException,
            IOException {
        HttpClient serverClient = new DefaultHttpClient();
        HttpGet getXmlRequest = new HttpGet(url);
        HttpResponse xmlResponse = serverClient.execute(getXmlRequest);
        return xmlResponse;
    };

}
