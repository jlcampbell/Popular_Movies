package com.campbell.jess.movies.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.campbell.jess.movies.data.MoviesRepository;
import com.campbell.jess.movies.data.database.MovieEntry;

/**
 * Created by jlcampbell on 7/4/2018.
 */

public class DetailActivityViewModel extends ViewModel {

    // Movie user is looking at
    private final LiveData<MovieEntry> mMovie;

    private final int mMovieId;
    private final MoviesRepository mRepository;

    public DetailActivityViewModel(MoviesRepository repository, int movieId){
        mRepository = repository;
        mMovieId = movieId;
        mMovie = mRepository.getMovieById(mMovieId);
    }

    public LiveData<MovieEntry> getMovie() { return mMovie; }
}
