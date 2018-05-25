package com.campbell.jess.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;

//TODO add detail labels
//TODO test with no internet connection

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String[] posters;
    private GridView gridView;
    private Context context;
    private static final String TAG = "MainActivity";
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        context = getApplicationContext();

        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this,"position"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
        setupSharedPreferences();
        loadMovieData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //updateUI();
    }

    //private void updateUI(){
    //    imageAdapter = new ImageAdapter()
    //}

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        NetworkUtils.setUrlBase(sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popularity)), this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            Toast.makeText(this, "settings now", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadMovieData() {

        new FetchMovieTask().execute();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v(TAG, "key "+key);
        Log.v(TAG, getString(R.string.pref_sort_key));
        if (key.equals(getString(R.string.pref_sort_key))){
            Log.v(TAG, "sort changed");
            String sort = sharedPreferences.getString(getString(R.string.pref_sort_key),"");
            Log.v(TAG, sort);
            NetworkUtils.setUrlBase(sort, this);
            Log.v(TAG, NetworkUtils.getUrlBase());
            loadMovieData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL movieRequestUrl = NetworkUtils.buildUrl();


            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                String[] posterUrls = MovieJsonUtils.getMoviePostersFromJson(jsonMovieResponse);
                Log.v(TAG, "fetching movie");
                Log.v(TAG, posterUrls[0]);
                return posterUrls;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //TODO use a recycler view with RecyclerView.GridLayoutManager

        @Override
        protected void onPostExecute(String[] posterUrls){
            if (posterUrls != null) {
                posters = posterUrls;

                ImageAdapter adapter = new ImageAdapter(context, posters);
                gridView.setAdapter(adapter);
            }
        }
    }

}
