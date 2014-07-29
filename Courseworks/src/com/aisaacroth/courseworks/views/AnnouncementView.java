package com.aisaacroth.courseworks.views;

import java.io.Serializable;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.structures.User;

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
    // private static final String USER_KEY = "user_key";
    // private User currentUser;

    String[] values = new String[] {};

    // public static AnnouncementView newInstance(User currentUser) {
    // AnnouncementView fragment = new AnnouncementView();
    // Bundle bundle = new Bundle();
    // bundle.putSerializable(USER_KEY, (Serializable) currentUser);
    // fragment.setArguments(bundle);
    // return fragment;
    // }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // currentUser = (User) getArguments().getSerializable(USER_KEY);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.activity_list_item,
                values);
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
