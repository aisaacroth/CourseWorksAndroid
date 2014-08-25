package edu.columbia.cuit.courseworks.reconstructors;

import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.structures.User;

/**
 * Reconstructs the user's identity via XML returned from the Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-21
 */
public class UserReconstructor extends Reconstructor {

    public UserReconstructor() {
        this.user = new User();
        this.dataString = null;
    }

    private void setUser() {
        user.setUni(parseFromXMLTag("displayId"));
        user.setUserID(parseFromXMLTag("id"));
        user.setDisplayName(parseFromXMLTag("displayName"));
        user.setEmailAddress(parseFromXMLTag("email"));
    }

    public User constructUser(String url, String sessionID)
            throws FailedConnectionException {
        setDataString(url, sessionID);
        setUser();
        return user;
    }

}