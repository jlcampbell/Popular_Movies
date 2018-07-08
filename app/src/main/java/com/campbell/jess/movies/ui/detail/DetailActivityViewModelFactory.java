package com.campbell.jess.movies.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.campbell.jess.movies.data.MoviesRepository;

/** heavily referenced google code labs at https://github.com/googlecodelabs/android-build-an-app-architecture-components/blob/arch-training-steps/app/src/main/java/com/example/android/sunshine/ui/detail/DetailViewModelFactory.java
**/


/**
 * Factory method that allows us to create a ViewModel with a constructor
 */
public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MoviesRepository mMoviesRepository;
    private final int mMovieId;

    //constructor
    public DetailActivityViewModelFactory(MoviesRepository repository, int id){
        this.mMoviesRepository = repository;
        this.mMovieId = id;
    }

    //give us a viewModel
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailActivityViewModel(mMoviesRepository, mMovieId);
    }
}
