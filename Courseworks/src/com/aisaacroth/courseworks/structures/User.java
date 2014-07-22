package com.aisaacroth.courseworks.structures;

/**
 * Represents a User within the Courseworks system.
 * 
 * @author Alexander Roth
 * @date 2014-07-22
 */
public class User {
    
    private String uni;
    private String userID;
    private String displayName;
    private String emailAddress;
    
    public User() {
        this.uni = null;
        this.userID = null;
        this.displayName = null;
        this.emailAddress = null;
    }
    
    public void setUni(String uni) {
        this.uni = uni;
    }
    
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public void setEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getUni() {
        return uni;
    }
    
    public String getUserID() {
        return userID;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    } 

    public String getReference() {
        return "/user/" + userID;
    }
}
