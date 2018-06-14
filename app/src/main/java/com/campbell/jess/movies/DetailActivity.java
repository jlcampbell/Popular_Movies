package com.campbell.jess.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campbell.jess.movies.database.AppDatabase;
import com.campbell.jess.movies.database.MovieEntry;
import com.campbell.jess.movies.utilities.MovieJsonUtils;
import com.campbell.jess.movies.utilities.NetworkUtils;
import com.campbell.jess.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    //TODO add a favorite button
    //TODO create a class AddMovieActivity.java
    //TODO link AddMovieActivity to onclick of favorite button


    private int position;
    private TextView tv_title;
    TextView tv_overview;
    TextView tv_releaseDate;
    TextView tv_rating;
    ImageView iv_poster;
    Button btn_favorite;

    Movie gMovie;

    AppDatabase mAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        Intent intentThatStartedActivity = getIntent();
        position = intentThatStartedActivity.getIntExtra("position", 0);
        new FetchDetailsTask().execute();
    }

    private void initViews(){
        tv_title = findViewById(R.id.tv_title);
        tv_releaseDate = findViewById(R.id.tv_releaseDate);
        tv_rating = findViewById(R.id.tv_rating);
        tv_overview = findViewById(R.id.tv_overview);
        iv_poster = findViewById(R.id.iv_poster);

        btn_favorite = findViewById(R.id.btn_favorite);
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFavoriteButtonClicked();
            }
        });

    }

    private void onFavoriteButtonClicked(){
        int id = gMovie.getId();
        String title = gMovie.getTitle();
        String poster = gMovie.getPoster();

        MovieEntry movieEntry = new MovieEntry(id, title, poster);

        mAppDatabase.movieDao().insertMovie(movieEntry);
        Toast mToast = Toast.makeText(this, title, Toast.LENGTH_SHORT );
        mToast.show();
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
                gMovie = movie;
                new FetchReviewsTask().execute();
            }

        }
    }

    public class FetchReviewsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL reviewRequestUrl = NetworkUtils.buildReviewUrl(getApplicationContext(), gMovie.getId());
            try {
                String jsonMovieReviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewRequestUrl);
                String[] reviews = MovieJsonUtils.getReviewsFromJson(jsonMovieReviewResponse, getApplicationContext());
                return reviews;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] reviews){
            if (reviews != null){
                gMovie.setReviews(reviews);
                populateUI(gMovie);
            }
        }
    }

    private void populateUI(Movie movie){
        Picasso.get().load(movie.getPoster()).into(iv_poster);
        tv_title.setText(movie.getTitle());
        tv_overview.setText(movie.getPlot());
        tv_rating.setText(movie.getRating());
        tv_releaseDate.setText(movie.getReleaseDate());

        //TODO add reviews here
    }
    }
