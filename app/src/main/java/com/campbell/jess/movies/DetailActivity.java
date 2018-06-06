package com.campbell.jess.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.campbell.jess.movies.utilities.MovieJsonUtils;
import com.campbell.jess.movies.utilities.NetworkUtils;
import com.campbell.jess.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    private int position;
    private TextView tv_title;
    TextView tv_overview;
    TextView tv_releaseDate;
    TextView tv_rating;
    ImageView iv_poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         tv_title = findViewById(R.id.tv_title);
         tv_releaseDate = findViewById(R.id.tv_releaseDate);
         tv_rating = findViewById(R.id.tv_rating);
         tv_overview = findViewById(R.id.tv_overview);
         iv_poster = findViewById(R.id.iv_poster);

        Intent intentThatStartedActivity = getIntent();
        position = intentThatStartedActivity.getIntExtra("position", 0);
        new FetchDetailsTask().execute();
    }

    public class FetchDetailsTask extends AsyncTask<String, Void, Movie> {

        @Override
        protected Movie doInBackground(String... strings) {
            URL movieRequestUrl = NetworkUtils.buildUrl(getApplicationContext());

            try {
                //try to get http response
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                return MovieJsonUtils.getMovie(jsonMovieResponse, position, getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie movie){
            if (movie != null) {
                Movie mMovie = movie;
                populateUI(mMovie);
            }

        }
    }

    private void populateUI(Movie movie){
        Picasso.get().load(movie.getPoster()).into(iv_poster);
        tv_title.setText(movie.getTitle());
        tv_overview.setText(movie.getPlot());
        tv_rating.setText(movie.getRating());
        tv_releaseDate.setText(movie.getReleaseDate());
    }
    }
