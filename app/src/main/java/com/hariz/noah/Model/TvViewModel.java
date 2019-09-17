package com.hariz.noah.Model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class TvViewModel extends ViewModel {
    private MutableLiveData<List<TvModel>> listTV = new MutableLiveData<>();

    public MutableLiveData<List<TvModel>> getListMovie() {
        return listTV;
    }

    public void setListMovie(MutableLiveData<List<TvModel>> listTV) {
        listTV.postValue((List<TvModel>) listTV);
    }
}
