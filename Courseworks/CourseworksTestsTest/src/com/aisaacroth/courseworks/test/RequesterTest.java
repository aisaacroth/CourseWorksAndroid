package com.aisaacroth.courseworks.test;

import java.io.IOException;

import com.aisaacroth.courseworks.utils.CASAuthUtil;

import junit.framework.TestCase;

public class RequesterTest extends TestCase {
    private String username;
    private String password;
    private String grantingTicket;
    private String serviceTicket;

    protected void setUp() throws Exception {
        super.setUp();
        initTestString();
    }

    private void initTestString() throws IOException {
        username = "air2112";
        password = "BA115hp34";
        grantingTicket = CASAuthUtil.getGrantingTicket(username, password);
        serviceTicket = CASAuthUtil.login(username, grantingTicket);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
