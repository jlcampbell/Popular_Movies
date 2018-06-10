package com.campbell.jess.movies.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jlcampbell on 6/6/2018.
 */
@Entity(tableName = "favorites")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "poster")
    private String poster;

    public MovieEntry(int id, String title, String poster) {
        this.id = id;
        this.title = title;
        this.poster = poster;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getPoster() { return poster; }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}

