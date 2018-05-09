package com.campbell.jess.movies;

import android.content.Context;

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

    final String MOVIE_TITLE = "title";

    final String MOVIE_POSTER_URL = "url";

    final String MOVIE_PLOT = "plot";

    final String MOVIE_RATING = "rating";

    final String MOVIE_RELEASE_DATE = "date";

    //method to get a ready to use JSONArray of movies from a json string
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
        final String MOVIE_POSTER = "poster_path";

        String[] parsedMoviePosters = null;

        JSONArray resultsArray = getJsonArray(movieJsonString);
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

        String[] parsedMovieData = null;

        JSONArray resultsArray = getJsonArray(movieJsonString);
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
    //returns a movie object for a given position in the movie string
    public static Movie getMovie(String movieJsonString, int position)
            throws JSONException{
        final String MOVIE_TITLE = "title";

        final String MOVIE_POSTER_URL = "url";

        final String MOVIE_PLOT = "plot";

        final String MOVIE_RATING = "rating";

        final String MOVIE_RELEASE_DATE = "date";


        String title;
        String poster;
        String plot;
        String rating;
        String releaseDate;

        JSONArray resultsArray = getJsonArray(movieJsonString);
        JSONObject movieData = resultsArray.getJSONObject(position);

        title = movieData.getString(MOVIE_TITLE);
        poster = movieData.getString(MOVIE_POSTER_URL);
        plot = movieData.getString(MOVIE_PLOT);
        rating = movieData.getString(MOVIE_RATING);
        releaseDate = movieData.getString(MOVIE_RELEASE_DATE);

        Movie movie = new Movie(title, poster, plot, rating, releaseDate);
        return movie;
    }
}
