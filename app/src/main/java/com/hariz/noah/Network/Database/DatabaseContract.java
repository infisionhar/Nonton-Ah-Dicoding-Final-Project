package com.hariz.noah.Network.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_NAME = "favorite";

    public static final class FavColumns implements BaseColumns {
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_JENIS = "jenis";

    }

    public static final String AUTHORITY = "com.hariz.noah";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

}
