package com.example.moviefinder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/?")
    Call<MovieComplete> getMovie(@Query("apikey") String apiKey,
                                 @Query("t") String search,
                                 @Query("plot") String plt
    );

    @GET("/?")
    Call<MoviePage> loadPageMovie(@Query("apikey") String apiKey,
                                 @Query("s") String search,
                                 @Query("page") int page

    );
}
