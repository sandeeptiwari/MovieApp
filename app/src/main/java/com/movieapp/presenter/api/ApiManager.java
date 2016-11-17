package com.movieapp.presenter.api;

import com.movieapp.modle.MovieBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sandeep Tiwari on 11/9/2016.
 */
public interface ApiManager {
    //@FormUrlEncoded
    //@POST("/api/user")
    //Call<CreateUserResponse> createUser(@Header("Authorization") String token, @Field("role") String role);
  //https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=8aa3c598a0c67ecf36413f81c411c555

    @GET("discover/movie?sort_by=popularity.desc&api_key=8aa3c598a0c67ecf36413f81c411c555")
    Call<MovieBean> getMovieResult();
}

