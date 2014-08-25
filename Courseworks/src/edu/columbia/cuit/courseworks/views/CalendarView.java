package edu.columbia.cuit.courseworks.views;

import edu.columbia.cuit.courseworks.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;

/**
 * TODO: Implement the CalendarView.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 */
public class CalendarView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar_view,
                container, false);
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}