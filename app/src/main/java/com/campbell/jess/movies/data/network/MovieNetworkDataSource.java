package com.campbell.jess.movies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.campbell.jess.movies.AppExecutors;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.campbell.jess.movies.model.Movie;

import java.net.URL;

/**
 * Created by jlcampbell on 7/4/2018. Heavily referenced sunshine "build an app with architecture components" tutorial at https://codelabs.developers.google.com/codelabs/build-app-with-arch-components/index.html
 */

public class MovieNetworkDataSource {
    private static final String LOG_TAG = MovieNetworkDataSource.class.getSimpleName();

    /** add update and firebase stuff here **/

    // For singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieNetworkDataSource sInstance;
    private final Context mContext;

    // LiveData storing the latest downloaded movies
    private final MutableLiveData<MovieEntry[]> mDownloadedMovies;
    private final AppExecutors mExecutors;

    private MovieNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedMovies = new MutableLiveData<MovieEntry[]>();
    }

    /**
     * Get the singleton for this class
     */
    public static MovieNetworkDataSource getsInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "getting instance");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "made new network data source");
            }
        }
        if (sInstance != null) {
            Log.d(LOG_TAG, "sInstance not null");
        }
        return sInstance;
    }

    //returns popular movies as a livedata list of MovieEntries
    public LiveData<MovieEntry[]> getMovies() {
        return mDownloadedMovies;
    }

    //returns high rated movies as a livedata list of MovieEntries
    public LiveData<MovieEntry[]> getHighRatedMovies() {
        return mDownloadedMovies;
    }

    //TODO create fetch movie service
    //public void startFetchMovieService()

    /**
     * gets the newest movies
     */
    public void fetchMovies(){
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL movieRequestUrl = NetworkUtils.buildUrl(mContext);

                    String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                    MovieEntry[] movieEntries = MovieJsonUtils.getMovieEntries(jsonMovieResponse, mContext);

                    if (movieEntries != null) {
                        mDownloadedMovies.postValue(movieEntries);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });


    }
    public void fetchPopularMovies(){
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL movieRequestUrl = NetworkUtils.buildPopularUrl();

                    String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                    MovieEntry[] movieEntries = MovieJsonUtils.getMovieEntries(jsonMovieResponse, mContext);

                    if (movieEntries != null) {
                        mDownloadedMovies.postValue(movieEntries);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });


    }



}
