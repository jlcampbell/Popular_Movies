package com.campbell.jess.movies.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.campbell.jess.movies.R;
import com.campbell.jess.movies.Utilities.InjectorUtils;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.campbell.jess.movies.ui.settings.SettingsActivity;
import com.campbell.jess.movies.data.database.AppDatabase;
import com.campbell.jess.movies.ui.detail.DetailActivity;
import com.campbell.jess.movies.data.network.MovieJsonUtils;
import com.campbell.jess.movies.data.network.NetworkUtils;

import java.net.URL;
import java.util.List;


/**
 * MainActivity contains array of movie posters that can be selected to see more details about each movie
 */

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, PosterRecyclerViewAdapter.PosterAdapterOnClickHandler {

    //private GridView gridView;
    private Context context;
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private PosterRecyclerViewAdapter mRecyclerViewAdapter;
    private AppDatabase mAppDatabase;

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        setupSharedPreferences();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_posters);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerViewAdapter = new PosterRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mAppDatabase = AppDatabase.getInstance(this);
//TODO THIS NEEDS TO BE FIXED

        MainActivityViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this.getApplicationContext());
        Log.d(TAG, "onCreate: provided factory");
        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
        Log.d(TAG, "onCreate: created view model");
        loadPopularMovieDataFromViewModel();
        //setupViewModel();
    }

    private void loadMovieDataFromViewModel() {
        Log.d(TAG, "loadMovieDataFromRoom: putting observer in place");
        final Observer<List<MovieEntry>> movieObserver= listLiveData -> {
            mRecyclerViewAdapter.setmMovieEntries(listLiveData);
            if (listLiveData != null && listLiveData.size() != 0) showPosterDataView();
        };
        mViewModel.getMovies().observe(this, movieObserver);
    }
    private void loadPopularMovieDataFromViewModel() {
        final Observer<List<MovieEntry>> movieObserver= listLiveData -> {
            mRecyclerViewAdapter.setmMovieEntries(listLiveData);
            if (listLiveData != null && listLiveData.size() != 0) showPosterDataView();
        };
        mViewModel.getPopularMovies().observe(this, movieObserver);
    }
    private void loadRatedMovieDataFromViewModel() {
        final Observer<List<MovieEntry>> movieObserver= listLiveData -> {
            mRecyclerViewAdapter.setmMovieEntries(listLiveData);
            if (listLiveData != null && listLiveData.size() != 0) showPosterDataView();
        };
        mViewModel.getRatedMovies().observe(this, movieObserver);
    }
    private void loadFavoriteMovieDataFromViewModel() {
        final Observer<List<MovieEntry>> movieObserver= listLiveData -> {
            mRecyclerViewAdapter.setmMovieEntries(listLiveData);
            if (listLiveData != null && listLiveData.size() != 0) showPosterDataView();
        };
        mViewModel.getFavoriteMovies().observe(this, movieObserver);
    }



    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        NetworkUtils.setUrlBase(sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popularity)), this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_key))) {
            String sort = sharedPreferences.getString(getString(R.string.pref_sort_key), "");
            // if sort equals favorites
            if (sort.equals(context.getString(R.string.pref_sort_favorites))) {
                loadFavoriteMovieDataFromViewModel();
                getSupportActionBar().setTitle(getString(R.string.favorites));
            } else if (sort.equals(context.getString(R.string.pref_sort_popularity))){
                // if sort equals popularity
                loadPopularMovieDataFromViewModel();
                getSupportActionBar().setTitle(getString(R.string.popular_label));

            } else if (sort.equals(context.getString(R.string.pref_sort_rating))){
                // if sort equals popularity
                loadRatedMovieDataFromViewModel();
                getSupportActionBar().setTitle(getString(R.string.rating_label));

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void showPosterDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int movieId) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

}
