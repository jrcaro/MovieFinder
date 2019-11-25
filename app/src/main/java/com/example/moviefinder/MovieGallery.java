package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieGallery extends AppCompatActivity {
    private  final static String API_KEY = "b55fd2c4";
    ListView list;
    ArrayList<String> titles = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_gallery);
        Intent intent = getIntent();
        String txtSearch = intent.getStringExtra(intent.EXTRA_TEXT);
        txtSearch = txtSearch.substring(0,1).toUpperCase() + txtSearch.substring(1);

        MovieService movieService = APIClient.getClient().create(MovieService.class);
        getMovieList(movieService, txtSearch);
        getMovie(movieService, txtSearch);


    }

    private void getMovie(MovieService service, String search){
        Call<MovieComplete> call = service.getMovie(API_KEY, search, "full");
        call.enqueue(new Callback<MovieComplete>() {
            @Override
            public void onResponse(Call<MovieComplete> call, Response<MovieComplete> response) {
                MovieComplete mov = response.body();
                //String jo = response.body().toString();
            }

            @Override
            public void onFailure(Call<MovieComplete> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getMovieList(MovieService service, String search){
        Call<List<MoviePage>> call = service.getMovieList(API_KEY, search);
        call.enqueue(new Callback<List<MoviePage>>() {
            @Override
            public void onResponse(Call<List<MoviePage>> call, Response<List<MoviePage>> response) {
                List<MoviePage> movL = response.body();
                //String jo = response.body().toString();
            }

            @Override
            public void onFailure(Call<List<MoviePage>> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
