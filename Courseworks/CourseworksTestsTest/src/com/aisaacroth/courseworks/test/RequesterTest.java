package com.aisaacroth.courseworks.test;

import java.io.IOException;

import com.aisaacroth.courseworks.requesters.Requester;
import com.aisaacroth.courseworks.utils.CASAuthUtil;
import com.aisaacroth.courseworks.utils.CookieParser;

import junit.framework.TestCase;

public class RequesterTest extends TestCase {
    private String username;
    private String password;
    private String grantingTicket;

    protected void setUp() throws Exception {
        super.setUp();
        initTestString();
    }

    private void initTestString() throws IOException {
        username = "air2112";
        password = "BA115hp34";
        grantingTicket = CASAuthUtil.getGrantingTicket(username, password);
    }

    public void testGrabJsessionCookie() throws IOException {
        String serviceTicket = null;
        serviceTicket = CASAuthUtil.login(username, grantingTicket);
        String url = "https://sakaidev.cc.columbia.edu/sakai-login-tool/container?ticket="
                + serviceTicket;
        String cookie = CookieParser.getCookie(url, "Set-Cookie");
        assertTrue(cookie.contains("JSESSION"));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
