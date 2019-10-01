package com.hariz.noah.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariz.noah.DetailMovieActivity;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Network.RetrofitHelper;
import com.hariz.noah.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.holder> {
    private Context mContext;
    private List<MovieModel> list;

    public MovieAdapter(Context mContext, List<MovieModel> list) {
        this.mContext = mContext;
        this.list = list;
    }


    public void setData(List<MovieModel> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }
    public void replaceAll(List<MovieModel> items) {
        list.clear();
        list = items;
        notifyDataSetChanged();
    }
    public List<MovieModel> getListFavorite() {
        return list;
    }

    public void setListFavorite(List<MovieModel> listFavorites) {
        this.list = listFavorites;
    }

    @Override
    public MovieAdapter.holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_movie, viewGroup, false);
        return new holder(view);
    }


    @Override
    public void onBindViewHolder(final MovieAdapter.holder viewHolder, final int i) {
        viewHolder.title.setText(list.get(i).getOriginalTitle());
        viewHolder.overview.setText(list.get(i).getOverview());
//        viewHolder.release.setText(list.get(i).getReleaseDate());
//        viewHolder.rating.setText(list.get(i).getVoteCount());
//
        String img_poster = RetrofitHelper.BASE_URL_IMAGE + "w185"+ list.get(i).getPosterPath();
        Glide.with(mContext)
                .load(img_poster)
                .into(viewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class holder extends RecyclerView.ViewHolder {

        public TextView title, overview, rating, release;
        public ImageView thumbnail;

        public holder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_movie_title);
            overview = view.findViewById(R.id.tv_movie_sinopsis);
            thumbnail = view.findViewById(R.id.img_movie_poster);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    MovieModel item = list.get(i);
                    Intent intent = new Intent(mContext, DetailMovieActivity.class);
                    intent.putExtra("movies", item);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
