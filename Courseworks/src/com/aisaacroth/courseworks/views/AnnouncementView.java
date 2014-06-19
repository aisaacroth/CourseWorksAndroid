package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/*******************************************************************************
 * The Class AnnouncementView. TODO: Add in the Announcement ListView.
 * 
 * @author Alexander Roth
 * @date 2014-06-19
 ******************************************************************************/
public class AnnouncementView extends ListFragment {

	boolean mDualPane;
	int mCurCheckPosition = 0;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
	 */
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

	/***************************************************************************
	 * A placeholder fragment containing a simple view.
	 **************************************************************************/
	public static class PlaceholderFragment extends Fragment {

		/***********************************************************************
		 * Instantiates a new placeholder fragment.
		 **********************************************************************/
		public PlaceholderFragment() {
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_announcement_view, container, false);
			return rootView;
		}
	}
}
