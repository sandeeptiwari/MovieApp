package com.movieapp.presenter.listener;

import com.movieapp.modle.Result;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 11/17/2016.
 */
public interface IMovieView {

    void onSuccessResult(List<Result> resultList);
    void onError();
}
