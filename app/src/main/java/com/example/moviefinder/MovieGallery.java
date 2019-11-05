package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        Call<Movie> call = service.getMovie(API_KEY, search, "full");
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie mov = response.body();
                //String jo = response.body().toString();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getMovieList(MovieService service, String search){
        Call<List<MovieList>> call = service.getMovieList(API_KEY, search);
        call.enqueue(new Callback<List<MovieList>>() {
            @Override
            public void onResponse(Call<List<MovieList>> call, Response<List<MovieList>> response) {
                List<MovieList> movL = response.body();
                //String jo = response.body().toString();
            }

            @Override
            public void onFailure(Call<List<MovieList>> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
