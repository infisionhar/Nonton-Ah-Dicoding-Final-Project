package com.hariz.noah;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hariz.noah.Adapter.TvAdapter;
import com.hariz.noah.Model.TvModel;
import com.hariz.noah.Network.Database.FavHelper;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritTvFragment extends Fragment {
    Unbinder unbinder;

    RecyclerView recyclerView;
    private List<TvModel> list;
    private FavHelper favoriteHelper;
    private TvAdapter adapter;


    public FavoritTvFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String tv) {
        FavoritTvFragment fragment = new FavoritTvFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorit_tv, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView = view.findViewById(R.id.rv_tv_list_fav);
        return view;
    }

    private class LoadDB extends AsyncTask<Void, Void,List<TvModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (list.size() > 0) {
                list.clear();
            }

        }

        @Override
        protected void onPostExecute(List<TvModel> favorites) {
            super.onPostExecute(favorites);
            list.addAll(favorites);
            adapter.setListFavorite(list);
            adapter.notifyDataSetChanged();

            if (list.size() == 0) {
                Toast.makeText(getActivity(), "tidak ada data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected List<TvModel> doInBackground(Void... voids) {
            return favoriteHelper.query2();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null) {
            favoriteHelper.close();
        }
    }

    @Override
    public void onResume() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        favoriteHelper = new FavHelper(getActivity());
        favoriteHelper.open();
        list = new LinkedList<>();
        adapter = new TvAdapter(getActivity());
        adapter.setListFavorite(list);
        recyclerView.setAdapter(adapter);
        new LoadDB().execute();
        super.onResume();
    }

}
