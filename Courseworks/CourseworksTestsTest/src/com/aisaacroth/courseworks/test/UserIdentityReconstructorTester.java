package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.web.UserReconstructor;

import junit.framework.TestCase;

public class UserIdentityReconstructorTester extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testConstructor() {
        UserReconstructor testIdentity = new UserReconstructor("air2112");
        assertNotNull(testIdentity);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
