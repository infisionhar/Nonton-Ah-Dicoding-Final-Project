package com.hariz.noah.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hariz.noah.Adapter.MovieAdapter;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Network.Database.FavHelper;
import com.hariz.noah.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritMovieFragment extends Fragment {

    Unbinder unbinder;
    RecyclerView recyclerView;
    private List<MovieModel> list;
    private FavHelper favoriteHelper;
    private MovieAdapter adapter;

    public FavoritMovieFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String movie) {
        FavoritMovieFragment fragment = new FavoritMovieFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorit_movie, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView = view.findViewById(R.id.rv_movie_list_fav);
        return view;
    }

    @Override
    public void onResume() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        favoriteHelper = new FavHelper(getActivity());
        favoriteHelper.open();
        list = new ArrayList<>();
        adapter = new MovieAdapter(getActivity(), list);
        adapter.setListFavorite(list);
        recyclerView.setAdapter(adapter);
        Log.e(TAG, "huwi " + list.size() + "");
        new Load().execute();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null) {
            favoriteHelper.close();
        }
    }

    private class Load extends AsyncTask<Void, Void, List<MovieModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (list.size() > 0) {
                list.clear();
            }

        }

        @Override
        protected void onPostExecute(List<MovieModel> favorites) {
            super.onPostExecute(favorites);
            list.addAll(favorites);
            adapter.setListFavorite(list);
            adapter.notifyDataSetChanged();

            Log.e(TAG, "huwi " + list.size() + "");
            if (list.size() == 0) {
                Toast.makeText(getActivity(), "tidak ada data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected List<MovieModel> doInBackground(Void... voids) {
            return favoriteHelper.query();
        }


    }
}
