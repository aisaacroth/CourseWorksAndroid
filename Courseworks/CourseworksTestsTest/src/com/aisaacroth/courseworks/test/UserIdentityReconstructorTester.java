package com.aisaacroth.courseworks.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.aisaacroth.courseworks.web.UserReconstructor;

import junit.framework.TestCase;

public class UserIdentityReconstructorTester extends TestCase {
    private UserReconstructor testIdentity;

    protected void setUp() throws Exception {
        super.setUp();
        testIdentity = new UserReconstructor("air2112");
    }
    
    @SmallTest
    public void testConstructor() {
        assertNotNull(testIdentity);
    }

    @SmallTest   
    public void testSettingAndGetting() {
        testIdentity.setUsername("name");
        assertEquals("name", testIdentity.getUsername());
        assertNotNull(testIdentity.getUsername());
    }
    
    @SmallTest
    public void testTimerActivates() {
        
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
