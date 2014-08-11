package com.aisaacroth.courseworks.views;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.structures.Announcement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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

        setTextViews(announcement);
    }
    
    private void setTextViews(Announcement announcement) {
        setTitleTextView(announcement.getTitle());
        setClassTextView(announcement.getClassId());
        setBodyTextView(Html.fromHtml(announcement.getBodyText()));
        setDateTextView(announcement.getPostedDate());
    }
    
    private void setTitleTextView(String title) {
        TextView titleText = (TextView) findViewById(R.id.announcement_title);
        titleText.setText(title);
    }
    
    private void setClassTextView(String className) {
        TextView classText = (TextView) findViewById(R.id.announcement_class);
        classText.setText(className);
    }
    
    private void setBodyTextView(Spanned bodyString) {
        TextView bodyText = (TextView) findViewById(R.id.announcement_body);
        bodyText.setText(bodyString);
        bodyText.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    private void setDateTextView(String date) {
        TextView dateText = (TextView) findViewById(R.id.posted_date);
        dateText.setText("Posted " + date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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