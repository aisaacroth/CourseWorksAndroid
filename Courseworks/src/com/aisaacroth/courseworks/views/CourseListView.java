package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;

/**
 * TODO: Implement CourseView styling
 * 
 * @author Alexander Roth
 * @data 2014-06-19
 */
public class CourseListView extends ListFragment {

    String[] values = new String[] { "Course 1", "Course 2", "Course 3",
            "Course 4", "Course 5" };

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