package com.aisaacroth.courseworks.feeds;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.reconstructors.AnnouncementReconstructor;
import com.aisaacroth.courseworks.structures.Announcement;

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
        
        //TODO: Switch to production servers
        url = "https://sakaidev.cc.columbia.edu/direct/announcement/user.xml?n=20&d=100";
        try {
            announcementReconstructor = new AnnouncementReconstructor();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayList<Announcement> doInBackground(String... params) {
        try {
            announcementList = announcementReconstructor
                    .constructAnnouncements(url, params[0]);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return announcementList;
    }

}
