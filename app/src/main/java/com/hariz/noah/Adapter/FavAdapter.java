package com.hariz.noah.Adapter;

import android.app.Activity;
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
import com.hariz.noah.Model.FavModel;
import com.hariz.noah.Network.RetrofitHelper;
import com.hariz.noah.R;

import java.util.LinkedList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private LinkedList<FavModel> listFavorites;
    private Activity activity;
    private Context context;

    public FavAdapter(Context context) {
        this.context = context;
    }

    public LinkedList<FavModel> getListFavorite() {
        return listFavorites;
    }

    public void setListFavorite(LinkedList<FavModel> listFavorites) {
        this.listFavorites = listFavorites;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fav_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(listFavorites.get(position).getTitle());
        holder.overview.setText(listFavorites.get(position).getDescription());
        holder.release.setText(listFavorites.get(position).getDate());

        String img_poster = RetrofitHelper.BASE_URL_IMAGE + "w185" + listFavorites.get(position).getPoster();
        Glide.with(context)
                .load(img_poster)
                .into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return getListFavorite().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, overview, rating, release;
        public ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_movie_title_fav);
            overview = view.findViewById(R.id.tv_movie_sinopsis_fav);
            thumbnail = view.findViewById(R.id.img_movie_poster_fav);
//            rating = (TextView) view.findViewById(R.id.text_movie_rating);
            release = view.findViewById(R.id.tv_movie_date_fav);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    FavModel item = listFavorites.get(i);
                    Intent intent = new Intent(context, DetailMovieActivity.class);
                    intent.putExtra("movies", item);
//                    intent.putExtra(DetailMovieActivity.EXTRA_ID, listFavorites.get(i).getId());
//                    intent.putExtra(DetailMovieActivity.IS_FAVORITE, 1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
