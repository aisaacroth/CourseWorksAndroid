package edu.columbia.cuit.courseworks.requesters;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;

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

    public HttpResponse getRequest(String url, String sessionId)
            throws FailedConnectionException {
        HttpClient serverClient = new DefaultHttpClient();
        HttpGet getDataRequest = new HttpGet(url);
        getDataRequest.setHeader("Cookie", sessionId);
        logRequestHeaders(getDataRequest);
        HttpResponse dataResponse = recieveResponse(serverClient, getDataRequest);
        return dataResponse;
    };

    public String getDataFromResponse(HttpResponse response)
            throws FailedConnectionException {
        HttpEntity dataEntity = response.getEntity();
        return parseEntityToString(dataEntity);
    }

    private void logRequestHeaders(HttpGet xmlRequest) {
        for (Header header : xmlRequest.getAllHeaders()) {
            Log.d("REQUEST HEADER", header.getName() + ": " + header.getValue());
        }
    }

    private HttpResponse recieveResponse(HttpClient client, HttpGet request)
            throws FailedConnectionException {
        try {
            return client.execute(request);
        } catch (ClientProtocolException e) {
            throw new FailedConnectionException(e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FailedConnectionException(e.getLocalizedMessage());
        }
    }

    private String parseEntityToString(HttpEntity entity)
            throws FailedConnectionException {
        try {
            return EntityUtils.toString(entity);
        } catch (ParseException e) {
            throw new FailedConnectionException(e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FailedConnectionException(e.getLocalizedMessage());
        }
    }

}