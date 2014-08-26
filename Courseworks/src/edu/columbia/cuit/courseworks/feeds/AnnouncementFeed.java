package edu.columbia.cuit.courseworks.feeds;

import java.util.ArrayList;

import edu.columbia.cuit.courseworks.exceptions.FailedConnectionException;
import edu.columbia.cuit.courseworks.reconstructors.AnnouncementReconstructor;
import edu.columbia.cuit.courseworks.structures.Announcement;

import android.os.AsyncTask;

/**
 * Accesses the Courseworks API and pulls in all information relating to the
 * current user's announcements.
 * 
 * @author Alexander Roth
 * @date 2014-08-01
 */
public class AnnouncementFeed extends
        AsyncTask<String, Void, ArrayList<Announcement>> {
    private AnnouncementReconstructor announcementReconstructor;
    private ArrayList<Announcement> announcementList;
    private String url;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // TODO: Switch to production servers
        url = "https://sakaidev.cc.columbia.edu/direct/announcement/user.xml?n=20&d=100";
        announcementReconstructor = new AnnouncementReconstructor();
    }

    @Override
    protected ArrayList<Announcement> doInBackground(String... params) {
        try {
            announcementList = announcementReconstructor
                    .constructAnnouncements(url, params[0]);
        } catch (FailedConnectionException e) {
            e = new FailedConnectionException(
                    "There appears to be a connection error.");
            e.printStackTrace();
        }

        return announcementList;
    }

}
