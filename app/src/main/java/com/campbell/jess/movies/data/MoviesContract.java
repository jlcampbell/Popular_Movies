package com.campbell.jess.movies.data;

import android.provider.BaseColumns;

public class MoviesContract {
    /*defines table contents of weather table */
    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_POSTER_URL = "url";

        public static final String COLUMN_BACKGROUND_URL = "background_url";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_PLOT = "plot";

        public static final String COLUMN_RATING = "rating";

    }
}
