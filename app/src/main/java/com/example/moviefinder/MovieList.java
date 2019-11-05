package com.example.moviefinder;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class MovieList {
    @Element("Title")
    private String title;

    @Element("Year")
    private String year;

    @Element("imdbID")
    private String imdbid;

    @Element("Type")
    private String type;

    @Element("Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
