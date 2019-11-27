package com.example.moviefinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviefinder.JSONToJava.Search;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CustomList extends ArrayAdapter<Search> {
    public CustomList(Activity context, List<Search> movie) {
        super(context, R.layout.list_single, movie);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the data item for this position
        Search movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_single, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView year = (TextView) view.findViewById(R.id.year);
        ImageView img = (ImageView) view.findViewById(R.id.poster);

        // Populate the data into the template view using the data object
        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        type.setText(movie.getType());
        Picasso.with(getContext())
                .load(movie.getPoster())
                .resize(450,667)
                .into(img);

        // Return the completed view to render on screen
        return view;
    }
}
