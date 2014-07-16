package com.aisaacroth.courseworks.test;

import java.io.IOException;

import com.aisaacroth.courseworks.services.RestAuthGrant;

import junit.framework.TestCase;

public class RestAuthServiceTest extends TestCase {
    
    
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testServiceWorks() {
        String username = "air2112";
        String password = "BA115hp34";
        String serviceTicket = null;
        try {
            serviceTicket = RestAuthGrant.login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(serviceTicket);
    }
    
    public void testServiceTicketExists() {
        String username = "air2112";
        String password = "BA115hp34";
        String serviceTicket = null;
        try {
            serviceTicket = RestAuthGrant.login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(serviceTicket.contains("ST"));

    }
    

    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
