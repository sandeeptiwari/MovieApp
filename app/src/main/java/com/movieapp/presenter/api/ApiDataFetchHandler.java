package com.movieapp.presenter.api;

import android.util.Log;

import com.movieapp.modle.MovieBean;
import com.movieapp.modle.Result;
import com.movieapp.presenter.listener.IMovieDataFetchDoneListener;
import com.movieapp.presenter.listener.IMovieInteracter;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sandeep Tiwari on 11/17/2016.
 */
public class ApiDataFetchHandler implements IMovieInteracter{
    private static final String TAG = ApiDataFetchHandler.class.getSimpleName();
    private IMovieDataFetchDoneListener listener;
    private ApiManager api;
    public ApiDataFetchHandler(){

        Retrofit retrofit = getRetrofitClient();

        // prepare call in Retrofit 2.0
        api = retrofit.create(ApiManager.class);
    }


    public Retrofit getRetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        // okHttpClient.interceptors().add(new DecryptedPayloadInterceptor(this));
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @Override
    public void fetchMoviedata(IMovieDataFetchDoneListener listener) {
        this.listener = listener;

        Call<MovieBean> call = api.getMovieResult();

        call.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                try {
                    if (response.code() == 200) {
                        MovieBean restResponse = response.body();
                        List<Result> results = restResponse.getResults();
                        Log.i(TAG, "SIZE "+results.size());
                        ApiDataFetchHandler.this.listener.onSuccess(results);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "so "+e);
                    ApiDataFetchHandler.this.listener.onError();
                }
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                ApiDataFetchHandler.this.listener.onError();
            }
        });
    }
}
