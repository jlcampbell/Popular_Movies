package com.campbell.jess.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.support.v7.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.net.URL;


/**
 * MainActivity contains array of movie posters that can be selected to see more details about each movie
 */

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //private GridView gridView;
    private Context context;
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;

    private PosterRecyclerViewAdapter mRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        setupSharedPreferences();

        //gridView = findViewById(R.id.gridview);
/**
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
 **/
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_posters);






        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerViewAdapter = new PosterRecyclerViewAdapter();

        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        loadMovieData();
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


    private void loadMovieData() {
        new FetchMovieTask().execute();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_key))) {
            String sort = sharedPreferences.getString(getString(R.string.pref_sort_key), "");
            NetworkUtils.setUrlBase(sort, this);
            loadMovieData();
        }
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


    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL movieRequestUrl = NetworkUtils.buildUrl(getApplicationContext());


            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                return MovieJsonUtils.getMoviePostersFromJson(jsonMovieResponse, getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] posterUrls) {
            if (posterUrls != null) {
                showPosterDataView();
                //ImageAdapter adapter = new ImageAdapter(context, posterUrls);
                //gridView.setAdapter(adapter);
                mRecyclerViewAdapter.setmThumbPaths(posterUrls);
            }
        }
    }

}
