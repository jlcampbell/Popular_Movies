package com.campbell.jess.movies.model;

public class Movie {

    private final int id;
    private final String title;
    private String poster;
    private String plot;
    private String rating;
    private String releaseDate;
    private String[] reviews;
    private String[] trailerIds;
    private String[] trailerTitles;


    public Movie(int id, String title, String poster, String plot, String rating, String releaseDate){
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.trailerIds = trailerIds;
        this.trailerTitles = trailerTitles;

    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getPoster() {return poster;}
    public String getPlot() {return plot;}
    public String getRating() {return rating;}
    public String getReleaseDate() {return releaseDate;}

    public void setReviews(String[] reviews){
        this.reviews = reviews;
    }
    public String[] getReviews() {return reviews;}

    public void setTrailerIds(String[] trailers) { this.trailerIds = trailerIds; }
    public String[] getTrailerIds() { return trailerIds; }

    public void setTrailerTitles(String[] trailerTitles) { this.trailerTitles = trailerTitles; }
    public String[] getTrailerTitles() { return trailerTitles; }
}
