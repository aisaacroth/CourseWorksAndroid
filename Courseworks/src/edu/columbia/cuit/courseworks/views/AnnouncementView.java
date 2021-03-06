package edu.columbia.cuit.courseworks.views;

import java.text.DateFormat;
import java.util.Date;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.structures.Announcement;

import android.app.*;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * A singleton view of any given announcement. Called from the
 * AnnouncementListView fragment.
 * 
 * @author Alexander Roth
 * @date 2014-07-20 
 */
public class AnnouncementView extends Activity {
    private TextView actionBarBackButton;
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
        setBackButtonCommand();
    }

    private void setTextViews() {
        actionBarBackButton = (TextView) findViewById(R.id.announcement_actionbar_back);
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
        actionBarBackButton.setTypeface(thinFont);
        actionBarTitleText.setTypeface(thinFont);
    }

    private void setBackButtonCommand() {
        actionBarBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
    }

}
