package com.hariz.noah;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritTvFragment extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorit_tv, container, false);
    }

}
