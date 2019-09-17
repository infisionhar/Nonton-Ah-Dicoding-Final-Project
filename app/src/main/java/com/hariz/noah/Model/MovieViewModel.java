package com.hariz.noah.Model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> listMovie = new MutableLiveData<>();

    public MutableLiveData<List<MovieModel>> getListMovie() {
        return listMovie;
    }

    public void setListMovie(MutableLiveData<List<MovieModel>> listMovies) {
        listMovie.postValue((List<MovieModel>) listMovies);
    }
}
