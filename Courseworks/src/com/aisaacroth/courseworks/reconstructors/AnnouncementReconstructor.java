package com.aisaacroth.courseworks.reconstructors;

import java.util.ArrayList;

import android.text.*;

import com.aisaacroth.courseworks.exceptions.FailedConnectionException;
import com.aisaacroth.courseworks.structures.Announcement;

/**
 * Reconstructs the current User's Announcements XML file returned from the
 * Courseworks API.
 * 
 * @author Alexander Roth
 * @date 2014-07-24
 */
public class AnnouncementReconstructor extends Reconstructor {

    private Announcement announcement;
    private ArrayList<Announcement> announcementList;

    public AnnouncementReconstructor() {
        this.announcement = null;
        this.dataString = null;
    }

    public ArrayList<Announcement> constructAnnouncements(String url,
            String sessionID) throws FailedConnectionException {
        String[] xmlArray = prepareXML(url, sessionID);
        announcementList = fetchAnnouncements(xmlArray);
        return announcementList;
    }

    private String[] prepareXML(String url, String sessionID)
            throws FailedConnectionException {
        setDataString(url, sessionID);
        String[] xmlArray = parseAnnouncementStrings();
        return xmlArray;
    }

    public String[] parseAnnouncementStrings() {
        removeCollectionTag();
        String[] announcementXMLs = separateplitAnnouncements();
        removeAnnouncementTag(announcementXMLs);
        return announcementXMLs;
    }

    private void removeCollectionTag() {
        int startIndex = this.dataString.indexOf(">") + 1;
        int endIndex = this.dataString.indexOf("</announcement_collection");
        this.dataString = this.dataString.substring(startIndex, endIndex);
    }

    private String[] separateplitAnnouncements() {
        return dataString.split("</announcement>");
    }

    private void removeAnnouncementTag(String[] announcements) {
        for (int i = 0; i < announcements.length; i++) {
            int startIndex = announcements[i].indexOf(">") + 1;
            announcements[i] = announcements[i].substring(startIndex);
        }
    }

    private ArrayList<Announcement> fetchAnnouncements(String[] xmlArray) {
        ArrayList<Announcement> announcements = new ArrayList<Announcement>();

        for (int i = 0; i < xmlArray.length - 1; i++) {
            this.announcement = new Announcement();
            this.dataString = xmlArray[i];
            setAnnouncement();
            announcements.add(announcement);
        }
        return announcements;
    }

    private void setAnnouncement() {
        announcement.setPostedDate(parseDateString());
        announcement.setTitle(parseFromTag("title"));
        announcement.setProfessorName(parseFromTag("createdByDisplayName"));
        announcement.setBodyText(formatBodyText());
        announcement.setClassId(parseFromTag("siteId"));
    }

    public String parseDateString() {
        String dateSubTag = getDateLine();
        String dashDate = getDate(dateSubTag);
        String dotDate = replaceDashesWithDots(dashDate);
        return dotDate;
    }

    private String getDateLine() {
        String startTag = "<createdOn";
        String endTag = "</createdOn>";
        int startTagLength = dataString.indexOf(startTag) + startTag.length();
        int endTagIndex = dataString.indexOf(endTag);
        String dateLine = dataString.substring(startTagLength, endTagIndex);
        return dateLine;
    }

    private String getDate(String dateString) {
        String dateAttritube = "date=\'";
        int startDateLength = dateString.indexOf(dateAttritube)
                + dateAttritube.length();
        int endIndexDate = dateString.indexOf("T");
        return dateString.substring(startDateLength, endIndexDate);
    }

    private String replaceDashesWithDots(String dateString) {
        return dateString.replace('-', '.');
    }

    private String formatBodyText() {
        String xmlBodyString = parseFromTag("body");
        Spanned spannedText = Html.fromHtml(xmlBodyString);
        return spannedText.toString();
    }
}