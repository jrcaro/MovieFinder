package com.example.moviefinder.APIService;

import com.example.moviefinder.JSONToJava.MovieComplete;
import com.example.moviefinder.JSONToJava.MoviePage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/?")
    Call<MovieComplete> getMovie(@Query("apikey") String apiKey,
                                 @Query("i") String search,
                                 @Query("plot") String plt
    );

    @GET("/?")
    Call<MoviePage> loadPageMovie(@Query("apikey") String apiKey,
                                  @Query("s") String search,
                                  @Query("page") int page

    );
}
