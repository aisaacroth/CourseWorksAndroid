package com.aisaacroth.courseworks.views;

import java.text.DateFormat;
import java.util.Date;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.structures.Announcement;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

public class AnnouncementView extends Activity {
    private TextView actionBarBackText;
    private TextView actionBarTitleText;
    private TextView currentDate;
    private TextView updateTime;
    private TextView titleText;
    private TextView classText;
    private TextView bodyText;
    private TextView dateText;
    private TextView classBox;
    private TextView postedBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_view);
        Intent announcementData = getIntent();
        Announcement announcement = (Announcement) announcementData
                .getSerializableExtra("Announcement");

        Log.d("ANNOUNCEMENT IN VIEW", "Title: " + announcement.getTitle());

        setActionBar();
        setAnnouncementView(announcement);
    }

    private void setActionBar() {
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.announcement_view_actionbar);
    }

    private void setAnnouncementView(Announcement announcement) {
        setTextViews();
        populateTextViews(announcement);
        setFonts();

    }

    private void setTextViews() {
        actionBarBackText = (TextView) findViewById(R.id.announcement_actionbar_back);
        actionBarTitleText = (TextView) findViewById(R.id.announcement_actionbar_title);
        currentDate = (TextView) findViewById(R.id.announcement_current_date);
        updateTime = (TextView) findViewById(R.id.announcement_update_time);
        titleText = (TextView) findViewById(R.id.announcement_title);
        classBox = (TextView) findViewById(R.id.announcement_class_box);
        classText = (TextView) findViewById(R.id.announcement_class);
        bodyText = (TextView) findViewById(R.id.announcement_body);
        dateText = (TextView) findViewById(R.id.announcement_posted_date);
        postedBox = (TextView) findViewById(R.id.announcement_posted_box);
    }

    private void populateTextViews(Announcement announcement) {
        currentDate.setText(setCurrentDate());
        updateTime.setText(setUpdateTime());
        titleText.setText(announcement.getTitle());
        classText.setText(announcement.getClassId());
        bodyText.setText(Html.fromHtml(announcement.getBodyText()));
        bodyText.setMovementMethod(LinkMovementMethod.getInstance());
        dateText.setText(announcement.getPostedDate());
    }

    private String setCurrentDate() {
        return DateFormat.getDateInstance().format(new Date());
    }

    private String setUpdateTime() {
        return "UPDATED "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(
                        new Date());
    }

    private void setFonts() {
        setBoldFont();
        setNarrowFont();
    }

    private void setBoldFont() {
        Typeface boldFont = Typeface.createFromAsset(getAssets(),
                "Roboto-Bold.ttf");
        classBox.setTypeface(boldFont);
        postedBox.setTypeface(boldFont);
    }

    private void setNarrowFont() {
        Typeface thinFont = Typeface.createFromAsset(getAssets(),
                "Roboto-Light.ttf");
        actionBarBackText.setTypeface(thinFont);
        actionBarTitleText.setTypeface(thinFont);
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