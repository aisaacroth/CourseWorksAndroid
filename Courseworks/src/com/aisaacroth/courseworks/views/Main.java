package com.aisaacroth.courseworks.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aisaacroth.courseworks.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
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

	List<Map<String, String>> notificationList = new ArrayList<Map<String, String>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
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

		final Button courses = (Button) findViewById(R.id.currentCourses);
		final Button announcements = (Button) findViewById(R.id.announcements);
		final Button calendar = (Button) findViewById(R.id.calendar);
		final Button settings = (Button) findViewById(R.id.settings);

		courses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent navCourse = new Intent(Main.this, CourseView.class);
				startActivity(navCourse);
			}
		});

		announcements.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent navAnnouncment = new Intent(Main.this,
						AnnouncementView.class);
				startActivity(navAnnouncment);

			}
		});

		calendar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent navCalendar = new Intent(Main.this, CalendarView.class);
				startActivity(navCalendar);
			}
		});

		settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent navSettings = new Intent(Main.this, Settings.class);
				startActivity(navSettings);
			}
		});

		// finish();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Initializes the central list on the home page with place holder
	 * notifications. TODO: Create method that dynamically creates
	 * notifications.
	 */
	private void initList() {
		for (int i = 0; i < 10; i++) {
			notificationList.add(createNotification("notification",
					"Notification" + i));
		}
	}

	/**
	 * Creates a notification object that will be placed into the notification
	 * section of the home page.
	 * 
	 * @param key
	 *            the key that will be used to access the notification.
	 * @param name
	 *            the name of the notification.
	 * @return the hash map that points to the notification.
	 */
	private HashMap<String, String> createNotification(String key, String name) {
		HashMap<String, String> notification = new HashMap<String, String>();
		notification.put(key, name);
		return notification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
	 * android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
	}

}