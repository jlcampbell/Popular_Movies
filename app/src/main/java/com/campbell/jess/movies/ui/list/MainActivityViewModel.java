package com.campbell.jess.movies.ui.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

import com.campbell.jess.movies.data.MoviesRepository;
import com.campbell.jess.movies.data.database.AppDatabase;
import com.campbell.jess.movies.data.database.MovieEntry;

import java.util.List;

/**
 * Created by jlcampbell on 7/5/2018.
 */

public class MainActivityViewModel extends ViewModel {
    private static final String log_tag = "main activity vm";

    private final MoviesRepository mMoviesRepository;

    private final LiveData<List<MovieEntry>> mMovies;
    private final LiveData<List<MovieEntry>> mPopularMovies;
    private final LiveData<List<MovieEntry>> mRatedMovies;
    private final LiveData<List<MovieEntry>> mFavoriteMovies;

    public MainActivityViewModel(MoviesRepository repository) {
        mMoviesRepository = repository;
        mMovies = mMoviesRepository.getMovies();
        mPopularMovies = mMoviesRepository.getPopularMovies();
        mRatedMovies = mMoviesRepository.getRatedMovies();
        mFavoriteMovies = mMoviesRepository.getFavoriteMovies();
    }

    public LiveData<List<MovieEntry>> getMovies() {
        return mMovies; }

    public LiveData<List<MovieEntry>> getPopularMovies() {
        return mPopularMovies; }

    public LiveData<List<MovieEntry>> getRatedMovies() {
        return mRatedMovies; }

    public LiveData<List<MovieEntry>> getFavoriteMovies() {
        return mFavoriteMovies; }

 }
