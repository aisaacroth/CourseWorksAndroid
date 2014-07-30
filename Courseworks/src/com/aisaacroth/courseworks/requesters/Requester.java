package com.aisaacroth.courseworks.requesters;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLConnection;

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
        CookieManager cookie = new CookieManager();
        CookieHandler.setDefault(cookie);
        HttpResponse xmlResponse = serverClient.execute(getXmlRequest);
        return xmlResponse;
    };

    public String getXMLFromResponse(HttpResponse response)
            throws ParseException, IOException {
        HttpEntity xmlEntity = response.getEntity();
        return EntityUtils.toString(xmlEntity);
    }

    public String getCookie(String url) throws IOException {
        URL cookieURL = new URL(url);
        URLConnection cookieConn = cookieURL.openConnection();
        cookieConn.connect();
        String setCookie = parseJsession(cookieConn);
        return setCookie;
    }

    private String parseJsession(URLConnection conn) {
        String headerName = null;
        String jsession = null;
        for (int i = 1; (headerName = conn.getHeaderFieldKey(i)) != null; i++) {
            jsession = extractJSessionFromHeaders(headerName, conn, i);
        }
        return jsession;
    }

    private String extractJSessionFromHeaders(String header,
            URLConnection conn, int pos) {
        String jsession = null;
        if (isSetCookie(header) && connHasJSession(conn, pos)) {
            String cookie = conn.getHeaderField(pos);
            jsession = extractJSessionFromString(cookie);
        }
        return jsession;
    }

    private boolean isSetCookie(String header) {
        return (header.equals("Set-Cookie")) ? true : false;
    }

    private boolean connHasJSession(URLConnection conn, int index) {
        return (conn.getHeaderField(index).contains("JSESSION")) ? true : false;
    }

    private String extractJSessionFromString(String cookie) {
        int cookieEndIndex = cookie.indexOf(";");
        return cookie.substring(0, cookieEndIndex);
    }

}