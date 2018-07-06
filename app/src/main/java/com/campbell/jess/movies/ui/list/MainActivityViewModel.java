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
        //int size = mMovies.
        Log.d(log_tag, "creating view model from repository");
    }

    public LiveData<List<MovieEntry>> getMovies() {
        Log.d(log_tag, "get movies");
        return mMovies; }
}
