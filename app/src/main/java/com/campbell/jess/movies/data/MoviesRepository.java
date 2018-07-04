package com.campbell.jess.movies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;

import com.campbell.jess.movies.AppExecutors;
import com.campbell.jess.movies.data.database.MovieDao;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.campbell.jess.movies.data.network.MovieNetworkDataSource;

/**
 * Created by jlcampbell on 7/4/2018.
 */

public class MoviesRepository {
    private static final String LOG_TAG = MoviesRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static MoviesRepository sInstance;
    private final com.campbell.jess.movies.data.database.MovieDao mMovieDao;
    private final MovieNetworkDataSource mMovieNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private MoviesRepository( MovieDao movieDao, MovieNetworkDataSource movieNetworkDataSource, AppExecutors appExecutors) {
        mMovieDao = movieDao;
        mMovieNetworkDataSource = movieNetworkDataSource;
        mExecutors = appExecutors;

        LiveData<MovieEntry[]> networkData = mMovieNetworkDataSource.getMovies();
        networkData.observeForever(newMovies ->  {
            mExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.bulkInsert(newMovies);
                }
            });
        });
    }

    public synchronized static MoviesRepository getInstance(MovieDao movieDao, MovieNetworkDataSource movieNetworkDataSource, AppExecutors appExecutors){
        Log.d(LOG_TAG, "getting the repository");
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = new MoviesRepository(movieDao, movieNetworkDataSource, appExecutors);
                Log.d(LOG_TAG, "made new repository");
            }
        }
        return sInstance;
    }

    //TODO initialize data needs to incorporate a daily update of the data, not just update it everytime it is called

    private synchronized void initializeData() {

    }


}

