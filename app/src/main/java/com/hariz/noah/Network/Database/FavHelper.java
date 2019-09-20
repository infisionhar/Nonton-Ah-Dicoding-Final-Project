package com.hariz.noah.Network.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hariz.noah.Model.FavModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_DATE;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_MOVIEID;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_POSTER_PATH;
import static com.hariz.noah.Network.Database.DatabaseContract.FavColumns.COLUMN_TITLE;
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


    public Long addFavorite(FavModel movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_TITLE, movie.getTitle());
        initialValues.put(COLUMN_PLOT_SYNOPSIS, movie.getDescription());
        initialValues.put(COLUMN_DATE, movie.getDate());
        initialValues.put(COLUMN_POSTER_PATH, movie.getPoster());
        return database.insert(DATABASE_TABLE, null, initialValues);

    }

    public int delete(int id) {
        return database.delete(TABLE_NAME, COLUMN_MOVIEID + " = " + id , null);
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

    public ArrayList<FavModel> query() {
        ArrayList<FavModel> arrayList = new ArrayList<FavModel>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        FavModel note;
        if (cursor.getCount() > 0) {
            do {

                note = new FavModel();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setId_item(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIEID)));

                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLOT_SYNOPSIS)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                note.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)));
                arrayList.add(note);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

}
