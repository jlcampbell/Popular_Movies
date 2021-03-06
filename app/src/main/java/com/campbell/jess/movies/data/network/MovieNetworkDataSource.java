package com.campbell.jess.movies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.campbell.jess.movies.AppExecutors;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.campbell.jess.movies.data.database.PopularMovieEntry;
import com.campbell.jess.movies.data.database.RatedMovieEntry;
import com.campbell.jess.movies.model.Movie;

import java.net.URL;
import java.util.List;

/**
 * Created by jlcampbell on 7/4/2018. Heavily referenced sunshine "build an app with architecture components" tutorial at https://codelabs.developers.google.com/codelabs/build-app-with-arch-components/index.html
 */

public class MovieNetworkDataSource {
    private static final String LOG_TAG = MovieNetworkDataSource.class.getSimpleName();

    /**
     * add update and firebase stuff here
     **/

    // For singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieNetworkDataSource sInstance;
    private final Context mContext;

    // LiveData storing the latest downloaded movies
    private final MutableLiveData<MovieEntry[]> mDownloadedMovies;
    private final MutableLiveData<MovieEntry[]> mRatedMovies;
    private final MutableLiveData<RatedMovieEntry[]> mRatedMovieList;
    private final MutableLiveData<MovieEntry[]> mPopularMovies;
    private final MutableLiveData<PopularMovieEntry[]> mPopularMovieList;
    private final MutableLiveData<String[]> mReviews;
    private final MutableLiveData<String[]> mTrailers;

    private String[] tempTrailers;

    private final AppExecutors mExecutors;
    //private String[] tempTrailers;

    private MovieNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;

        //todo this will be all movies
        mDownloadedMovies = new MutableLiveData<MovieEntry[]>();

        mRatedMovies = new MutableLiveData<MovieEntry[]>();
        mRatedMovieList = new MutableLiveData<RatedMovieEntry[]>();
        mPopularMovies = new MutableLiveData<MovieEntry[]>();
        mPopularMovieList = new MutableLiveData<PopularMovieEntry[]>();
        mReviews = new MutableLiveData<String[]>();
        mTrailers = new MutableLiveData<String[]>();
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

    public LiveData<MovieEntry[]> getPopularMovies() { return mPopularMovies; }

    public LiveData<PopularMovieEntry[]> getPopularMovieList() { return mPopularMovieList; }

    //returns high rated movies as a livedata list of MovieEntries
    public LiveData<MovieEntry[]> getHighRatedMovies() {
        return mRatedMovies;
    }

    public LiveData<RatedMovieEntry[]> getRatedMovieList() { return mRatedMovieList; }

    public LiveData<String[]> getTrailers() { return mTrailers; }
    //TODO create fetch movie service
    //public void startFetchMovieService()

    /**
     * gets the newest movies
     */
    public void fetchMovies() {
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
    public void fetchPopularMovies() {
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL movieRequestUrl = NetworkUtils.buildPopularUrl();

                    String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                    MovieEntry[] movieEntries = MovieJsonUtils.getMovieEntries(jsonMovieResponse, mContext);
                    PopularMovieEntry[] popularMovieEntries = MovieJsonUtils.getPopularMovieIds(jsonMovieResponse, mContext);

                    if (movieEntries != null) {
                        //mDownloadedMovies.postValue(movieEntries);
                        mPopularMovies.postValue(movieEntries);
                    }
                    if (popularMovieEntries != null) {
                        mPopularMovieList.postValue(popularMovieEntries);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    public void fetchRatedMovies() {
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL movieRequestUrl = NetworkUtils.buildRatedUrl();

                    String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                    MovieEntry[] movieEntries = MovieJsonUtils.getMovieEntries(jsonMovieResponse, mContext);
                    RatedMovieEntry[] ratedMovieEntries = MovieJsonUtils.getRatedMovieIds(jsonMovieResponse, mContext);

                    if (movieEntries != null) {
                        //mDownloadedMovies.postValue(movieEntries);
                        mRatedMovies.postValue(movieEntries);
                    }
                    if (ratedMovieEntries != null) {
                        mRatedMovieList.postValue(ratedMovieEntries);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    };
    public void fetchMovieReviews(int id) {
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL reviewRequestUrl = NetworkUtils.buildReviewUrl(id);

                    String jsonMovieReviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewRequestUrl);
                    String[] reviews = MovieJsonUtils.getReviewsFromJson(jsonMovieReviewResponse, mContext);

                    if (reviews != null) {
                        mReviews.postValue(reviews);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    };

    public void fetchMovieTrailers(int id) {
        Log.d(LOG_TAG, "attempting to fetch movie trailers in data source");
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL trailerRequestUrl = NetworkUtils.buildVideoUrl(id);
                    Log.d(LOG_TAG, "building video url");
                    Log.d(LOG_TAG, trailerRequestUrl.toString());

                    String jsonTrailerMovieResponse = NetworkUtils.getResponseFromHttpUrl(trailerRequestUrl);
                    Log.d(LOG_TAG, jsonTrailerMovieResponse);
                    String[] trailers = MovieJsonUtils.getTrailersFromJson(jsonTrailerMovieResponse, mContext);
                    Log.d(LOG_TAG, trailers[0]);
                    if (trailers != null){
                        mTrailers.postValue(trailers);
                       // Log.d()
                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    public String[] fetchMovieTrailersReturnable(int id) {

        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL trailerRequestUrl = NetworkUtils.buildVideoUrl(id);

                    String jsonTrailerMovieResponse = NetworkUtils.getResponseFromHttpUrl(trailerRequestUrl);
                    String[] trailers = MovieJsonUtils.getTrailersFromJson(jsonTrailerMovieResponse, mContext);

                    if (trailers != null){

                        tempTrailers = trailers;
                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
        return tempTrailers;
    }

/**
    public void fetchBulkPopularTrailers() {
        //todo change to all movie list
        PopularMovieEntry[] popularMovies = mPopularMovieList.getValue();
        String[][] allTrailers = new List
        for (PopularMovieEntry movie: popularMovies
             ) {
            int id = movie.getId();
            fetchMovieTrailers(id);
            String[] popularTrailers = mTrailers;
        }
    }
**/
}




