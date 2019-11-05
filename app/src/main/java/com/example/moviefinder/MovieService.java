package com.example.moviefinder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/?")
    Call<Movie> getMovie(@Query("apikey") String apiKey,
                         @Query("t") String search,
                         @Query("plot") String plt
    );

    @GET("/?")
    Call<List<MovieList>> getMovieList(@Query("apikey") String apiKey,
                                   @Query("s") String search
    );
}
