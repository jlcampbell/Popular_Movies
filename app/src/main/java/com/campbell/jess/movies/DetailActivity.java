package com.campbell.jess.movies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailersAdapterOnClickHandler {


    private int position;
    private TextView tv_title;
    TextView tv_overview;
    TextView tv_releaseDate;
    TextView tv_rating;
    TextView tv_reviews;
    TextView tv_trailers;

    RecyclerView mRecyclerVeiwTrailers;
    TrailersAdapter mTrailersAdapter;

    RecyclerView mRecyclerViewReviews;
    ReviewsAdapter mReviewsAdapter;

    ImageView iv_poster;
    Button btn_favorite;

    Movie gMovie;

    AppDatabase mAppDatabase;

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

        LinearLayoutManager reviewLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewReviews.setLayoutManager(reviewLinearLayoutManager);
        mRecyclerViewReviews.setHasFixedSize(false);
        mReviewsAdapter = new ReviewsAdapter();
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);

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
        tv_reviews = findViewById(R.id.tv_reviews);
        tv_trailers = findViewById(R.id.tv_trailer);

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
        int id = gMovie.getId();
        String title = gMovie.getTitle();
        String poster = gMovie.getPoster();

        MovieEntry movieEntry = new MovieEntry(id, title, poster);

        mAppDatabase.movieDao().insertMovie(movieEntry);
        Toast mToast = Toast.makeText(this, title, Toast.LENGTH_SHORT );
        mToast.show();
    }

    @Override
    public void onClick(int position) {
        Toast mToast = Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT);
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
//TODO combine reveiws and trailer requests into one api call
    public class FetchReviewsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL reviewRequestUrl = NetworkUtils.buildReviewUrl(getApplicationContext(), gMovie.getId());
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
                gMovie.setReviews(reviews);
                new FetchTrailersTask().execute();
            }
        }
    }

    public class FetchTrailersTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL videoRequestUrl = NetworkUtils.buildVideoUrl(getApplicationContext(), gMovie.getId());
            try {

                //String[] reviews = MovieJsonUtils.getReviewsFromJson(jsonMovieReviewResponse, getApplicationContext());

                String jsonTrailerMovieResponse = NetworkUtils.getResponseFromHttpUrl(videoRequestUrl);
                String[] trailers = MovieJsonUtils.getTrailersFromJson(jsonTrailerMovieResponse, getApplicationContext());


                return trailers;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] trailers){
            if (trailers != null){
                gMovie.setTrailers(trailers);
                populateUI(gMovie);
                mRecyclerViewReviews.setVisibility(View.VISIBLE);
                mRecyclerVeiwTrailers.setVisibility(View.VISIBLE);
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
        //for (String review: reviews
        //     ) {
        //    tv_reviews.append(review + "\n");
        //}
        mReviewsAdapter.setReviewStrings(reviews);

        String[] trailers = movie.getTrailers();
        //for (String trailer: trailers){
        //    tv_trailers.append(trailer + "\n");
        //}
        mTrailersAdapter.setTrailerIDs(trailers);
    }

    private void goToYouTube(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
    }
