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
        this.xmlString = null;
    }

    private void setUser() {
        user.setUni(parseFromTag("displayId"));
        user.setUserID(parseFromTag("id"));
        user.setDisplayName(parseFromTag("displayName"));
        user.setEmailAddress(parseFromTag("email"));
    }

    public User constructUser(String url, String sessionID)
            throws FailedConnectionException {
        setXMLString(url, sessionID);
        setUser();
        return user;
    }

}