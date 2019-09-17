package com.hariz.noah;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Network.Database.FavHelper;
import com.hariz.noah.Network.RetrofitHelper;

public class DetailMovieActivity extends AppCompatActivity {
    private MovieModel movie;

    TextView titleMovie, overView, Rating, Release, RatingTitle, ReleaseTitle;
    ImageView imageCover, imagePoster;
    String cover, poster_, movieTitle, overview, release, rating;
    private FavHelper databaseFavoritDatabaseFavHelper;

    private final AppCompatActivity activity = DetailMovieActivity.this;
    int movie_id;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        movie = getIntent().getParcelableExtra("movies");
        cover = movie.getBackdropPath();
        poster_ = movie.getPosterPath();
        movieTitle = movie.getOriginalTitle();
        overview = movie.getOverview();
        movie_id = movie.getId();
        rating = Double.toString(movie.getVoteAverage());
        release = movie.getReleaseDate();
        setTitle(movieTitle);
        Button FavoriteButton =
                (Button) findViewById(R.id.save_fav);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isEdit){
                SharedPreferences.Editor editor = getSharedPreferences("com.hariz.noah.DetailActivity", MODE_PRIVATE).edit();
                editor.putBoolean("Favorite Added", true);
                editor.commit();
                savefav();
                Snackbar.make(v, "Added to Favorite",
                        Snackbar.LENGTH_SHORT).show();
//                }else{
//                    int movie_id = getIntent().getExtras().getInt("id");
//                    databaseFavoritDatabaseFavHelper = new FavHelper(DetailMovieActivity.this);
//                    FavHelper.deleteFavorite(movie_id);
//
//                    SharedPreferences.Editor editor = getSharedPreferences("com.hariz.noah.DetailActivity", MODE_PRIVATE).edit();
//                    editor.putBoolean("Favorite Removed", true);
//                    editor.commit();
//                    Snackbar.make(v, "Removed from Favorite",
//                            Snackbar.LENGTH_SHORT).show();
//                }
//
//
            }
        });

        imageCover = findViewById(R.id.img_detail_cover);
        imagePoster = findViewById(R.id.img_detail_poster);
        titleMovie = findViewById(R.id.tv_detail_title);
        overView = findViewById(R.id.tv_detail_overview);
        Rating = findViewById(R.id.text_movie_rating);
        Release = findViewById(R.id.text_movie_rilis);
        RatingTitle = findViewById(R.id.text_movie_rating_title);
        ReleaseTitle = findViewById(R.id.text_movie_rilis_title);
        init();
    }

    private void init() {
        String rttitle = String.format(getResources().getString(R.string.rating));
        RatingTitle.setText(rttitle);
        String rtTgl = String.format(getResources().getString(R.string.tglrilis));
        ReleaseTitle.setText(rtTgl);

        String poster_title = RetrofitHelper.BASE_URL_IMAGE + poster_;
        Glide.with(this)
                .load(poster_title)
                .into(imagePoster);
        Glide.with(this)
                .load(poster_title)
                .into(imageCover);
        titleMovie.setText(movieTitle);
        overView.setText(overview);
        Rating.setText(rating);
        Release.setText(release);
    }

    public void savefav() {
        databaseFavoritDatabaseFavHelper = new FavHelper(activity);
        movie = new MovieModel();
        movie.setId(movie_id);
        movie.setTitle(movieTitle);
        movie.setOverview(overview);
        movie.setReleaseDate(release);
        movie.setOverview(overview);
        FavHelper.addFavorite(movie);
    }
}
