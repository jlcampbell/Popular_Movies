package com.campbell.jess.movies;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.campbell.jess.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility functions to handle The Movie Database JSON data.
 */

public class MovieJsonUtils {
    //TODO clean up duplicate code between the methods
    final static String TAG = "movie json utils";

    final static String MOVIE_TITLE = "title";

    final static String MOVIE_POSTER_URL = "poster_path";

    final static String MOVIE_BACKDROP_URL = "backdrop_path";

    final static String MOVIE_PLOT = "overview";

    final static String MOVIE_RATING = "vote_average";

    final static String MOVIE_RELEASE_DATE = "release_date";

    //method to get a ready to use JSONArray with entries for each movie from a json string
    public static JSONArray getJsonArray(String movieJsonString) throws JSONException {

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

        JSONArray resultsArray = movieJson.getJSONArray("results");
        return resultsArray;
    }

    // method to get a string array of movie poster urls from the json string
    public static String[] getMoviePostersFromJson(String movieJsonString)
        throws JSONException {

        String[] parsedMoviePosters = null;

        //getJsonArray returns array of movie json objects from "results" key
        JSONArray resultsArray = getJsonArray(movieJsonString);
         parsedMoviePosters = new String[resultsArray.length()];

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
    public static Movie getMovie(String movieJsonString, int position)
            throws JSONException{

        String title;
        String poster;
        String backdrop;
        String plot;
        String rating;
        String releaseDate;

        //getJsonArray returns array of movie json objects from "results" key
        JSONArray resultsArray = getJsonArray(movieJsonString);
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
