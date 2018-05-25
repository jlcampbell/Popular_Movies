package com.campbell.jess.movies;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /* omit api key from public repos */
    private static final String API_KEY = "";

    private static String urlBase = "https://api.themoviedb.org/3/movie/popular?api_key=";

    public static String getUrlBase(){
        return urlBase;
    }
    /**
     * sets the urlBase for the api request. This should be one of the preference sortby values
     * @param sortBy
     */
    public static void setUrlBase(String sortBy, Context context){
        Log.v(TAG, "sort by "+sortBy);
        String popularity = context.getString(R.string.pref_sort_popularity);
        String rating = context.getString(R.string.pref_sort_rating);
        Log.v(TAG, popularity+" "+rating);
        if (sortBy.equals(popularity)){
            urlBase = "https://api.themoviedb.org/3/movie/popular?api_key=";
        } else if (sortBy.equals(rating)){
            urlBase = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
        }
    }


    public static URL buildUrl() {
        URL url = null;
        try {
            //url = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1");
            url = new URL(urlBase+API_KEY+"&language=en-US&page=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "build URL "+ url);


        return url;
    }

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
