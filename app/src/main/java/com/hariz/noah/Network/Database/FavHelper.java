package com.hariz.noah.Network.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hariz.noah.Model.MovieModel;

import java.util.ArrayList;
import java.util.List;

import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.TABLE_NAME;

public class FavHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }


    public static Long addFavorite(MovieModel movie) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.FavColumns.iditem, movie.getId());
        values.put(DatabaseContract.FavColumns.title, movie.getOriginalTitle());
        values.put(DatabaseContract.FavColumns.deskripsi, movie.getOverview());
        values.put(DatabaseContract.FavColumns.date, movie.getPosterPath());
        values.put(DatabaseContract.FavColumns.jenis, "Movie");
        return database.insert(DATABASE_TABLE, null, values);

    }

    public static int deleteFavorite(int id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.FavColumns.iditem + "=" + id, null);
    }

    public List<MovieModel> getAllFavorite() {
        String[] columns = {
                DatabaseContract.FavColumns._ID,
                DatabaseContract.FavColumns.iditem,
                DatabaseContract.FavColumns.title,
                DatabaseContract.FavColumns.deskripsi,
                DatabaseContract.FavColumns.date,
                DatabaseContract.FavColumns.jenis

        };
        String sortOrder =
                DatabaseContract.FavColumns._ID + " ASC";
        List<MovieModel> favoriteList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                MovieModel movie = new MovieModel();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex
                        (DatabaseContract.FavColumns.iditem))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex
                        (DatabaseContract.FavColumns.title)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex
                        (DatabaseContract.FavColumns.deskripsi))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex
                        (DatabaseContract.FavColumns.date)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex
                        (DatabaseContract.FavColumns.jenis)));

                favoriteList.add(movie);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return favoriteList;
    }

}
