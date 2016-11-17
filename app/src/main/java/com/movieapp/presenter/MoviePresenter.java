package com.movieapp.presenter;

import android.content.Context;
import android.widget.ImageView;

import com.movieapp.modle.Result;
import com.movieapp.presenter.api.ApiDataFetchHandler;
import com.movieapp.presenter.listener.IMovieDataFetchDoneListener;
import com.movieapp.presenter.listener.IMoviePresenter;
import com.movieapp.presenter.listener.IMovieView;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 11/17/2016.
 */
public class MoviePresenter implements IMoviePresenter, IMovieDataFetchDoneListener {

    private Context context;
    private IMovieView iMovieView;
    private ApiDataFetchHandler apiDataFetchHandler;

    public MoviePresenter(Context context, IMovieView iMovieView){
        this.context = context;
        this.iMovieView = iMovieView;
        apiDataFetchHandler = new ApiDataFetchHandler();
    }



    @Override
    public void fetchMovies() {
        apiDataFetchHandler.fetchMoviedata(this);
    }

    @Override
    public void onSuccess(List<Result> resultList) {
        iMovieView.onSuccessResult(resultList);
    }

    @Override
    public void onError() {

    }
}
