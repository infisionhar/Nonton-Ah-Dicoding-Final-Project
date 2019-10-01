package com.hariz.noah;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hariz.noah.Adapter.MovieAdapter;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Network.Database.FavHelper;
import com.hariz.noah.Network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

import static android.support.constraint.Constraints.TAG;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<MovieModel> list;
    private FavHelper favoriteHelper;
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        favoriteHelper = new FavHelper(mContext);
        favoriteHelper.open();
        list = new ArrayList<>();
        list.addAll(favoriteHelper.query());

        Log.e(TAG, "huwi " + list.size() + "");
        if (list.size() == 0) {
            Toast.makeText(mContext, "tidak ada data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (list.size() == 0){
            Log.d("get count" , "list 0");
        }
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        String img_poster = RetrofitHelper.BASE_URL_IMAGE + "w185" +list.get(position).getPosterPath();
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(img_poster)
                    .submit(512, 512)
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bitmap);

            Log.d("hwwww", "getViewAt: "+bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rv.setTextViewText(R.id.banner_text, list.get(position).getTitle());

        Bundle bundle = new Bundle();
        bundle.putInt(FavWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
