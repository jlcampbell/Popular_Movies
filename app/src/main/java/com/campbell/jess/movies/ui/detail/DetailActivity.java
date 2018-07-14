package com.campbell.jess.movies.ui.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import com.campbell.jess.movies.Utilities.InjectorUtils;
import com.campbell.jess.movies.data.database.AppDatabase;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.campbell.jess.movies.data.network.MovieJsonUtils;
import com.campbell.jess.movies.data.network.NetworkUtils;
import com.campbell.jess.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailersAdapterOnClickHandler {


    private int mMovieId;
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
    Button btn_unfavorite;

    Movie mMovie;

    String[] mTrailerIds;
    String[] mTrailerTitles;
    String[] mReviews;

    String TAG = this.getClass().getSimpleName();

    private DetailActivityViewModel mViewModel;

    private boolean mIsFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();



        //trailers adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerVeiwTrailers.setLayoutManager(linearLayoutManager);
        mRecyclerVeiwTrailers.setHasFixedSize(false);
        mTrailersAdapter = new TrailersAdapter(this);
        mRecyclerVeiwTrailers.setAdapter(mTrailersAdapter);

        //reviews adapter
        mReviewsAdapter = new ReviewsAdapter();
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);
        LinearLayoutManager reviewLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewReviews.setLayoutManager(reviewLinearLayoutManager);
        mRecyclerViewReviews.setHasFixedSize(false);

        Intent intentThatStartedActivity = getIntent();
        mMovieId = intentThatStartedActivity.getIntExtra("movieId", 0);

        DetailActivityViewModelFactory factory = InjectorUtils.provideDetailActivityViewModelFactory(this, mMovieId);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);

        mViewModel.getMovie().observe(this, movieEntry -> {
            if (movieEntry != null) populateUI(movieEntry);
        });
        mViewModel.getFavorite().observe(this,favoriteStatus -> {
            if (favoriteStatus != null) displayFavorite();
            else displayDefault();
        });

        new FetchTrailersTask().execute();
        new FetchReviewsTask().execute();
    }

    private void displayFavorite(){
        btn_favorite.setVisibility(View.INVISIBLE);
        btn_unfavorite.setVisibility(View.VISIBLE);
    }
    private void displayDefault(){
        btn_favorite.setVisibility(View.VISIBLE);
        btn_unfavorite.setVisibility(View.INVISIBLE);
    }

    private void populateUI(MovieEntry movie){
        Picasso.get().load(movie.getPoster()).into(iv_poster);
        tv_title.setText(movie.getTitle());
        tv_overview.setText(movie.getPlot());
        tv_rating.setText(movie.getRating());
        tv_releaseDate.setText(movie.getReleaseDate());
        getSupportActionBar().setTitle(movie.getTitle());
         }
     private void populateTrailers(){
         mTrailersAdapter.setTrailerTitles(mTrailerTitles);
     }
     private void populateReviews(){
         mReviewsAdapter.setReviewStrings(mReviews);
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

        btn_unfavorite = findViewById(R.id.btn_unfavorite);
        btn_unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onUnfavoriteButtonClicked();}
        });
        if (mIsFavorite){
            btn_favorite.setVisibility(View.INVISIBLE);
            btn_unfavorite.setVisibility(View.VISIBLE);
        }
    }

    private void onUnfavoriteButtonClicked(){
        mViewModel.deleteFavorite();
        btn_favorite.setVisibility(View.VISIBLE);
        btn_unfavorite.setVisibility(View.INVISIBLE);
    }

    private void onFavoriteButtonClicked(){
        mViewModel.addFavorite();
        btn_favorite.setVisibility(View.INVISIBLE);
        btn_unfavorite.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        Toast mToast = Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT);
        mToast.show();
        String id = mTrailerIds[position];
        goToYouTube(getApplicationContext(), id);
    }

//TODO combine reveiws and trailer requests into one api call
//TODO move reviews and trailer requests to datasource
    public class FetchReviewsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL reviewRequestUrl = NetworkUtils.buildReviewUrl(mMovieId);
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
                mReviews = reviews;
                populateReviews();
            }
        }
    }

    public class FetchTrailersTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL videoRequestUrl = NetworkUtils.buildVideoUrl(mMovieId);
            try {
                String jsonTrailerMovieResponse = NetworkUtils.getResponseFromHttpUrl(videoRequestUrl);
                return jsonTrailerMovieResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonTrailerMovieResponse){
            try {
                mTrailerIds = MovieJsonUtils.getTrailersFromJson(jsonTrailerMovieResponse, getApplicationContext());
                mTrailerTitles = MovieJsonUtils.getTrailerTitlesFromJson(jsonTrailerMovieResponse, getApplicationContext());
                populateTrailers();
                mRecyclerVeiwTrailers.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void goToYouTube(Context context, String id){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    }
