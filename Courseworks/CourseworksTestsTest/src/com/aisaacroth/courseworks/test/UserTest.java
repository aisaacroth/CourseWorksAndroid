package com.aisaacroth.courseworks.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.aisaacroth.courseworks.structures.User;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    private User testUser;
    private String uni;
    private String userID;
    private String displayName;
    private String emailAddress;

    protected void setUp() throws Exception {
        super.setUp();
        createTestVariables();
        initTestUser();
    }
    
    private void createTestVariables() {
        testUser = new User();
        uni = "air2112";
        userID = "cff5b1bc-89d2-4473-b3c5-df79f372acf3";
        displayName = "Alexander Isaac Roth";
        emailAddress = "air2112@columbia.edu";
    }
    
    private void initTestUser() {
        testUser.setDisplayName(displayName);
        testUser.setEmailAddress(emailAddress);
        testUser.setUni(uni);
        testUser.setUserID(userID);
    }

    @SmallTest
    public void testConstructor() {
        assertNotNull(testUser);
    }

    @SmallTest
    public void testDisplayName() {
        assertEquals(displayName, testUser.getDisplayName());
    }

    @SmallTest
    public void testEmail() {
        assertEquals(emailAddress, testUser.getEmailAddress());
    }

    @SmallTest
    public void testReference() {
        assertEquals("/user/" + userID, testUser.getReference());
    }

    @SmallTest
    public void testUni() {
        assertEquals(uni, testUser.getUni());
    }

    @SmallTest
    public void testUserID() {
        assertEquals(userID, testUser.getUserID());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
