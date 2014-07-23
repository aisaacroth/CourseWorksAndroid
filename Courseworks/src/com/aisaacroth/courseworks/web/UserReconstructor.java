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
public class UserReconstructor extends Reconstructor {

    public UserReconstructor(User user) {
        this.user = user;
    }

    public User constructUser(String url) {
        try {
            HttpResponse response = getResponse(url);
            String file = getXMLFromResponse(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public HttpResponse getResponse(String url)
            throws ClientProtocolException, IOException {
        HttpClient serverClient = new DefaultHttpClient();
        HttpGet getUsersXml = new HttpGet(url);
        HttpResponse currentUserResponse = serverClient.execute(getUsersXml);
        return currentUserResponse;
    }

}