package com.aisaacroth.courseworks.views;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.AnnouncementAdapter;
import com.aisaacroth.courseworks.feeds.AnnouncementFeed;
import com.aisaacroth.courseworks.structures.*;

import android.support.v4.app.ListFragment;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;

/**
 * Maintains a dynamic list of announcements that are pushed to the user each
 * time he/she logs into the application. Displayed as the initial tab on the
 * Main Activity.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 */
public class AnnouncementListView extends ListFragment {
    private ArrayList<Announcement> announcementList;
    private ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        setActionBar();
        getAnnouncements();
        setHasOptionsMenu(true);
        AnnouncementAdapter adapter = new AnnouncementAdapter(
                this.getActivity(), announcementList);
        setListAdapter(adapter);
        View view = inflater.inflate(R.layout.fragment_announcement_list_view,
                null);
        return view;
    }

    private void setActionBar() {
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        actionBar.setCustomView(R.layout.announcement_list_actionbar);
    }


    private void getAnnouncements() {
        String sessionID = getSessionID();
        AnnouncementFeed announcementFeed = new AnnouncementFeed();
        try {
            announcementList = announcementFeed.execute(sessionID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String getSessionID() {
        Intent sessionIntent = getActivity().getIntent();
        return sessionIntent.getStringExtra("JSESSION");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.announcement_list_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent announcement = new Intent(getActivity(), AnnouncementView.class);
        announcement.putExtra("Announcement", announcementList.get(position));
        startActivity(announcement);
    }

}
