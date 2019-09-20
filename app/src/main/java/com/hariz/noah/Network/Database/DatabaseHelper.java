package com.hariz.noah.Network.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbfav";
    private static final int DATABASE_VERSION = 1;

    final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + DatabaseContract.FavColumns.TABLE_NAME + " (" +
            DatabaseContract.FavColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.FavColumns.COLUMN_MOVIEID + " INTEGER, " +
            DatabaseContract.FavColumns.COLUMN_TITLE + " TEXT NOT NULL, " +
            DatabaseContract.FavColumns.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL," +
            DatabaseContract.FavColumns.COLUMN_DATE + " TEXT NOT NULL " +
            "); ";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
