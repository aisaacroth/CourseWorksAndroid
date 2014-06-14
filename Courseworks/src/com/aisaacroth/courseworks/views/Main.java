package com.aisaacroth.courseworks.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aisaacroth.courseworks.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/*******************************************************************************
 * The Home class. TODO: Will act as the homepage for the Courseworks app.
 * 
 * @author Alexander Roth
 * @date 2014-02-24
 ******************************************************************************/
public class Main extends Activity {

	@SuppressWarnings("rawtypes")
	private Class[] activities = { CourseView.class, AnnouncementView.class,
			CalendarView.class, Settings.class };
	List<Map<String, String>> notificationList = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initList();

		ListView lv = (ListView) findViewById(R.id.listView);

		SimpleAdapter simpleAdpt = new SimpleAdapter(this, notificationList,
				android.R.layout.simple_list_item_1,
				new String[] { "notification" },
				new int[] { android.R.id.text1 });

		lv.setAdapter(simpleAdpt);
		registerForContextMenu(lv);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				TextView clickedView = (TextView) view;
				Toast.makeText(
						Main.this,
						"Item with id [" + id + "] - Position [" + position
								+ "]", Toast.LENGTH_SHORT).show();
			}
		});

		// finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initList() {
		for (int i = 0; i < 10; i++) {
			notificationList
					.add(createNotification("notification", "Test" + i));
		}
	}

	private HashMap<String, String> createNotification(String key, String name) {
		HashMap<String, String> notification = new HashMap<String, String>();
		notification.put(key, name);
		return notification;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	}

}