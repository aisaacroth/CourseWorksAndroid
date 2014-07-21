package com.aisaacroth.courseworks.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * A CAS Client that implements CAS' RESTful API in order to communicate with
 * the authentication server. Meant as an alternate to the method than the OAuth
 * protocols that could also be implemented.
 * 
 * @author Alexander Roth
 * @date 2014-05-19
 */

public class CASRestAuthenticator {

    private static String tGT;
    private static final int LOCATION = 2;

    public static String getGrantingTicket(String username, String password)
            throws IOException {
        String ticket = null;
        HttpResponse response = postUserInfoToServer(username, password);
        ticket = parseTicket(ticket, response);
        return ticket;
    }

    private static HttpResponse postUserInfoToServer(String username,
            String password) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();

        // TODO: Switch to Production server. Currently testing on Dev servers.
        HttpPost httpPost = new HttpPost(
                "https://casdev.cc.columbia.edu/cas/v1/tickets");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        addUserPasswordParameter(paramList, username, password);
        httpPost.setEntity(new UrlEncodedFormEntity(paramList));

        logPost(httpPost);

        HttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    private static void addUserPasswordParameter(List<NameValuePair> paramList,
            String username, String password) {
        paramList.add(new BasicNameValuePair("username", username));
        paramList.add(new BasicNameValuePair("password", password));
    }

    private static void logPost(HttpPost post) {
        Log.d("Courseworks", post.getURI().toString()
                + post.getParams().toString());
        Log.d("Courseworks", post.getEntity().toString());
    }

    private static String parseTicket(String ticket, HttpResponse response) {
        if (checkResponseCreated(response) && checkLocationExists(response)) {
            Header[] allHeaders = response.getAllHeaders();
            String ticketWithHeader = allHeaders[LOCATION].toString();
            ticket = ticketWithHeader.substring(ticketWithHeader
                    .lastIndexOf('/') + 1);
        }
        return ticket;
    }

    private static boolean checkResponseCreated(HttpResponse response) {
        return (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_CREATED) ? true
                : false;
    }

    private static boolean checkLocationExists(HttpResponse response) {
        return (response.getHeaders("Location") != null) ? true : false;
    }

    public static String login(String username, String ticket)
            throws IOException {
        tGT = ticket;
        String serviceTicket = null;
        if (ticketGrantingTicketExists()) {
            serviceTicket = getServiceTicket(tGT);
        }

        return serviceTicket;
    }

    private static boolean ticketGrantingTicketExists() {
        return (hasTGT()) ? true : false;
    }

    private static boolean hasTGT() {
        return (tGT == null || tGT.isEmpty()) ? false : true;
    }

    private static String getServiceTicket(String ticket)
            throws ClientProtocolException, IOException {
        String serviceTicket = null;
        HttpResponse response = postTicketToServer(ticket);

        if (connectionIsGood(response)) {
            serviceTicket = getServiceTicketFromResponse(response);
        }
        return serviceTicket;
    }

    private static HttpResponse postTicketToServer(String ticket)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(
                "https://casdev.cc.columbia.edu/cas/v1/tickets/" + ticket);

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        addService(paramList);
        httpPost.setEntity(new UrlEncodedFormEntity(paramList));

        logPost(httpPost);

        HttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    private static void addService(List<NameValuePair> paramList) {
        paramList.add(new BasicNameValuePair("service",
                "https://courseworks.columbia.edu/sakai-login-tool/container"));
    }

    private static boolean connectionIsGood(HttpResponse response) {
        return ((response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) ? true
                : false);
    }

    private static String getServiceTicketFromResponse(HttpResponse response) {
        HttpEntity serviceEntity = response.getEntity();
        String serviceTicket = null;
        try {
            serviceTicket = EntityUtils.toString(serviceEntity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceTicket;
    }

    public void logout() throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(
                "http://cas.columbia.edu/cas/v1/tickets/" + tGT);
        httpClient.execute(delete);
    }

}