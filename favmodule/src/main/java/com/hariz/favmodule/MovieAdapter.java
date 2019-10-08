package com.hariz.favmodule;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.hariz.favmodule.DatabaseContract.FavColumns.COLUMN_PLOT_SYNOPSIS;
import static com.hariz.favmodule.DatabaseContract.FavColumns.COLUMN_POSTER_PATH;
import static com.hariz.favmodule.DatabaseContract.FavColumns.COLUMN_TITLE;
import static com.hariz.favmodule.DatabaseContract.getColumnString;


public class MovieAdapter extends CursorAdapter {
    private Context mContex;

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.mContex = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView tvJudul = (TextView) view.findViewById(R.id.tv_movie_title);
            TextView tvDesc = (TextView) view.findViewById(R.id.tv_movie_sinopsis);
            ImageView imgMovie = (ImageView) view.findViewById(R.id.img_movie_poster);

            tvJudul.setText(getColumnString(cursor, COLUMN_TITLE));
            tvDesc.setText(getColumnString(cursor, COLUMN_PLOT_SYNOPSIS));
            String url = "http://image.tmdb.org/t/p/w185/";
            Glide.with(context).load(url + getColumnString(cursor, COLUMN_POSTER_PATH)).into(imgMovie);
        }
    }

    public void setListFavorite(List<FavModel> list) {
    }
}
