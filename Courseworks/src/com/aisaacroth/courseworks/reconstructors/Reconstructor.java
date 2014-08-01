package com.aisaacroth.courseworks.reconstructors;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.requesters.Requester;
import com.aisaacroth.courseworks.structures.User;

/**
 * Abstract class that provides some parsing functionality to reconstruct an
 * XML object.
 * 
 * @author Alexander Roth
 * @date 2014-07-29
 */
public abstract class Reconstructor {

    public User user;
    public String xmlString;
    private Requester requester;

    public void setXMLString(String url, String sessionID) throws ClientProtocolException,
            IOException {
        requester = new Requester();
        this.xmlString = getXMLFromRequester(url, sessionID);
    }

    private String getXMLFromRequester(String url, String sessionID)
            throws ClientProtocolException, IOException {
        return requester.getXMLFromResponse(requester.getRequest(url, sessionID));
    }

    public String parseFromTag(String xmlTag) {
        String startTag = "<" + xmlTag + ">";
        String endTag = "</" + xmlTag + ">";

        int indexOfOpenTag = this.xmlString.indexOf(startTag);
        int indexOfCloseTag = this.xmlString.indexOf(endTag);
        int openTagEndIndex = indexOfOpenTag + startTag.length();

        String value = this.xmlString.substring(openTagEndIndex,
                indexOfCloseTag);
        return value;
    }

}
