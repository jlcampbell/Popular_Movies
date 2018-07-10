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
    //private final LiveData<List<MovieEntry>> mFavoriteMovies;

    /**
    public MainActivityViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        mMovies = database.movieDao().loadAllMovies();
    }
**/

    public MainActivityViewModel(MoviesRepository repository) {
        mMoviesRepository = repository;
        mMovies = mMoviesRepository.getMovies();
        mPopularMovies = mMoviesRepository.getPopularMovies();
        mRatedMovies = mMoviesRepository.getRatedMovies();
        Log.d(log_tag, "creating view model from repository");
    }

    public LiveData<List<MovieEntry>> getMovies() {
        Log.d(log_tag, "get movies");
        return mMovies; }

    public LiveData<List<MovieEntry>> getPopularMovies() {
        Log.d(log_tag, "get popular movies");
        return mPopularMovies; }

    public LiveData<List<MovieEntry>> getRatedMovies() {
        Log.d(log_tag, "get rated movies");
        return mRatedMovies; }
/**
    public LiveData<List<MovieEntry>> getFavoriteMovies() {
        Log.d(log_tag, "get favorite movies");
        return mFavoriteMovies; }
**/
 }
