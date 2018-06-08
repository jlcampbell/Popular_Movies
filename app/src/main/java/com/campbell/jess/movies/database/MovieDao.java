package com.campbell.jess.movies.database;

import java.util.List;

/**
 * Created by jlcampbell on 6/8/2018.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY title")
    List<MovieEntry> loadAllMovies();


}
