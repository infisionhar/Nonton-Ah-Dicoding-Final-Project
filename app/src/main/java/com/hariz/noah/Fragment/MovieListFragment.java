package com.hariz.noah.Fragment;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hariz.noah.Adapter.MovieAdapter;
import com.hariz.noah.BuildConfig;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Model.MovieViewModel;
import com.hariz.noah.Model.Response.MovieResponse;
import com.hariz.noah.Network.RetrofitHelper;
import com.hariz.noah.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment implements SearchView.OnQueryTextListener {
    private final String SOME_VALUE_KEY = "someValueToSave";
    private final String MOVIE_LIST_KEY = "movieListKey";
    LinearLayoutManager manager;
    ProgressDialog dialog;
    Unbinder unbinder;
    //    @BindView(R.id.rv_movie_list)
//    RecyclerView recyclerView;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<MovieModel> list;
    private MovieViewModel mainViewModel;
    private int someStateValue;
    private Observer<List<MovieModel>> getMovie = new Observer<List<MovieModel>>() {
        @Override
        public void onChanged(List<MovieModel> weatherItems) {
            if (weatherItems != null) {
                adapter.setData(weatherItems);
            }
        }

    };

    SearchView searchView;

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String movie) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        recyclerView = view.findViewById(R.id.rv_movie_list);
        init();
        if (savedInstanceState != null) {
            someStateValue = savedInstanceState.getInt(SOME_VALUE_KEY);
            list = savedInstanceState.getParcelableArrayList(MOVIE_LIST_KEY);
            if (list != null && list.size() > 0) {
                adapter.setData(list);
            } else {
                loadData();
                init();
            }
        } else {
            loadData();
            init();
        }
        mainViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mainViewModel.getListMovie().observe(this, getMovie);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SOME_VALUE_KEY, someStateValue);
        outState.putParcelableArrayList(MOVIE_LIST_KEY, (ArrayList<? extends Parcelable>) list);
        super.onSaveInstanceState(outState);
    }

    private void init() {
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        adapter = new MovieAdapter(getContext(), list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void loadsearch() {
        String cari_movie = searchView.getQuery().toString();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();
        RetrofitHelper.getService().getItemSearch(cari_movie)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            for (MovieModel r : response.body().getResults()) {
                                list.add(r);
                                Log.d("", "onResponse: " + r);
                                dialog.dismiss();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Movie Tidak ada", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }


    private void loadData() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();

        RetrofitHelper.getService().getMovie(BuildConfig.THE_MOVIE_API_TOKEN)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            for (MovieModel r : response.body().getResults()) {
                                list.add(r);
                                Log.d("", "onResponse: " + r);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Movie Tidak ada", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        loadsearch();
        init();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
