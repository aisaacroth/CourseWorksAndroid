package com.aisaacroth.courseworks.web;

import com.aisaacroth.courseworks.structures.User;

/**
 * Reconstructs the user's identity via XML returned from the Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-21
 */
public class UserReconstructor {
    
    private User currentUser;
    
    public User constructUser() {
        return currentUser;
    }
}