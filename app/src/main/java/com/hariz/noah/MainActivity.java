package com.hariz.noah;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hariz.noah.Fragment.MovieListFragment;
import com.hariz.noah.Fragment.TvListFragment;

public class MainActivity extends AppCompatActivity {
    private final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";
    Fragment fragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.movie_title:
                    fragment = new MovieListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_content, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.tv_title:
                    fragment = new TvListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_content, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Nonton Ah");

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.movie_title);
            fragment = getSupportFragmentManager().findFragmentByTag(SIMPLE_FRAGMENT_TAG);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            case R.id.fav:
                startActivity(new Intent(MainActivity.this, FavoritActivity.class));
                return true;
        }
        return false;
    }
}
