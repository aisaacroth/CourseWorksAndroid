package com.aisaacroth.courseworks.utils;

import java.io.IOException;
import java.net.*;

public class CookieParser {

    private static URLConnection conn;

    public static String getCookie(String url, String type) throws IOException {
        URL cookieURL = new URL(url);
        conn = cookieURL.openConnection();
        conn.connect();
        String setCookie = parseCookie(type);
        return setCookie;
    }

    private static String parseCookie(String type) {
        String headerName = null;
        String cookie = null;
        for (int i = 1; (headerName = conn.getHeaderFieldKey(i)) != null; i++) {
            cookie = extractCookieFromHeaders(headerName, type, i);
        }
        return cookie;
    }

    private static String extractCookieFromHeaders(String header, String type,
            int pos) {
        String cookie = null;
        if (isSetCookie(header, type) && connHasJSession(pos)) {
            String headerString = conn.getHeaderField(pos);
            cookie = extractCookieFromString(headerString);
        }
        return cookie;
    }

    private static boolean isSetCookie(String header, String type) {
        return (header.equals(type)) ? true : false;
    }

    private static boolean connHasJSession(int index) {
        return (conn.getHeaderField(index).contains("JSESSION")) ? true : false;
    }

    private static String extractCookieFromString(String cookie) {
        int cookieEndIndex = cookie.indexOf(";");
        return cookie.substring(0, cookieEndIndex);
    }

}