package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieGallery extends AppCompatActivity {
    private  final static String API_KEY = "b55fd2c4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_gallery);
        Intent intent = getIntent(); //recibe el intent de la main activity
        String txtSearch = intent.getStringExtra(intent.EXTRA_TEXT);
        //txtSearch = txtSearch.substring(0,1).toUpperCase() + txtSearch.substring(1); //pone en mayuscula la primera letra

        MovieService movieService = APIClient.getClient().create(MovieService.class); //crea el servicio

        getMovieList(movieService, txtSearch); //llama a la implementacion

    }

    private void getMovie(MovieService service, String search){
        Call<MovieComplete> call = service.getMovie(API_KEY, search, "full");
        call.enqueue(new Callback<MovieComplete>() {
            @Override
            public void onResponse(Call<MovieComplete> call, Response<MovieComplete> response) {
            }

            @Override
            public void onFailure(Call<MovieComplete> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getMovieList(MovieService service, String search){
        Call<MoviePage> call = service.getMovieList(API_KEY, search);
        call.enqueue(new Callback<MoviePage>() {
            @Override
            public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {

            }

            @Override
            public void onFailure(Call<MoviePage> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
