package com.campbell.jess.movies;

import android.content.Context;
import android.os.AsyncTask;
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

        context = getApplicationContext();

        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this,"position"+position, Toast.LENGTH_SHORT).show();
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
                String[] simpleJsonMovieData = MovieJsonUtils.getMoviePostersFromJson(jsonMovieResponse);
                return simpleJsonMovieData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //TODO use a recycler view with RecyclerView.GridLayoutManager
        //TODO create a data model like in the sandwich app to store the data for each movie
        @Override
        protected void onPostExecute(String[] jsonMovieResponse){
            if (jsonMovieResponse != null) {
                posters = jsonMovieResponse;
                Log.v(TAG, "first poster " + posters[0]);
                //Log.v(TAG, "context "+context);

                ImageAdapter adapter = new ImageAdapter(context, posters);
                gridView.setAdapter(adapter);
            }
        }
    }

}
