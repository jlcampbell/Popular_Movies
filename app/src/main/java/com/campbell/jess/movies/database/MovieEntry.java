package com.campbell.jess.movies.database;

/**
 * Created by jlcampbell on 6/6/2018.
 */

public class MovieEntry {

    private int id;
    private String title;

    public MovieEntry(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}

