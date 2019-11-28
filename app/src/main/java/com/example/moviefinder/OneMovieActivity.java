package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviefinder.APIService.APIClient;
import com.example.moviefinder.APIService.MovieService;
import com.example.moviefinder.JSONToJava.MovieComplete;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneMovieActivity extends AppCompatActivity {
    private final static String API_KEY = "b55fd2c4";

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
                TextView title = (TextView) findViewById(R.id.title);
                TextView director = (TextView) findViewById(R.id.director);
                TextView actors = (TextView) findViewById(R.id.actors);
                TextView plot = (TextView) findViewById(R.id.plot);
                TextView genre = (TextView) findViewById(R.id.genre);
                ImageView poster = (ImageView) findViewById(R.id.poster);

                title.setText(response.body().getTitle());
                director.setText("Director: " + response.body().getDirector());
                actors.setText("Cast: " + response.body().getActors());
                plot.setText(response.body().getPlot());
                plot.setMovementMethod(new ScrollingMovementMethod());
                genre.setText("Genre: " + response.body().getGenre());
                Picasso.with(getApplicationContext())
                        .load(response.body().getPoster())
                        .resize(750,1112)
                        .into(poster);
            }

            @Override
            public void onFailure(Call<MovieComplete> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
