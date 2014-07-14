package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;

/**
 * Maintains a dynamic list of announcements that are pushed to the user each
 * time he/she logs into the application. Displayed as the initial tab on the
 * Main Activity.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 */
public class AnnouncementView extends ListFragment {

    String[] values = new String[] { "Notification 1", "Notification 2",
            "Notification 3", "Notification 4", "Notification 5",
            "Notification 6", "Notification 7", "Notification 8" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                values);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

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
