package com.hariz.noah.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariz.noah.DetailTvActivity;
import com.hariz.noah.Model.TvModel;
import com.hariz.noah.Network.RetrofitHelper;
import com.hariz.noah.R;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.holder> {
    private Context mContext;
    private List<TvModel> list;


    public TvAdapter(Context mContext, List<TvModel> tvModels) {
        this.mContext = mContext;
        this.list = tvModels;
    }

    public TvAdapter(Context context) {
        this.mContext = context;
    }
    public void setData(List<TvModel> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }
    public List<TvModel> getListFavorite() {
        return list;
    }

    public void setListFavorite(List<TvModel> listFavorites) {
        this.list = listFavorites;
    }

    @Override
    public TvAdapter.holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_tv, viewGroup, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        holder.title.setText(list.get(i).getOriginalName());
        holder.overview.setText(list.get(i).getOverview());
        String img_poster = RetrofitHelper.BASE_URL_IMAGE + "w185" +list.get(i).getPosterPath();
        Glide.with(mContext)
                .load(img_poster)
                .into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class holder extends RecyclerView.ViewHolder {
        public TextView title, overview;
        public ImageView thumbnail;

        public holder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_tv_title);
            overview = view.findViewById(R.id.tv_tv_sinopsis);
            thumbnail = view.findViewById(R.id.img_tv_poster);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    TvModel item = list.get(i);
                    Intent intent = new Intent(mContext, DetailTvActivity.class);
                    intent.putExtra("TV", item);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
