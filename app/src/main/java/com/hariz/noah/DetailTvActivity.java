package com.hariz.noah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariz.noah.Model.TvModel;
import com.hariz.noah.Network.RetrofitHelper;

public class DetailTvActivity extends AppCompatActivity {
    TvModel tv;
    TextView titleTv, overViewTV, RatingTV, ReleaseTV, RatingTitleTV, ReleaseTitleTV;
    ImageView imageCoverTV, imagePosterTV;
    String covertv, poster_tv, movieTitletv, overviewtv, releasetv, ratingtv;
    int tv_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        imageCoverTV = findViewById(R.id.img_detail_cover_tv);
        imagePosterTV = findViewById(R.id.img_detail_poster_tv);

        titleTv = findViewById(R.id.tv_detail_title_tv);
        overViewTV = findViewById(R.id.tv_detail_overview_tv);
        RatingTV = findViewById(R.id.text_tv_rating);
        ReleaseTV = findViewById(R.id.text_tv_rilis);
        RatingTitleTV = findViewById(R.id.text_tv_rating_title);
        ReleaseTitleTV = findViewById(R.id.text_tv_rilis_title);
        init();
    }

    private void init() {
        String rttitle = String.format(getResources().getString(R.string.rating));
        RatingTitleTV.setText(rttitle);
        String rtTgl = String.format(getResources().getString(R.string.tanggal_tayang));
        ReleaseTitleTV.setText(rtTgl);

        tv = getIntent().getParcelableExtra("TV");

        covertv = tv.getPosterPath();
        poster_tv = tv.getPosterPath();

        movieTitletv = tv.getOriginalName();
        overviewtv = tv.getOverview();
        tv_id = tv.getId();
        ratingtv = Double.toString(tv.getVoteAverage());
        releasetv = tv.getFirstAirDate();
//        setTitle(movieTitle);

        String poster_title = RetrofitHelper.BASE_URL_IMAGE +"w185"+ covertv;
        Glide.with(this)
                .load(poster_title)
                .into(imagePosterTV);
        String poster_bg = RetrofitHelper.BASE_URL_IMAGE +"w185"+ covertv;
        Glide.with(this)
                .load(poster_bg)
                .into(imageCoverTV);
        titleTv.setText(movieTitletv);
        overViewTV.setText(overviewtv);
        RatingTV.setText(ratingtv);
        ReleaseTV.setText(releasetv);
    }
}
