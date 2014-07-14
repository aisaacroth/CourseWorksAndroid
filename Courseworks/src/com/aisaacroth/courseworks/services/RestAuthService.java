package com.aisaacroth.courseworks.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * A CAS Client that implements CAS' RESTful API in order to communicate with
 * the authentication server. Meant as an alternate to the method than the OAuth
 * protocols that could also be implemented.
 * 
 * @author Alexander Roth
 * @date 2014-05-19
 */

public class RestAuthService extends IntentService {

    private String tGT;

    public RestAuthService() {
        super("RestAuthService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String username = extractFromIntent(intent, "username");
        String password = extractFromIntent(intent, "password");

        try {
            login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String extractFromIntent(Intent intent, String key) {
        Bundle extras = intent.getExtras();
        return extras.getString(key);
    }

    /**
     * Logins the user into the Columbia's CAS Servers using calls to the CAS
     * RESTful API.
     * 
     * @param credentials
     *            The user's personal credentials (i.e. username and password).
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void login(String username, String password) throws IOException {
        tGT = getGrantingTicket(username, password);
        checkTGTExists();
        String serviceTicket = retrieveServiceTicket(tGT);
        checkServiceTicketExists(serviceTicket);
    }

    /**
     * Makes a POST call to CAS authentication servers in order to return a
     * Ticket Granting Resource.
     * 
     * @param username
     *            The user's username (i.e. their uni).
     * @param password
     *            The user's password.
     * @return the ticket granting ticket, which allows for the system to ask
     *         for a service ticket. Returns null on error or if the ticket was
     *         not granted.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private String getGrantingTicket(String username, String password)
            throws IOException {
        String ticket = null;
        HttpResponse response = postUserInfoToServer(username, password);
        HttpParams param = response.getParams();

        ticket = parseTicket(ticket, param, response);
        return ticket;
    }

    /**
     * Sends a POST call to Columbia's CAS Authentication server with the user's
     * information in order to request a Ticket Granting Resource.
     * 
     * @param username
     *            The user's username (i.e. their uni).
     * @param password
     *            The password
     * @return The HTTP response containing the Ticket Granting Ticket Resource.
     * @throws ClientProtocolException
     *             the client protocol exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private HttpResponse postUserInfoToServer(String username, String password)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(
                "https://casdev.cc.columbia.edu/cas/v1/tickets HTTP/1.0");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        addUserPasswordParameter(paramList, username, password);
        httpPost.setEntity(new UrlEncodedFormEntity(paramList));

        logPost(httpPost);

        HttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    private void addUserPasswordParameter(List<NameValuePair> paramList,
            String username, String password) {
        paramList.add(new BasicNameValuePair("username", username));
        paramList.add(new BasicNameValuePair("password", password));
    }

    private void logPost(HttpPost post) {
        Log.d("Courseworks", post.toString());
        Log.d("Courseworks", post.getEntity().toString());
    }

    /**
     * Gets the service ticket from the CAS Authentication server.
     * 
     * @param ticket
     *            the Ticket Granting Resource used to request a service ticket.
     * @return the service ticket
     * @throws ClientProtocolException
     *             the client protocol exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private String retrieveServiceTicket(String ticket)
            throws ClientProtocolException, IOException {
        String serviceTicket = null;
        HttpResponse response = postTicketToServer(ticket);

        if (connectionIsGood(response)) {
            serviceTicket = getServiceTicket(response);
        }
        return serviceTicket;
    }

    /**
     * Sends a POST call to Columbia's CAS Authentication server with the Ticket
     * Granting Resource in order to request a Service Ticket.
     * 
     * @param ticket
     *            The Ticket Granting Ticket Resource
     * @return The HTTP response that contains the Service Ticket.
     * @throws ClientProtocolException
     *             the client protocol exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private HttpResponse postTicketToServer(String ticket)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(
                "https://casdev.cc.columbia.edu/cas/v1/tickets" + ticket
                        + " HTTP/1.0");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        addService(paramList);
        httpPost.setEntity(new UrlEncodedFormEntity(paramList));

        logPost(httpPost);

        HttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    private void addService(List<NameValuePair> paramList) {
        paramList.add(new BasicNameValuePair("service",
                "https://courseworks.columbia.edu/portal"));
    }

    private boolean connectionIsGood(HttpResponse response) {
        return ((response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) ? true
                : false);
    }

    private String getServiceTicket(HttpResponse response) {
        return response.getEntity().toString();
    }

    private String parseTicket(String ticket, HttpParams params,
            HttpResponse response) {
        if (checkParametersExist(params) && checkResponseCreated(response)) {
            String paramString = params.getParameter("Location").toString();
            ticket = paramString.substring(paramString.lastIndexOf('/') + 1);
        }
        return ticket;
    }

    private boolean checkParametersExist(HttpParams param) {
        return (param.getParameter("Location") != null) ? true : false;
    }

    private boolean checkResponseCreated(HttpResponse response) {
        return (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_CREATED) ? true
                : false;
    }

    private void checkServiceTicketExists(String serviceTicket) {
        if (serviceTicket.isEmpty() || hasTGT()) {
            throw new NullPointerException();
        }
    }

    private void checkTGTExists() {
        if (hasTGT()) {
            throw new NullPointerException();
        }
    }

    private boolean hasTGT() {
        return (tGT.isEmpty() || tGT == null) ? true : false;
    }

    /**
     * Logouts the user out of the CAS server by calling DELETE to the RESTful
     * API.
     * 
     * @throws ClientProtocolException
     *             the client protocol exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void logout() throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(
                "http://cas.columbia.edu/cas/v1/tickets/" + tGT);
        httpClient.execute(delete);
    }

}