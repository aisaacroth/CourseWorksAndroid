package edu.columbia.cuit.courseworks.views;

import java.util.ArrayList;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.adapters.ItemAdapter;
import edu.columbia.cuit.courseworks.utils.LogoutUtil;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;

public abstract class ItemListView<T> extends ListFragment {

    protected ArrayList<T> itemList;
    private ActionBar actionBar;
    protected ItemAdapter<T> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState, int Id) {
        View view = inflater.inflate(Id, null);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.list_signout_option) {
            getActivity().finish();
            LogoutUtil.logout(getActivity().getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected String getSessionID() {
        Intent sessionIntent = getActivity().getIntent();
        return sessionIntent.getStringExtra("JSESSION");
    }

    protected void setActionBar(int layout) {
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        actionBar.setCustomView(layout);
    }

}
