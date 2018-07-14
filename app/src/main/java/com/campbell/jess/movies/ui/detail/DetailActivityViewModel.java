package com.campbell.jess.movies.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.campbell.jess.movies.data.MoviesRepository;
import com.campbell.jess.movies.data.database.FavoriteMovieEntry;
import com.campbell.jess.movies.data.database.MovieEntry;

import java.util.List;

/**
 * Created by jlcampbell on 7/4/2018.
 */

public class DetailActivityViewModel extends ViewModel {

    // Movie user is looking at
    private final LiveData<MovieEntry> mMovie;
    //private final LiveData<String[]> mReviews;
 //   private final LiveData<String[]> mTrailers;
    private final int mMovieId;
    private final MoviesRepository mRepository;

    //private boolean mIsFavorite;
    private MutableLiveData<Boolean> mIsFavorite;
    private final LiveData<List<MovieEntry>> mFavorites;
    private LiveData<FavoriteMovieEntry> mFavorite;

    public DetailActivityViewModel(MoviesRepository repository, int movieId){
        mRepository = repository;
        mMovieId = movieId;
        mMovie = mRepository.getMovieById(mMovieId);
        mFavorites = mRepository.getFavoriteMovies();
        mFavorite = mRepository.getFavoriteById(mMovieId);
        //mTrailers = mRepository.getTrailersById(mMovieId);

    }

    public LiveData<MovieEntry> getMovie() { return mMovie; }
    public LiveData<List<MovieEntry>> getFavorites() { return mFavorites;}


    public LiveData<FavoriteMovieEntry> getFavorite() { return mFavorite; }
    //   public LiveData<String[]> getTrailers() { return mTrailers; }

    public void addFavorite() {
        mRepository.addFavorite(mMovieId);
    }
    public void deleteFavorite() {
        mRepository.deleteFavorite(mMovieId);
    }
}
