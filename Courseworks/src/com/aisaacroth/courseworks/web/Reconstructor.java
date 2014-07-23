package com.aisaacroth.courseworks.web;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.aisaacroth.courseworks.structures.User;

public abstract class Reconstructor {

    public User user;
    public String xmlString;

    public HttpResponse getResponse(String url) throws ClientProtocolException,
            IOException {
        HttpClient serverClient = new DefaultHttpClient();
        HttpGet getXmlRequest = new HttpGet(url);
        HttpResponse xmlResponse = serverClient.execute(getXmlRequest);
        return xmlResponse;
    }

    public String getXMLFromResponse(HttpResponse response)
            throws ParseException, IOException {
        HttpEntity xmlEntity = response.getEntity();
        return EntityUtils.toString(xmlEntity);
    }

    public String parseFromTag(String xmlTag) {
        String startTag = "<" + xmlTag + ">";
        String endTag = "</" + xmlTag + ">";

        int indexOfOpenTag = xmlString.indexOf(startTag);
        int indexOfCloseTag = xmlString.indexOf(endTag);
        int openTagEndIndex = indexOfOpenTag + startTag.length();

        String value = xmlString.substring(openTagEndIndex, indexOfCloseTag);
        return value;
    }

}
