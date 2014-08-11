package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.structures.Announcement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

public class AnnouncementView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_view);
        Intent announcementData = getIntent();
        Announcement announcement = (Announcement) announcementData
                .getSerializableExtra("Announcement");

        Log.d("ANNOUNCEMENT IN VIEW", "Title: " + announcement.getTitle());

        TextView titleText = (TextView) findViewById(R.id.announcement_title);
        titleText.setText(announcement.getTitle());
        TextView classText = (TextView) findViewById(R.id.announcement_class);
        classText.setText(announcement.getClassId());
        TextView bodyText = (TextView) findViewById(R.id.announcement_body);
        bodyText.setText(Html.fromHtml(announcement.getBodyText()));
        bodyText.setMovementMethod(LinkMovementMethod.getInstance());
        TextView dateText = (TextView) findViewById(R.id.posted_date);
        dateText.setText("Posted " + announcement.getPostedDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.announcement_view, menu);
        return true;
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