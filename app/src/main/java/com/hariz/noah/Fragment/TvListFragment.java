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

import com.hariz.noah.Adapter.TvAdapter;
import com.hariz.noah.BuildConfig;
import com.hariz.noah.Model.Response.TvResponse;
import com.hariz.noah.Model.TvModel;
import com.hariz.noah.Model.TvViewModel;
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
public class TvListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private final String SOME_VALUE_KEY = "someValueToSave";
    private final String MOVIE_LIST_KEY = "movieListKey";
    LinearLayoutManager manager;
    ProgressDialog dialog;
    Unbinder unbinder;
    private RecyclerView recyclerView;
    private TvAdapter adapter;
    private List<TvModel> list;
    private TvViewModel mainViewModel;
    private int someStateValue;
    private Observer<List<TvModel>> getTv = new Observer<List<TvModel>>() {
        @Override
        public void onChanged(List<TvModel> tvItems) {
            if (tvItems != null) {
                adapter.setData(tvItems);
            }
        }
    };


    SearchView searchView;
    public TvListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String tv) {
        TvListFragment fragment = new TvListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView = view.findViewById(R.id.rv_tv_list);
        searchView = view.findViewById(R.id.search_tv);
        searchView.setOnQueryTextListener(this);
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
        mainViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        mainViewModel.getListMovie().observe(this, getTv);
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

        adapter = new TvAdapter(getContext(), list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void loadsearch() {
        String cari_movie = searchView.getQuery().toString();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();
        RetrofitHelper.getService().getItemTvSearch(cari_movie)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if (response.body() != null) {
                            for (TvModel r : response.body().getResults()) {
                                list.add(r);
                                Log.d("", "onResponse: " + r);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {
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

        RetrofitHelper.getService().getTvMovie(BuildConfig.THE_MOVIE_API_TOKEN)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if (response.body() != null) {
                            for (TvModel r : response.body().getResults()) {
                                list.add(r);
                                Log.d("", "onResponse: " + r);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        loadsearch();
        init();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
