package com.aisaacroth.courseworks.test;

import java.io.IOException;

import com.aisaacroth.courseworks.requesters.Requester;
import com.aisaacroth.courseworks.utils.CASAuthUtil;

import junit.framework.TestCase;

public class RequesterTest extends TestCase {
    private String username;
    private String password;
    private String grantingTicket;
    private Requester testRequester;

    protected void setUp() throws Exception {
        super.setUp();
        initTestString();
        testRequester = new Requester();
    }

    private void initTestString() throws IOException {
        username = "air2112";
        password = "BA115hp34";
        grantingTicket = CASAuthUtil.getGrantingTicket(username, password);
    }

    public void testGrabJsessionCookie() throws IOException {
        String serviceTicket = null;
        serviceTicket = CASAuthUtil.login(username, grantingTicket);
        String url = "https://sakaidev.cc.columbia.edu/sakai-login-tool/container?force.login=yes&ticket="
                + serviceTicket;
        String cookie = testRequester.getCookie(url);
        assertTrue(cookie.contains("JSESSION"));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
