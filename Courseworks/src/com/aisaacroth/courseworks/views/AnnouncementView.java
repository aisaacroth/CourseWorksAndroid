package com.aisaacroth.courseworks.views;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.adapters.AnnouncementAdapter;
import com.aisaacroth.courseworks.reconstructors.AnnouncementReconstructor;
import com.aisaacroth.courseworks.structures.*;

import android.support.v4.app.ListFragment;
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
    private User currentUser;
    private AnnouncementReconstructor reconstructor;
    private ArrayList<Announcement> announcementList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        currentUser = ((Main) getActivity()).getUser();
        try {
            reconstructor = new AnnouncementReconstructor(currentUser);
            announcementList = reconstructor.constructAnnouncements("dummy");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
