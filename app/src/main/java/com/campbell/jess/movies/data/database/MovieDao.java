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
    //////////////////////MOVIES
    //select all movies
    @Query("SELECT * FROM movies ORDER BY id")
    LiveData<List<MovieEntry>> loadAllMovies();

    //select a movie by id
    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<MovieEntry> getMovieById(int id);

    //insert one movie
    @Insert
    void insertMovie(MovieEntry movieEntry);

    /** method to insert many movies at once into movie table
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(MovieEntry... movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntry movieEntry);

    @Delete
    void deletePopMovie(MovieEntry movieEntry);



    ///////////////////////POPULAR MOVIES


    @Query("SELECT * FROM movies INNER JOIN popularMovies ON movies.id=popularMovies.id")
    LiveData<List<MovieEntry>> loadAllPopMovies();

    //select a movie by id
    @Query("SELECT * FROM popularMovies WHERE id = :id")
    LiveData<PopularMovieEntry> getPopMovieById(int id);

    //insert one movie
    @Insert
    void insertPopMovie(PopularMovieEntry movieEntry);

    /** method to insert many movies at once into pop movie table
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkPopInsert(PopularMovieEntry... movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePopMovie(PopularMovieEntry movieEntry);

    @Delete
    void deletePopMovie(PopularMovieEntry movieEntry);

    ///////////////////////HIGH RATED MOVIES

    @Query("SELECT * FROM movies INNER JOIN ratedMovies ON movies.id=ratedMovies.id")
    LiveData<List<MovieEntry>> loadAllRatedMovies();

    //select a movie by id
    @Query("SELECT * FROM ratedMovies WHERE id = :id")
    LiveData<RatedMovieEntry> getRatedMovieById(int id);

    //insert one movie
    @Insert
    void insertRatedMovie(RatedMovieEntry movieEntry);

    /** method to insert many movies at once into pop movie table
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkRatedInsert(RatedMovieEntry... movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRatedMovie(RatedMovieEntry movieEntry);

    @Delete
    void deleteRatedMovie(RatedMovieEntry movieEntry);


    ///////////////////////FAVORITE MOVIES
    @Query("SELECT * FROM movies INNER JOIN favoriteMovies ON movies.id=favoriteMovies.id")
    LiveData<List<MovieEntry>> loadAllFavoriteMovies();

    //select a movie by id
    @Query("SELECT * FROM favoriteMovies WHERE id = :id")
    LiveData<FavoriteMovieEntry> getFavoriteMovieById(int id);

    //insert one movie
    @Insert
    void insertFavoriteMovie(FavoriteMovieEntry movieEntry);

    /** method to insert many movies at once into favorite movie table
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkFavoriteInsert(FavoriteMovieEntry... movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteMovie(FavoriteMovieEntry movieEntry);

    @Delete
    void deleteFavoriteMovie(FavoriteMovieEntry movieEntry);



}