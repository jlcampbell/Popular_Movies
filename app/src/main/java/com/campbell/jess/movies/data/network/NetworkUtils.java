package com.campbell.jess.movies.data.network;

import android.content.Context;

import com.campbell.jess.movies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * utils for connecting to the MovieDB api, and returning data used by the app
 */


public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /* obtain a personal api key from www.themoviedb.org and put it here */
    private static final String API_KEY = "";

    private static String urlBase = "";

    /**
     * sets the urlBase for the api request to fill the main activity poster grid.
     * This should be one of the preference sortby values
     *
     * @param sortBy - describes whether to display movies in order of rating or popularity
     */

    public static void setUrlBase(String sortBy, Context context) {
        String popularity = context.getString(R.string.pref_sort_popularity);
        String rating = context.getString(R.string.pref_sort_rating);
        if (sortBy.equals(popularity)) {
            urlBase = context.getString(R.string.popularity_url_base);
        } else if (sortBy.equals(rating)) {
            urlBase = context.getString(R.string.rating_url_base);
        }

    }

    public static URL buildPopularUrl() {
        String urlString = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1";
        URL url = buildUrlFromString(urlString);
        return url;
    }

    public static URL buildRatedUrl() {
        String urlString = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY+"&language=en-US&page=1";
        URL url = buildUrlFromString(urlString);
        return url;
    }

    public static URL buildReviewUrl(int movie_id) {
        String review = "https://api.themoviedb.org/3/movie/"+String.valueOf(movie_id)+"/reviews?api_key="+API_KEY+"&language=en-US&page=1";
        URL url = buildUrlFromString(review);
        return url;
    }

    public static URL buildVideoUrl(int movie_id) {
        String videos = "https://api.themoviedb.org/3/movie/"+String.valueOf(movie_id)+"/videos?api_key="+API_KEY+"&language=en-US&page=1";
        URL url = buildUrlFromString(videos);
        return url;
    }

    public static URL buildUrlFromString(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


//TODO CONSOLIDATE BUILDURL METHODS
    /**
     * uses API_KEY provided by user above and urlbase to build a url to query movieDB api
     * @return url
     */
    public static URL buildUrl(Context context) {
        URL url = null;
        try {
            //url = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1");
            url = new URL(urlBase + API_KEY + context.getString(R.string.url_end));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * method to query api
     * @param url - url for querying moviedb api
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

}
