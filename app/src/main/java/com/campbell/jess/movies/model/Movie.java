package com.campbell.jess.movies.model;

public class Movie {

    private String title;
    private String poster;
    private String plot;
    private String rating;
    private String releaseDate;

    public Movie(String title, String poster, String plot, String rating, String releaseDate){
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {return title;}
    public String getPoster() {return poster;}
    public String getPlot() {return plot;}
    public String getRating() {return rating;}
    public String getReleaseDate() {return releaseDate;}

}
