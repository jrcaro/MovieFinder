package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.moviefinder.APIService.APIClient;
import com.example.moviefinder.APIService.MovieService;
import com.example.moviefinder.ExpandableList.CustomExpandableListAdapter;
import com.example.moviefinder.JSONToJava.MovieComplete;
import com.example.moviefinder.JSONToJava.Rating;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneMovieActivity extends AppCompatActivity {
    private final static String API_KEY = "b55fd2c4";
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<>();
    TextView title;
    TextView director;
    TextView actors;
    TextView plot;
    TextView genre;
    ImageView poster;
    TextView year;
    TextView rated;
    TextView released;
    TextView writer;
    TextView awards;
    TextView imdbRating;
    TextView boxOffice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent(); //recibe el intent de la main activity
        String title = intent.getStringExtra(intent.EXTRA_TEXT);

        //crea el servicio
        MovieService movieService = APIClient.getClient().create(MovieService.class);
        getMovie(movieService, title);
    }

    private void getMovie(MovieService service, String search){
        Call<MovieComplete> call = service.getMovie(API_KEY, search, "full");
        call.enqueue(new Callback<MovieComplete>() {
            @Override
            public void onResponse(Call<MovieComplete> call, Response<MovieComplete> response) {
                loadView(response);
                }

            @Override
            public void onFailure(Call<MovieComplete> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void loadView(Response<MovieComplete> resp){
        title = (TextView) findViewById(R.id.title);
        director = (TextView) findViewById(R.id.director);
        actors = (TextView) findViewById(R.id.actors);
        plot = (TextView) findViewById(R.id.plot);
        genre = (TextView) findViewById(R.id.genre);
        poster = (ImageView) findViewById(R.id.poster);
        year = (TextView) findViewById(R.id.year);
        rated = (TextView) findViewById(R.id.rated);
        released = (TextView) findViewById(R.id.released);
        writer = (TextView) findViewById(R.id.writer);
        awards = (TextView) findViewById(R.id.awards);
        imdbRating = (TextView) findViewById(R.id.imdbRating);
        boxOffice = (TextView) findViewById(R.id.boxOffice);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        title.setText(resp.body().getTitle());
        Picasso.with(getApplicationContext())
                .load(resp.body().getPoster())
                .resize(750,1112)
                .into(poster);
        director.append(resp.body().getDirector());
        actors.append(resp.body().getActors());
        plot.append(resp.body().getPlot());
        genre.append(resp.body().getGenre());
        year.append(resp.body().getYear());
        rated.append(resp.body().getRated());
        released.append(resp.body().getReleased());
        writer.append(resp.body().getWriter());
        awards.append(resp.body().getAwards());
        imdbRating.append(resp.body().getImdbRating());
        boxOffice.append(resp.body().getBoxOffice());

        expandableListDetail = getRatings(resp);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

    private HashMap<String, List<String>> getRatings(Response<MovieComplete> resp){
        List<Rating> ratings = resp.body().getRatings();
        List<String> ratingsStr = new ArrayList<String>();
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        for (Rating rat : ratings){
            ratingsStr.add(rat.getSource() + ": " + rat.getValue());
        }

        expandableListDetail.put("Ratings", ratingsStr);
        return expandableListDetail;
    }
}
