package com.campbell.jess.movies.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campbell.jess.movies.AppExecutors;
import com.campbell.jess.movies.R;
import com.campbell.jess.movies.data.database.AppDatabase;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.campbell.jess.movies.data.network.MovieJsonUtils;
import com.campbell.jess.movies.data.network.NetworkUtils;
import com.campbell.jess.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailersAdapterOnClickHandler {


    private int position;
    private TextView tv_title;
    TextView tv_overview;
    TextView tv_releaseDate;
    TextView tv_rating;

    RecyclerView mRecyclerVeiwTrailers;
    TrailersAdapter mTrailersAdapter;

    RecyclerView mRecyclerViewReviews;
    ReviewsAdapter mReviewsAdapter;

    ImageView iv_poster;
    Button btn_favorite;

    Movie mMovie;

    AppDatabase mAppDatabase;

    String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerVeiwTrailers.setLayoutManager(linearLayoutManager);
        mRecyclerVeiwTrailers.setHasFixedSize(false);
        mTrailersAdapter = new TrailersAdapter(this);
        mRecyclerVeiwTrailers.setAdapter(mTrailersAdapter);

        mReviewsAdapter = new ReviewsAdapter();
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);
        LinearLayoutManager reviewLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewReviews.setLayoutManager(reviewLinearLayoutManager);
        mRecyclerViewReviews.setHasFixedSize(false);


        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        Intent intentThatStartedActivity = getIntent();
        position = intentThatStartedActivity.getIntExtra("position", 0);
       // new FetchDetailsTask().execute();
    }

    private void initViews(){
        tv_title = findViewById(R.id.tv_title);
        tv_releaseDate = findViewById(R.id.tv_releaseDate);
        tv_rating = findViewById(R.id.tv_rating);
        tv_overview = findViewById(R.id.tv_overview);

        mRecyclerVeiwTrailers = findViewById(R.id.recyclerview_trailers);

        mRecyclerViewReviews = findViewById(R.id.recyclerview_reviews);

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
        int id = mMovie.getId();
        String title = mMovie.getTitle();
        String poster = mMovie.getPoster();
        String plot = mMovie.getPlot();
        String rating = mMovie.getRating();
        String releaseDate = mMovie.getReleaseDate();

        final MovieEntry movieEntry = new MovieEntry(id, title, poster, plot, rating, releaseDate );
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.movieDao().insertMovie(movieEntry);
                Log.v(TAG, "inserting movie to db");
                finish();
            }
        });

    }

    @Override
    public void onClick(int position) {
        Toast mToast = Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT);
        mToast.show();
        String id = mMovie.getTrailerIds()[position];
  //      goToYouTube(getApplicationContext(), id);
    }
/**
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
                mMovie = movie;
                new FetchReviewsTask().execute();
            }
        }
    }
//TODO combine reveiws and trailer requests into one api call
    public class FetchReviewsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL reviewRequestUrl = NetworkUtils.buildReviewUrl(getApplicationContext(), mMovie.getId());
            try {
                String jsonMovieReviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewRequestUrl);
                String[] reviews = MovieJsonUtils.getReviewsFromJson(jsonMovieReviewResponse, getApplicationContext());

                //String jsonTrailerMovieResponse = NetworkUtils.getResponseFromHttpUrl(videoRequestUrl);
                return reviews;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] reviews){
            if (reviews != null){
                mMovie.setReviews(reviews);
                new FetchTrailersTask().execute();
            }
        }
    }

    public class FetchTrailersTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL videoRequestUrl = NetworkUtils.buildVideoUrl(getApplicationContext(), mMovie.getId());
            try {

                //String[] reviews = MovieJsonUtils.getReviewsFromJson(jsonMovieReviewResponse, getApplicationContext());

                String jsonTrailerMovieResponse = NetworkUtils.getResponseFromHttpUrl(videoRequestUrl);



                return jsonTrailerMovieResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonTrailerMovieResponse){
            String[] trailers;
            String[] titles;
            try {
                trailers = MovieJsonUtils.getTrailersFromJson(jsonTrailerMovieResponse, getApplicationContext());
                mMovie.setTrailerIds(trailers);
                titles = MovieJsonUtils.getTrailerTitlesFromJson(jsonTrailerMovieResponse, getApplicationContext());
                mMovie.setTrailerTitles(titles);
                populateUI(mMovie);
                mRecyclerViewReviews.setVisibility(View.VISIBLE);
                mRecyclerVeiwTrailers.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void populateUI(Movie movie){
        Picasso.get().load(movie.getPoster()).into(iv_poster);
        tv_title.setText(movie.getTitle());
        tv_overview.setText(movie.getPlot());
        tv_rating.setText(movie.getRating());
        tv_releaseDate.setText(movie.getReleaseDate());

        String[] reviews = movie.getReviews();

        mReviewsAdapter.setReviewStrings(reviews);

        String[] trailerIds = movie.getTrailerIds();
        String[]trailerTitles = movie.getTrailerTitles();
        mTrailersAdapter.setTrailerTitles(trailerTitles);
    }

    private void goToYouTube(Context context, String id){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    } **/
    }
