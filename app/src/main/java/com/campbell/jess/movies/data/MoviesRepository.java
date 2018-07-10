package com.campbell.jess.movies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;

import com.campbell.jess.movies.AppExecutors;
import com.campbell.jess.movies.data.database.MovieDao;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.campbell.jess.movies.data.database.PopularMovieEntry;
import com.campbell.jess.movies.data.database.RatedMovieEntry;
import com.campbell.jess.movies.data.network.MovieNetworkDataSource;

import java.util.List;

import static android.content.ContentValues.TAG;

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

        //todo delete this generic method
        LiveData<MovieEntry[]> networkData = mMovieNetworkDataSource.getMovies();
        networkData.observeForever(newMovies ->  {
            mExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.bulkInsert(newMovies);
                    String test = newMovies[0].getTitle();
                    Log.d(LOG_TAG, test );
                }
            });
        });

        LiveData<MovieEntry[]> popularNetworkData = mMovieNetworkDataSource.getPopularMovies();
        popularNetworkData.observeForever(newMovies -> {
            mExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.bulkInsert(newMovies);
                }
            });
        });
        LiveData<PopularMovieEntry[]> popularNetworkList = movieNetworkDataSource.getPopularMovieList();
        popularNetworkList.observeForever(newMovies -> {
            mExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.bulkPopInsert(newMovies);
                }
            });
            });


        LiveData<MovieEntry[]> ratedNetworkData = mMovieNetworkDataSource.getHighRatedMovies();
        ratedNetworkData.observeForever(newMovies -> {
            mExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.bulkInsert(newMovies);
                }
            });
        });
        LiveData<RatedMovieEntry[]> ratedNetworkList = movieNetworkDataSource.getRatedMovieList();
        ratedNetworkList.observeForever(newMovies -> {
            mExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.bulkRatedInsert(newMovies);
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


    private synchronized void initializeData() {
//TODO initialize data needs to incorporate a daily update of the data, not just update it everytime it is called
        Log.d(TAG, "initializeData: ");
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieNetworkDataSource.fetchPopularMovies();
                mMovieNetworkDataSource.fetchRatedMovies();
            }
        });
        mInitialized = true;
    }
    private synchronized void initializeDetailData(int id) {
        Log.d(TAG, "initialize detail data");
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieNetworkDataSource.fetchMovieReviews(id);
                mMovieNetworkDataSource.fetchMovieTrailers(id);
            }
        });
        //details initialized = true
    }

    /*
    database operations- called by view models to get movies from DAO
     */


    public LiveData<List<MovieEntry>> getMovies() {
        initializeData();
        //todo add a step here to clear old movies
        return mMovieDao.loadAllMovies();
    }

    public LiveData<List<MovieEntry>> getPopularMovies() {
        initializeData();
        return mMovieDao.loadAllPopMovies();
    }

    public LiveData<List<MovieEntry>> getRatedMovies() {
        initializeData();
        return mMovieDao.loadAllRatedMovies();
    }

    public LiveData<MovieEntry> getMovieById(int id) {
        initializeData();
        return mMovieDao.getMovieById(id);
    }

    public LiveData<MovieEntry> getTrailersById(int id) {
        initializeDetailData();
        return m
    }

}

