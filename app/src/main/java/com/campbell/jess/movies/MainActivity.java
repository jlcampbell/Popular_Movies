package com.campbell.jess.movies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;

//TODO add detail views for each movie
//TODO add a sort feature

public class MainActivity extends AppCompatActivity {

    private TextView mJsonTestTextView;

    private String[] posters;

    private GridView gridView;

    private Context context;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

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
                String[] posterUrls = MovieJsonUtils.getMoviePostersFromJson(jsonMovieResponse);
                return posterUrls;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //TODO use a recycler view with RecyclerView.GridLayoutManager
        //TODO create a data model like in the sandwich app to store the data for each movie
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
