package com.hariz.noah.Network.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_DATE;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_JENIS;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_MOVIEID;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_POSTER_PATH;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_TITLE;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_USERRATING;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbfav";
    private static final int DATABASE_VERSION = 2;

    final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + DatabaseContract.FavColumns.TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            COLUMN_MOVIEID + " INTEGER, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_POSTER_PATH + " TEXT, " +
            COLUMN_USERRATING + " REAL, " +
            COLUMN_PLOT_SYNOPSIS + " TEXT," +
            COLUMN_DATE + " TEXT, " +
            COLUMN_JENIS + " TEXT " +
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
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }
}
