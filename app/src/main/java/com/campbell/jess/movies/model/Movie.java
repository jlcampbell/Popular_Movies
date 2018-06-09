package com.campbell.jess.movies.model;

public class Movie {

    private final int id;
    private final String title;
    private String poster;
    private String plot;
    private String rating;
    private String releaseDate;

    public Movie(int id, String title, String poster, String plot, String rating, String releaseDate){
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getPoster() {return poster;}
    public String getPlot() {return plot;}
    public String getRating() {return rating;}
    public String getReleaseDate() {return releaseDate;}

}
