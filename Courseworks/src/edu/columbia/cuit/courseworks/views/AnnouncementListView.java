package edu.columbia.cuit.courseworks.views;

import java.util.concurrent.ExecutionException;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.AnnouncementAdapter;
import edu.columbia.cuit.courseworks.feeds.AnnouncementFeed;
import edu.columbia.cuit.courseworks.structures.*;

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
public class AnnouncementListView extends ItemListView<Announcement> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        setActionBar(R.layout.announcement_list_actionbar);
        getAnnouncements();
        setHasOptionsMenu(true);
        adapter = new AnnouncementAdapter(this.getActivity(), itemList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState,
                R.layout.fragment_announcement_list_view);
    }

    private void getAnnouncements() {
        String sessionID = getSessionID();
        AnnouncementFeed announcementFeed = new AnnouncementFeed();
        try {
            itemList = announcementFeed.execute(sessionID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent announcement = new Intent(getActivity(), AnnouncementView.class);
        announcement.putExtra("Announcement", itemList.get(position));
        startActivity(announcement);
    }

}
