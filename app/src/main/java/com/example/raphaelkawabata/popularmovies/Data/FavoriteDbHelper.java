package com.example.raphaelkawabata.popularmovies.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.raphaelkawabata.popularmovies.Models.MovieInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael.kawabata on 10/11/2017.
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 5;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + FavoritedMovieContract.FavoritesEntry.TABLE_NAME + " (" +
                        FavoritedMovieContract.FavoritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FavoritedMovieContract.FavoritesEntry.COLUMN_MOVIE + " TEXT NOT NULL, " +
                        FavoritedMovieContract.FavoritesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                        FavoritedMovieContract.FavoritesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        FavoritedMovieContract.FavoritesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                        FavoritedMovieContract.FavoritesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                        FavoritedMovieContract.FavoritesEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                        FavoritedMovieContract.FavoritesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                        "UNIQUE (" + FavoritedMovieContract.FavoritesEntry.COLUMN_MOVIE + ") ON CONFLICT IGNORE);";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoritedMovieContract.FavoritesEntry.TABLE_NAME);
        this.onCreate(db);
    }

    public long addFavoriteMovie(MovieInformation movieInformation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoritedMovieContract.FavoritesEntry.COLUMN_MOVIE, movieInformation.getId());
        contentValues.put(FavoritedMovieContract.FavoritesEntry.COLUMN_VOTE_AVERAGE, movieInformation.getVoteAverage());
        contentValues.put(FavoritedMovieContract.FavoritesEntry.COLUMN_TITLE, movieInformation.getOriginalTitle());
        contentValues.put(FavoritedMovieContract.FavoritesEntry.COLUMN_OVERVIEW, movieInformation.getOverview());
        contentValues.put(FavoritedMovieContract.FavoritesEntry.COLUMN_POSTER_PATH, movieInformation.getPosterPath());
        contentValues.put(FavoritedMovieContract.FavoritesEntry.COLUMN_BACKDROP_PATH, movieInformation.getBackdropPath());
        contentValues.put(FavoritedMovieContract.FavoritesEntry.COLUMN_RELEASE_DATE, movieInformation.getReleaseDate());
        long row = db.insertWithOnConflict(FavoritedMovieContract.FavoritesEntry.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        return row;
    }

    public List<MovieInformation> getAllFavorite() {
        List<MovieInformation> movieInformationList = new ArrayList<>();
        String query = "SELECT * FROM " + FavoritedMovieContract.FavoritesEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        MovieInformation movieInformation;
        if (cursor.moveToFirst()) {
            do {
                movieInformation = new MovieInformation();
                movieInformation.setId(cursor.getString(1));
                movieInformation.setVoteAverage(cursor.getString(2));
                movieInformation.setTitle(cursor.getString(3));
                movieInformation.setOverview(cursor.getString(4));
                movieInformation.setPosterPath(cursor.getString(5));
                movieInformation.setBackdropPath(cursor.getString(6));
                movieInformation.setReleaseDate(cursor.getString(7));
                movieInformationList.add(movieInformation);
            } while (cursor.moveToNext());
        }
        return movieInformationList;
    }

    public void deleteFavorite(MovieInformation movieInformation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoritedMovieContract.FavoritesEntry.TABLE_NAME,
                FavoritedMovieContract.FavoritesEntry.COLUMN_MOVIE + " = ?",
                new String[]{String.valueOf(movieInformation.getId())});
        db.close();
    }
}
