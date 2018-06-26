package com.campbell.jess.movies.utilities;

import android.content.Context;

import com.campbell.jess.movies.R;
import com.campbell.jess.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Objects;

/**
 * Utility functions to handle The Movie Database JSON data.
 */

public class MovieJsonUtils {

    private static String MOVIE_ID;
    private static String MOVIE_TITLE;
    private static String MOVIE_POSTER_URL;
    private static String MOVIE_BACKDROP_URL;
    private static String MOVIE_PLOT;
    private static String MOVIE_RATING;
    private static String MOVIE_RELEASE_DATE;
    private static String MOVIE_REVIEW;
    private static String MOVIE_TRAILER_ID;
    private static String MOVIE_TRAILER_TITLE;


    //method to get a ready to use JSONArray with entries for each movie from a json string
    public static JSONArray getJsonArray(String movieJsonString, Context context) throws JSONException {

        MOVIE_ID = context.getString(R.string.movie_id_key);

        MOVIE_TITLE = context.getString(R.string.movie_title_key) ;

        MOVIE_POSTER_URL = context.getString(R.string.movie_poster_url_key);

        MOVIE_BACKDROP_URL = context.getString(R.string.movie_backdrop_url_key);

        MOVIE_PLOT = context.getString(R.string.movie_plot_key);

        MOVIE_RATING = context.getString(R.string.movie_rating_key);

        MOVIE_RELEASE_DATE = context.getString(R.string.movie_release_date_key);

        MOVIE_REVIEW = context.getString(R.string.movie_review_key);

        MOVIE_TRAILER_ID = context.getApplicationContext().getString(R.string.movie_trailer_id_key);

        MOVIE_TRAILER_TITLE = context.getResources().getString(R.string.movie_trailer_title_key);



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

    public static String[] getReviewsFromJson(String reviewJsonString, Context context)
        throws JSONException {

        String[] parsedReviews;

        JSONArray reviewsArray = getJsonArray(reviewJsonString, context);
        parsedReviews = new String[Objects.requireNonNull(reviewsArray).length()];

        for (int i=0; i < reviewsArray.length(); i++) {
            JSONObject movieReviews = reviewsArray.getJSONObject(i);
            String review = movieReviews.getString(MOVIE_REVIEW);
            parsedReviews[i] = review;
        }
        return parsedReviews;

    }

    public static String[] getTrailersFromJson(String trailerJsonString, Context context)
        throws JSONException {

        String[] parsedTrailers;

        JSONArray trailersArray = getJsonArray(trailerJsonString, context);
        parsedTrailers = new String[Objects.requireNonNull(trailersArray).length()];

        for (int i=0; i<trailersArray.length(); i++) {
            JSONObject movieTrailers = trailersArray.getJSONObject(i);
            String trailer = movieTrailers.getString(MOVIE_TRAILER_ID);
            parsedTrailers[i] = trailer;
        }
        return parsedTrailers;
    }

    public static String[] getTrailerTitlesFromJson(String trailerTitleJsonString, Context context)
        throws JSONException {

        String[] parsedTrailerTitles;

        JSONArray trailerTitlesArray = getJsonArray(trailerTitleJsonString, context);
        parsedTrailerTitles = new String[Objects.requireNonNull(trailerTitlesArray).length()];

        for (int i=0; i< parsedTrailerTitles.length; i++){
            JSONObject trailerTitles = trailerTitlesArray.getJSONObject(i);
            String trailerTitle = trailerTitles.getString(MOVIE_TRAILER_TITLE);
            parsedTrailerTitles[i] = trailerTitle;
        }
        return parsedTrailerTitles;
    }



    //returns a Movie object for a given position in the movie string
    public static Movie getMovie(String movieJsonString, int position, Context context)
            throws JSONException{

        List<String> reviews;
        //some kind of movie trailer variable

        //getJsonArray returns array of movie json objects from "results" key
        JSONArray resultsArray = getJsonArray(movieJsonString, context);

        //movieData should be a JSONObject for a single movie
        JSONObject movieData = resultsArray.getJSONObject(position);

        //get reviews

        int id = movieData.getInt(MOVIE_ID);
        String title = movieData.getString(MOVIE_TITLE);
        String backdrop = "http://image.tmdb.org/t/p/" + "w500"+ movieData.getString(MOVIE_BACKDROP_URL);
        String plot = movieData.getString(MOVIE_PLOT);
        String rating = movieData.getString(MOVIE_RATING);
        String releaseDate = movieData.getString(MOVIE_RELEASE_DATE);

        //reviews = reviewData.getString(

        Movie movie = new Movie(id, title, backdrop, plot, rating, releaseDate);
        return movie;
    }
}
