package com.campbell.jess.movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mJsonTestTextView;

    private String[] posters;

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///TODO currently trying to get image adapter to populate with my movie poster urls
        mJsonTestTextView = (TextView) findViewById(R.id.tv_json_test);
        GridView gridView = (GridView) findViewById(R.id.gridview);
       // ImageAdapter adapter = new ImageAdapter(this);
       // gridView.setAdapter(adapter);
        loadMovieData();
    }

    private void loadMovieData() {
        new FetchMovieTask().execute();
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL movieRequestUrl = NetworkUtils.buildUrl();
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                //String[] simpleJsonMovieData = MovieJsonUtils.getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);
                String[] simpleJsonMovieData = MovieJsonUtils.getMoviePostersFromJson(jsonMovieResponse);
                return simpleJsonMovieData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //TODO eventually use a recycler view with RecyclerView.GridLayoutManager
        //TODO 1 create a data model like in the sandwich app to store the data for each movie
        //TODO have each movie url go to setmThumbPaths(String[])
        @Override
        protected void onPostExecute(String[] jsonMovieResponse){
            if (jsonMovieResponse != null) {
                posters = jsonMovieResponse;
                ImageAdapter adapter = new ImageAdapter(MainActivity.this, posters);
                gridView.setAdapter(adapter);
            }
        }
    }

}
