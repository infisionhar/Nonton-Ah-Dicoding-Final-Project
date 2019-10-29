package com.hariz.noah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public static String IS_FAVORITE = "is_favorite";


    private FavHelper favoriteHelper;
    private int favorite;

    private boolean isFavorite = false;

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

        setTitle(movieTitle);
        favoriteHelper = new FavHelper(this);
        favoriteHelper.open();
        favorite = getIntent().getIntExtra(IS_FAVORITE, 0);
        setTitle(movieTitle);
        final MaterialFavoriteButton materialFavoriteButtonNice = findViewById(R.id.favorite_button);
        materialFavoriteButtonNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    savefav();
                    Toast.makeText(DetailMovieActivity.this, "Add Fav", Toast.LENGTH_SHORT).show();
                    isFavorite = true;
                    materialFavoriteButtonNice.setFavorite(isFavorite, true);
                } else {
                    favoriteHelper.deleteFav(movie.getId());
                    Toast.makeText(DetailMovieActivity.this, "Delete From Fav", Toast.LENGTH_SHORT).show();
                    isFavorite = false;
                    materialFavoriteButtonNice.setFavorite(isFavorite, true);
                }
            }
        });
        init();
        MovieModel model = favoriteHelper.checkDataExists(String.valueOf(movie.getId()));
        if (model != null && model.getId() != null) {
            isFavorite = true;
            materialFavoriteButtonNice.setFavorite(isFavorite, true);
        }
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
        favoriteHelper.open();
        MovieModel favorites = new MovieModel();
        MovieModel movieModel = getIntent().getParcelableExtra("movies");
        Double rate = movieModel.getVoteAverage();
        favorites.setId(movieModel.getId());
        favorites.setTitle(movieModel.getTitle());
        favorites.setPosterPath(movieModel.getPosterPath());
        favorites.setOverview(movieModel.getOverview());
        favorites.setVoteAverage(rate);
        favorites.setReleaseDate(movieModel.getReleaseDate());
        favoriteHelper.addFavorite(favorites);
    }

}
