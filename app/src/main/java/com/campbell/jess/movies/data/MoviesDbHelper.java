package com.campbell.jess.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time
     * @param sqLiteDatabase the database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + " (" +
                        MoviesContract.MovieEntry._ID                   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MoviesContract.MovieEntry.COLUMN_MOVIE_ID       + " INTEGER, "                 +
                        MoviesContract.MovieEntry.COLUMN_BACKGROUND_URL + " CHAR(250), "                 +
                        MoviesContract.MovieEntry.COLUMN_PLOT           + " TEXT, "                       +
                        MoviesContract.MovieEntry.COLUMN_POSTER_URL + " CHAR(250)" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
