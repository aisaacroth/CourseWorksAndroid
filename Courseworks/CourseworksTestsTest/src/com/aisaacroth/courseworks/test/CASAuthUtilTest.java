package com.aisaacroth.courseworks.test;

import java.io.IOException;

import com.aisaacroth.courseworks.utils.CASAuthUtil;

import junit.framework.TestCase;

public class CASAuthUtilTest extends TestCase {
    private String username;
    private String password;
    private String grantingTicket;

    protected void setUp() throws Exception {
        super.setUp();
        initTestStrings();
    }

    private void initTestStrings() throws IOException {
        username = "air2112";
        password = "BA115hp34";
        grantingTicket = CASAuthUtil.getGrantingTicket(username,
                password);
    }

    public void testServiceWorks() {
        String serviceTicket = null;
        try {
            serviceTicket = CASAuthUtil
                    .login(username, grantingTicket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(serviceTicket);
    }

    public void testServiceTicketExists() {
        String serviceTicket = null;
        try {
            serviceTicket = CASAuthUtil
                    .login(username, grantingTicket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(serviceTicket.contains("ST"));
    }

    public void testGetGrantingTicket() {
        assertNotNull(grantingTicket);
        assertTrue(grantingTicket.contains("TGT"));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
