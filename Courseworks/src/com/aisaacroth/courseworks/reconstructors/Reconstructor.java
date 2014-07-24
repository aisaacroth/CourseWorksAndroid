package com.aisaacroth.courseworks.reconstructors;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.util.EntityUtils;

import com.aisaacroth.courseworks.structures.User;

public abstract class Reconstructor {

    public User user;
    public String xmlString;

    public String getXMLFromResponse(HttpResponse response)
            throws ParseException, IOException {
        HttpEntity xmlEntity = response.getEntity();
        return EntityUtils.toString(xmlEntity);
    }

    public String parseFromTag(String xmlTag) {
        String startTag = "<" + xmlTag + ">";
        String endTag = "</" + xmlTag + ">";

        int indexOfOpenTag = this.xmlString.indexOf(startTag);
        int indexOfCloseTag = this.xmlString.indexOf(endTag);
        int openTagEndIndex = indexOfOpenTag + startTag.length();

        String value = this.xmlString.substring(openTagEndIndex, indexOfCloseTag);
        return value;
    }

}
