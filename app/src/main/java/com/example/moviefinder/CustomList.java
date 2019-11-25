package com.example.moviefinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] title;
    private final String[] poster;
    private final String[] year;
    private final String[] type;

    public CustomList(Activity context, String[] title, String[] poster, String[] year, String[] type) {
        super(context, R.layout.list_single, title);
        this.context = context;
        this.title = title;
        this.poster = poster;
        this.year = year;
        this.type = type;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView itemTitle = (TextView) rowView.findViewById(R.id.title);
        TextView itemYear = (TextView) rowView.findViewById(R.id.year);
        TextView itemType = (TextView) rowView.findViewById(R.id.type);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        itemTitle.setText(title[position]);
        itemYear.setText(year[position]);
        itemType.setText(type[position]);
        //imagen poster
        return rowView;
    }
}
