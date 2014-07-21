package com.aisaacroth.courseworks.web;

/**
 * Reconstructs the user's identity through their UNI.
 * 
 * @author Alexander Roth
 * @date 2014-07-21
 */
public class UserReconstructor {
    
    private String username;

    public UserReconstructor(String username) {
        this.username = username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
}