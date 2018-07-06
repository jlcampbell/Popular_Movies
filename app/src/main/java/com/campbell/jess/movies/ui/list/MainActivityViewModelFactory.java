package com.campbell.jess.movies.ui.list;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.campbell.jess.movies.data.MoviesRepository;
import com.campbell.jess.movies.data.database.MovieEntry;

import java.util.List;

/**
 * Created by jlcampbell on 7/5/2018.
 */

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final static String TAG = MainActivityViewModel.class.getSimpleName();

    private final MoviesRepository mMoviesRepository;



    public MainActivityViewModelFactory(MoviesRepository moviesRepository) { this.mMoviesRepository = moviesRepository; }




    @Override

    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(mMoviesRepository);
    }


}
