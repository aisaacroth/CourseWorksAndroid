package com.aisaacroth.courseworks.views;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.AnnouncementAdapter;
import com.aisaacroth.courseworks.feeds.AnnouncementFeed;
import com.aisaacroth.courseworks.structures.*;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;

/**
 * Maintains a dynamic list of announcements that are pushed to the user each
 * time he/she logs into the application. Displayed as the initial tab on the
 * Main Activity.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 */
public class AnnouncementView extends ListFragment {
    private ArrayList<Announcement> announcementList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        Intent sessionIntent = getActivity().getIntent();
        String sessionID = sessionIntent.getStringExtra("JSESSION");
        AnnouncementFeed announcementFeed = new AnnouncementFeed();
        
        try {
            announcementList = announcementFeed.execute(sessionID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
        AnnouncementAdapter adapter = new AnnouncementAdapter(
                this.getActivity(), announcementList);
        setListAdapter(adapter);
        View view = inflater.inflate(R.layout.fragment_announcement_view, null);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
