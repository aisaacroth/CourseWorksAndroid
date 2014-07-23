package com.aisaacroth.courseworks.web;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;

import com.aisaacroth.courseworks.structures.User;

public abstract class Reconstructor {

    public User user;
    
    public abstract HttpResponse getResponse(String url) throws ClientProtocolException, IOException;

    public String getXMLFromResponse(HttpResponse response)
            throws ParseException, IOException {
        HttpEntity xmlEntity = response.getEntity();
        return EntityUtils.toString(xmlEntity);
    }
    
    public String getFromTag(String xmlTag) {
        return null;
    }
}
