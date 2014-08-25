package edu.columbia.cuit.courseworks.adapters;

import java.util.List;

import edu.columbia.cuit.courseworks.R;
import edu.columbia.cuit.courseworks.structures.Announcement;

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
public class AnnouncementAdapter extends ItemAdapter<Announcement> {

    private TextView titleText;
    private TextView classText;
    private TextView dateText;

    public AnnouncementAdapter(Context context, List<Announcement> itemList) {
        super(context, itemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Announcement announcement = itemList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.announcement_list_item,
                    null);
        }

        setListItemFormat(convertView, announcement);
        return convertView;
    }

    @Override
    protected void setListItemFormat(View view, Announcement announcement) {
        setListItemViews(view);
        setTextInViews(announcement);
        setFontsForText();
    }

    @Override
    protected void setListItemViews(View view) {
        titleText = (TextView) view.findViewById(R.id.announcement_item_title);
        classText = (TextView) view.findViewById(R.id.announcement_item_class);
        dateText = (TextView) view.findViewById(R.id.announcement_item_date);
    }

    @Override
    protected void setTextInViews(Announcement announcement) {
        titleText.setText(announcement.getTitle());
        classText.setText(announcement.getClassId());
        dateText.setText("/ Posted " + announcement.getPostedDate());
    }

    @Override
    protected void setFontsForText() {
        setRegularFont();
        setMediumFont();
    }

    private void setRegularFont() {
        Typeface regularFont = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Regular.ttf");
        titleText.setTypeface(regularFont);
    }

    private void setMediumFont() {
        Typeface mediumFont = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Medium.ttf");
        classText.setTypeface(mediumFont);
        dateText.setTypeface(mediumFont);
    }

}