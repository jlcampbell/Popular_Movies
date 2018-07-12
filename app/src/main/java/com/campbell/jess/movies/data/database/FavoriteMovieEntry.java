package com.campbell.jess.movies.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "favoriteMovies")
public class FavoriteMovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public FavoriteMovieEntry(int id) {
        this.id = id;

    }

    public int getId() { return id; }
}
