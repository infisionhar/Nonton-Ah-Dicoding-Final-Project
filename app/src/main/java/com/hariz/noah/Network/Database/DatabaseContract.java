package com.hariz.noah.Network.Database;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class FavColumns implements BaseColumns{
        public static final String TABLE_NAME = "favorite";
        public static final String iditem = "id_item";
        public static final String title = "title";
        public static final String deskripsi = "deskripsi";
        public static final String date = "date";
        public static final String jenis = "jenis";
    }
}
