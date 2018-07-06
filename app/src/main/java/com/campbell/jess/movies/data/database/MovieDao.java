package com.campbell.jess.movies.data.database;



import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


/**
 * Created by jlcampbell on 6/8/2018.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY id")
    LiveData<List<MovieEntry>> loadAllMovies();


    //method for selecting popular movie list only

    //method for selecting top rated movies only

    //method for selecting favorite movies only

    //gets movie details for a single movie
    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<MovieEntry> getMovieById(int id);

    @Insert
    void insertMovie(MovieEntry movieEntry);

    /** method to insert many movies at once into movie table
     * needs to be fixed so that works correctly when the same movie is added from multiple sources
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(MovieEntry... movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntry movieEntry);

    @Delete
    void deleteMovie(MovieEntry movieEntry);
}