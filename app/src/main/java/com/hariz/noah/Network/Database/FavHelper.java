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

import static android.provider.BaseColumns._ID;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_DATE;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_MOVIEID;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_POSTER_PATH;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_TITLE;
//import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_USERRATING;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_USERRATING;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.TABLE_NAME;

public class FavHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private Context context;
    private static FavHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavHelper(Context context) {
        this.context = context;
    }

    public FavHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
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


    public Long addFavorite(MovieModel movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_MOVIEID, movie.getId());
        initialValues.put(COLUMN_TITLE, movie.getTitle());
        initialValues.put(COLUMN_POSTER_PATH, movie.getPosterPath());
        initialValues.put(COLUMN_USERRATING, "7.4");
        initialValues.put(COLUMN_PLOT_SYNOPSIS, movie.getOverview());
        initialValues.put(COLUMN_DATE, movie.getReleaseDate());

        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int deleteFav(int id) {
        return database.delete(TABLE_NAME, DatabaseContract.FavColumns.COLUMN_MOVIEID + " = " + id, null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }


    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

    public List<MovieModel> query() {
        String[] columns = {
                DatabaseContract.FavColumns._ID,
                DatabaseContract.FavColumns.COLUMN_MOVIEID,
                DatabaseContract.FavColumns.COLUMN_TITLE,
                COLUMN_USERRATING,
                DatabaseContract.FavColumns.COLUMN_POSTER_PATH,
                DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS

        };
        String sortOrder =
                DatabaseContract.FavColumns._ID + " ASC";
        List<MovieModel> favoriteList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseContract.FavColumns.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        cursor.moveToFirst();

//        MovieModel note;
//        if (cursor.getCount() > 0) {
//            do {
//
//                note = new  MovieModel();
//                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIEID)));
//
//                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
//                note.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLOT_SYNOPSIS)));
//                note.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
//                note.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)));
//
//                favoriteList.add(note);
//                cursor.moveToNext();
//
//            } while (!cursor.isAfterLast());
//        }
//        cursor.close();
//        return favoriteList;
//    }
        if (cursor.moveToFirst()) {
            do {
                MovieModel movie = new MovieModel();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.COLUMN_TITLE)));
//                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS)));

                favoriteList.add(movie);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return favoriteList;
    }

}
