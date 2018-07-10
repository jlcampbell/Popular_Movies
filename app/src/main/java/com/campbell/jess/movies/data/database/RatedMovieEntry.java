package com.campbell.jess.movies.data.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ratedMovies")

public class RatedMovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public RatedMovieEntry(int id) {
        this.id = id;

    }

    public int getId() { return id; }

}

