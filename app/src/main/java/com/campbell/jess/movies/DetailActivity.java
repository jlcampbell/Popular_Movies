package com.campbell.jess.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.campbell.jess.movies.model.Movie;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    private int position;
    private Movie mMovie;
    TextView test;
    String TAG = "detail Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        test = findViewById(R.id.testText);

        Intent intentThatStartedActivity = getIntent();
        position = intentThatStartedActivity.getIntExtra("position", 0);
        new FetchDetailsTask().execute();
    }

    public class FetchDetailsTask extends AsyncTask<String, Void, Movie> {

        @Override
        protected Movie doInBackground(String... strings) {
            URL movieRequestUrl = NetworkUtils.buildUrl();
            Log.v(TAG, "fetch details task- url is: "+movieRequestUrl.toString());

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                Log.v(TAG, "jsonMovieResponse is "+jsonMovieResponse);
                Movie movie = MovieJsonUtils.getMovie(jsonMovieResponse, position);


                return movie;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie movie){
            if (movie != null) {
                mMovie = movie;
                populateUI(mMovie);
            }

        }
    }

    private void populateUI(Movie movie){

        test.setText(movie.getTitle());
    }
    }
