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

        MovieService movieService = APIClient.getClient().create(MovieService.class);

        Call<Movie> call = movieService.getMovie(API_KEY, txtSearch);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie mov = response.body();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
}