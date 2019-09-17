package com.hariz.noah.Network.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbnoteapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            DatabaseContract.FavColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.FavColumns.iditem + " INTEGER, " +
            DatabaseContract.FavColumns.title + " TEXT NOT NULL, " +
            DatabaseContract.FavColumns.deskripsi + " TEXT NOT NULL, " +
            DatabaseContract.FavColumns.date + " TEXT NOT NULL, " +
            DatabaseContract.FavColumns.jenis + " TEXT " +
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
