package com.aisaacroth.courseworks.web;

import java.io.IOException;
import java.util.AbstractCollection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.aisaacroth.courseworks.structures.User;

/**
 * Reconstructs the user's identity via XML returned from the Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-21
 */
public class UserReconstructor {

    private static User currentUser;

    public static User constructUser(String url) {
        try {
            HttpResponse response = getCurrentUserResponse(url);
            String file = getXMLFromResponse(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    public static HttpResponse getCurrentUserResponse(String url)
            throws ClientProtocolException, IOException {
        HttpClient serverClient = new DefaultHttpClient();
        HttpGet getUsersXml = new HttpGet(url);
        HttpResponse currentUserResponse = serverClient.execute(getUsersXml);
        return currentUserResponse;
    }

    public static String getXMLFromResponse(HttpResponse response) throws ParseException, IOException {
        HttpEntity xmlEntity = response.getEntity();
        return EntityUtils.toString(xmlEntity); 
    }
}