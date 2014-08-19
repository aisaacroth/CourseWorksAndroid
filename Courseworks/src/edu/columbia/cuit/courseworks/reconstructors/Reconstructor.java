package edu.columbia.cuit.courseworks.reconstructors;


import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.requesters.Requester;
import edu.columbia.cuit.courseworks.structures.User;

/**
 * Abstract class that provides some parsing functionality to reconstruct an XML
 * object.
 * 
 * @author Alexander Roth
 * @date 2014-07-29
 */
public abstract class Reconstructor {

    public User user;
    public String dataString;
    private Requester requester;

    public void setDataString(String url, String sessionID)
            throws FailedConnectionException {
        requester = new Requester();
        this.dataString = getDataFromRequester(url, sessionID);
    }

    private String getDataFromRequester(String url, String sessionID)
            throws FailedConnectionException {
        return requester.getXMLFromResponse(requester
                .getRequest(url, sessionID));
    }

    public String parseFromTag(String xmlTag) {
        String startTag = "<" + xmlTag + ">";
        String endTag = "</" + xmlTag + ">";

        int indexOfOpenTag = this.dataString.indexOf(startTag);
        int indexOfCloseTag = this.dataString.indexOf(endTag);
        int openTagEndIndex = indexOfOpenTag + startTag.length();

        String value = this.dataString.substring(openTagEndIndex,
                indexOfCloseTag);
        return value;
    }

}
