package com.movieapp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.movieapp.modle.R;
import com.movieapp.modle.Result;
import com.movieapp.presenter.MoviePresenter;
import com.movieapp.presenter.listener.IItemClickListener;
import com.movieapp.presenter.listener.IMovieView;
import com.movieapp.view.adapter.MovieViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMovieView, IItemClickListener {

    @BindView(R.id.fragment_images_progress)
    ProgressBar progressBar;
    @BindView(R.id.rv_movie_view)RecyclerView rvMovie;
    private MovieViewAdapter movieViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MoviePresenter moviePresenter = new MoviePresenter(this, this);
        progressBar.setVisibility(View.VISIBLE);
        rvMovie.setVisibility(View.GONE);
        moviePresenter.fetchMovies();
    }



    @Override
    public void onSuccessResult(List<Result> resultList) {
        progressBar.setVisibility(View.GONE);
        rvMovie.setVisibility(View.VISIBLE);
        movieViewAdapter = new MovieViewAdapter(resultList);
        movieViewAdapter.setOnItemClickListener(this);
        rvMovie.setAdapter(movieViewAdapter);
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onError() {

    }

    @Override
    public void onClick(View v, int position) {
        Toast.makeText(this, "Click item "+position, Toast.LENGTH_LONG).show();
    }
}
