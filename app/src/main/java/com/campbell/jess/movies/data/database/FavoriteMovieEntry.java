package com.campbell.jess.movies.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favoriteMovies")

public class FavoriteMovieEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "plot")
    private String plot;

    @ColumnInfo(name = "rating")
    private String rating;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;

    @ColumnInfo(name = "trailerIds")
    private String trailerIds;

    @ColumnInfo(name = "trailerTitles")
    private String trailerTitles;

    public FavoriteMovieEntry(int id, String title, String poster, String plot, String rating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public int getId() { return id; }
    //public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    //public void setTitle(String title) { this.title = title; }

    public String getPoster() { return poster; }
    /**public void setPoster(String poster) {
     this.poster = poster;
     }
     **/
    public String getPlot() {return plot; }
    // public void setPlot(String plot) {this.plot = plot; }

    public String getRating() {return rating; }
    //public void setRating(String rating) {this.rating = rating; }

    public String getReleaseDate() { return releaseDate; }
    //public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getTrailerIds() { return trailerIds; }
    public void setTrailerIds(String trailerIds) { this.trailerIds = trailerIds; }

    public String getTrailerTitles() { return trailerTitles; }
    public void setTrailerTitles(String trailerTitles) { this.trailerTitles = trailerTitles; }
}
