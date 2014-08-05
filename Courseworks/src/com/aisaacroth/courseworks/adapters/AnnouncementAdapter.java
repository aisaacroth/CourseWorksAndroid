package com.aisaacroth.courseworks.adapters;

import java.util.List;

import com.aisaacroth.courseworks.R;
import com.aisaacroth.courseworks.structures.Announcement;

import android.content.Context;
import android.graphics.Typeface;
import android.view.*;
import android.widget.*;

/**
 * Extends the ArrayAdapter class in order to run an ListView for the 
 * announcement center within the "Courseworks" application.
 * 
 * @author Alexander Roth
 * @date 2014-06-12
 */
public class AnnouncementAdapter extends BaseAdapter {
    
    private Context context;
    private List<Announcement> announcementList;

    public AnnouncementAdapter(Context context, List<Announcement> announcementList) {
        this.context = context;
        this.announcementList = announcementList;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Announcement announcement = announcementList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.announcement_list_item, null);
        }
        
        TextView titleText = (TextView) convertView.findViewById(R.id.item_title);
        titleText.setText(announcement.getTitle());
        TextView classText = (TextView) convertView.findViewById(R.id.item_class);
        classText.setText(announcement.getClassId());
        TextView dateText = (TextView) convertView.findViewById(R.id.item_date);
        dateText.setText("/ Posted " + announcement.getPostedDate());
        
        Typeface regularFont = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        titleText.setTypeface(regularFont);
        
        Typeface mediumFont = Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf");
        classText.setTypeface(mediumFont);
        dateText.setTypeface(mediumFont);
        
        return convertView;
    }


    
    @Override
    public int getCount() {
        return announcementList.size();
    }

    @Override
    public Object getItem(int position) {
        return announcementList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}