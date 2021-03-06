package com.example.moviefinder.JSONToJava;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviePage implements Serializable
{

    @SerializedName("Search")
    @Expose
    private List<SummaryMovie> summaryMovies = null;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("Response")
    @Expose
    private String response;
    private final static long serialVersionUID = 5959300877853489327L;

    public List<SummaryMovie> getSummaryMovies() {
        return summaryMovies;
    }

    public void setSummaryMovies(List<SummaryMovie> summaryMovies) {
        this.summaryMovies = summaryMovies;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
