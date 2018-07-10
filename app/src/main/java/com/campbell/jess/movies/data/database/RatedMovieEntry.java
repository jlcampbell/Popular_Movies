package com.campbell.jess.movies.data.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ratedMovies")

public class RatedMovieEntry extends MovieEntry {


    public RatedMovieEntry(int id, String title, String poster, String plot, String rating, String releaseDate) {
        super(id, title, poster, plot, rating, releaseDate);

    }

}

