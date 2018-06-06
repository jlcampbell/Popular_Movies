package com.campbell.jess.movies.utilities;

import android.content.Context;

import com.campbell.jess.movies.R;
import com.campbell.jess.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Objects;

/**
 * Utility functions to handle The Movie Database JSON data.
 */

public class MovieJsonUtils {

    private static String MOVIE_TITLE;
    private static String MOVIE_POSTER_URL;
    private static String MOVIE_BACKDROP_URL;
    private static String MOVIE_PLOT;
    private static String MOVIE_RATING;
    private static String MOVIE_RELEASE_DATE;
    private static String MOVIE_REVIEW;


    //method to get a ready to use JSONArray with entries for each movie from a json string
    public static JSONArray getJsonArray(String movieJsonString, Context context) throws JSONException {

        MOVIE_TITLE = context.getString(R.string.movie_title_key) ;

        MOVIE_POSTER_URL = context.getString(R.string.movie_poster_url_key);

        MOVIE_BACKDROP_URL = context.getString(R.string.movie_backdrop_url_key);

        MOVIE_PLOT = context.getString(R.string.movie_plot_key);

        MOVIE_RATING = context.getString(R.string.movie_rating_key);

        MOVIE_RELEASE_DATE = context.getString(R.string.movie_release_date_key);



        final String MOVIE_MESSAGE_CODE = "cod";

        JSONObject movieJson = new JSONObject(movieJsonString);

        /* if there is an error */
        if (movieJson.has(MOVIE_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(MOVIE_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection
                        .HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* server down */
                    return null;
            }
        }

        return movieJson.getJSONArray("results");
    }

    // method to get a string array of movie poster urls from the json string
    public static String[] getMoviePostersFromJson(String movieJsonString, Context context)
        throws JSONException {

        String[] parsedMoviePosters;

        //getJsonArray returns array of movie json objects from "results" key
        JSONArray resultsArray = getJsonArray(movieJsonString, context);
         parsedMoviePosters = new String[Objects.requireNonNull(resultsArray).length()];

        //iterate through each movie
        for (int i=0; i < resultsArray.length(); i++) {
            //get the Json object representing the movie//
            JSONObject movieData = resultsArray.getJSONObject(i);
            String poster = "http://image.tmdb.org/t/p/" + "w185" + movieData.getString(MOVIE_POSTER_URL);

            parsedMoviePosters[i] = poster;

        }
        return parsedMoviePosters;

    }

    //returns a Movie object for a given position in the movie string
    public static Movie getMovie(String movieJsonString, int position, Context context)
            throws JSONException{

        String title;
        String poster;
        String backdrop;
        String plot;
        String rating;
        String releaseDate;

        //getJsonArray returns array of movie json objects from "results" key
        JSONArray resultsArray = getJsonArray(movieJsonString, context);
        //movieData should be a JSONObject for a single movie
        JSONObject movieData = resultsArray.getJSONObject(position);

        title = movieData.getString(MOVIE_TITLE);
        backdrop = "http://image.tmdb.org/t/p/" + "w500"+ movieData.getString(MOVIE_BACKDROP_URL);
        plot = movieData.getString(MOVIE_PLOT);
        rating = movieData.getString(MOVIE_RATING);
        releaseDate = movieData.getString(MOVIE_RELEASE_DATE);

        Movie movie = new Movie(title, backdrop, plot, rating, releaseDate);
        return movie;
    }
}
