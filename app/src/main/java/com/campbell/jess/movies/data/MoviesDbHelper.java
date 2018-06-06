package com.campbell.jess.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.campbell.jess.movies.model.Movie;

public class MoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 3;

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
                        MoviesContract.MovieEntry.COLUMN_MOVIE_ID       + " INTEGER NOT NULL, "                 +
                        MoviesContract.MovieEntry.COLUMN_TITLE          + " CHAR(250) NOT NULL, "               +
                        MoviesContract.MovieEntry.COLUMN_BACKGROUND_URL + " CHAR(250) NOT NULL, "                 +
                        MoviesContract.MovieEntry.COLUMN_PLOT           + " TEXT NOT NULL, "                       +
                        MoviesContract.MovieEntry.COLUMN_POSTER_URL + " CHAR(250) NOT NULL" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    /**
     * if you change the update version number of the db, onUpgrade fires, discarding old data and calling onCreate
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
