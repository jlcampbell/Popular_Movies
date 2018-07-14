package com.campbell.jess.movies.Utilities;

import android.content.Context;
import android.util.Log;

import com.campbell.jess.movies.AppExecutors;
import com.campbell.jess.movies.data.MoviesRepository;
import com.campbell.jess.movies.data.database.AppDatabase;
import com.campbell.jess.movies.data.network.MovieNetworkDataSource;
import com.campbell.jess.movies.ui.detail.DetailActivityViewModelFactory;
import com.campbell.jess.movies.ui.list.MainActivityViewModelFactory;

/**
 * Created by jlcampbell on 7/5/2018. Heavily referenced Build an App with Architecture Components tutorial at https://codelabs.developers.google.com/codelabs/build-app-with-arch-components/index.html
 */

public class InjectorUtils {
    private static String log_tag = "injectorUtils";
    public static MoviesRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MovieNetworkDataSource networkDataSource = MovieNetworkDataSource.getsInstance(context.getApplicationContext(), executors);
        return MoviesRepository.getInstance(database.movieDao(), networkDataSource, executors);
    }


    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        MoviesRepository moviesRepository = provideRepository(context.getApplicationContext());
        return new MainActivityViewModelFactory(moviesRepository);
    }

    public static DetailActivityViewModelFactory provideDetailActivityViewModelFactory(Context context, int id) {
        MoviesRepository moviesRepository = provideRepository(context.getApplicationContext());
        return new DetailActivityViewModelFactory(moviesRepository, id);
    }

}
