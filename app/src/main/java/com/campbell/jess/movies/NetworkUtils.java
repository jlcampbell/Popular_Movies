package com.campbell.jess.movies;

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

    //public static final String POPULAR_MOVIES_URL;

    public static URL buildUrl() {
        URL url = null;
        try {
            url = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1");
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
