package com.hariz.noah.Network.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final class FavColumns implements BaseColumns{

        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String COLUMN_DATE = "date";
    }
}
