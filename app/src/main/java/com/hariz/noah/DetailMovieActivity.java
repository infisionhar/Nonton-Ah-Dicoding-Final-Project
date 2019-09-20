package com.hariz.noah;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Network.Database.FavHelper;
import com.hariz.noah.Network.RetrofitHelper;

public class DetailMovieActivity extends AppCompatActivity {
    private MovieModel movie;

    TextView titleMovie, overView, Rating, Release, RatingTitle, ReleaseTitle;
    ImageView imageCover, imagePoster;
    String cover, poster_, movieTitle, overview, release, rating;

    public static String EXTRA_ID = "extra_id";
    public static String IS_FAVORITE = "is_favorite";

    private FavHelper favoriteHelper;
    private boolean isFavorite = false;
    private int favorite;

    int movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        imageCover = findViewById(R.id.img_detail_cover);
        imagePoster = findViewById(R.id.img_detail_poster);
        titleMovie = findViewById(R.id.tv_detail_title);
        overView = findViewById(R.id.tv_detail_overview);
        Rating = findViewById(R.id.text_movie_rating);
        Release = findViewById(R.id.text_movie_rilis);
        RatingTitle = findViewById(R.id.text_movie_rating_title);
        ReleaseTitle = findViewById(R.id.text_movie_rilis_title);

        favoriteHelper = new FavHelper(this);
        favoriteHelper.open();
        favorite = getIntent().getIntExtra(IS_FAVORITE, 0);
        if (favorite == 1) {
            isFavorite = true;

        }
        setTitle(movieTitle);
        MaterialFavoriteButton materialFavoriteButtonNice =
                findViewById(R.id.favorite_button);
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean f) {
                        if (f) {
                            savefav();
                            Snackbar.make(buttonView, "Added to Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                        } else {
                            favoriteHelper.deleteFav(getIntent().getIntExtra(EXTRA_ID, 0));

                            SharedPreferences.Editor editor = getSharedPreferences("com.hariz.noah.DetailActivity", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Removed", true);
                            editor.commit();
                            Snackbar.make(buttonView, "Removed from Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }

                });
        init();
    }
    private void init() {

        String rttitle = String.format(getResources().getString(R.string.rating));
        RatingTitle.setText(rttitle);
        String rtTgl = String.format(getResources().getString(R.string.tglrilis));
        ReleaseTitle.setText(rtTgl);

        movie = getIntent().getParcelableExtra("movies");
        cover = movie.getBackdropPath();
        poster_ = movie.getPosterPath();
        movieTitle = movie.getOriginalTitle();
        overview = movie.getOverview();
        movie_id = movie.getId();
        rating = Double.toString(movie.getVoteAverage());
        release = movie.getReleaseDate();
        setTitle(movieTitle);
        String poster_title = RetrofitHelper.BASE_URL_IMAGE + "w185" + poster_;
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
        MovieModel favorites = new MovieModel();
        favorites = new MovieModel();

        Double rate = movie.getVoteAverage();

        favorites.setId(movie_id);
        favorites.setTitle(movieTitle);
        favorites.setPosterPath(poster_);
        favorites.setOverview(overview);
        favorites.setVoteAverage(rate);
        favorites.setReleaseDate(release);
        favoriteHelper.addFavorite(favorites);
    }

}
