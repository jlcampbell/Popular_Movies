package com.campbell.jess.movies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility functions to handle The Movie Database JSON data.
 */

public class MovieJsonUtils {
    //TODO clean up duplicate code between the methods

    // method to get a string array of movie poster urls from the json string
    public static String[] getMoviePostersFromJson(String movieJsonString)
        throws JSONException {
        final String MOVIE_POSTER = "poster_path";

        final String MOVIE_MESSAGE_CODE = "cod";

        String[] parsedMoviePosters = null;

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
        parsedMoviePosters = new String[resultsArray.length()];

        //iterate through each movie
        for (int i=0; i < resultsArray.length(); i++) {
            //get the Json object representing the movie//
            JSONObject movieData = resultsArray.getJSONObject(i);
            String poster = "http://image.tmdb.org/t/p/" + "w185" + movieData.getString(MOVIE_POSTER);

            parsedMoviePosters[i] = poster;

        }
        return parsedMoviePosters;

    }



    //returns just the movie titles from the JSON string
    public static String[] getSimpleMovieStringsFromJson(Context context, String movieJsonString)
        throws JSONException {
        final String MOVIE_LIST = "list";

        final String MOVIE_TITLE = "title";

        final String MOVIE_POSTER_URL = "url";

        final String MOVIE_PLOT = "plot";

        final String MOVIE_RATING = "rating";

        final String MOVIE_RELEASE_DATE = "date";

        final String MOVIE_MESSAGE_CODE = "cod";

        String[] parsedMovieData = null;

        //---------------start refactor into new method for general parsing

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
        parsedMovieData = new String[resultsArray.length()];

        //iterate through each movie
        for (int i=0; i < resultsArray.length(); i++) {
            //get the Json object representing the movie//
            JSONObject movieData = resultsArray.getJSONObject(i);
            String title = movieData.getString(MOVIE_TITLE);

            parsedMovieData[i] = title;

        }

        //currently, parsed movie data returns just the title


        return parsedMovieData;
    }
}
