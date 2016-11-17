package com.movieapp.presenter.listener;

import com.movieapp.modle.Result;

import java.util.List;

/**
 * Created by gur38618 on 11/17/2016.
 */
public interface IMovieDataFetchDoneListener {

    void onSuccess(List<Result> resultList);
    void onError();
}
