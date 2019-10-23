package com.hariz.favmodule;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.hariz.favmodule.DatabaseContract.getColumnInt;
import static com.hariz.favmodule.DatabaseContract.getColumnString;

public class FavModel implements Parcelable {
    private int id;
    private String title;
    private String release_date;
    private String overview;
    private String poster;

    protected FavModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        poster = in.readString();
    }

    public FavModel(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.FavColumns._ID);
        this.title = getColumnString(cursor, DatabaseContract.FavColumns.COLUMN_TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS);
        this.release_date = getColumnString(cursor, DatabaseContract.FavColumns.COLUMN_DATE);
        this.poster = getColumnString(cursor, DatabaseContract.FavColumns.COLUMN_POSTER_PATH);
    }
    public static final Creator<FavModel> CREATOR = new Parcelable.Creator<FavModel>() {
        @Override
        public FavModel createFromParcel(Parcel in) {
            return new FavModel(in);
        }

        @Override
        public FavModel[] newArray(int size) {
            return new FavModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeString(poster);
    }

    public FavModel() {

    }

    public int getId() {
        return id;
    }

    public static Parcelable.Creator<FavModel> getCREATOR() {
        return CREATOR;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
