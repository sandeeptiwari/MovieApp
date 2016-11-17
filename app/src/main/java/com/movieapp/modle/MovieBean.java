package com.movieapp.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep Tiwari on 11/17/2016.
 */
public class MovieBean {
    @SerializedName("page")
    @Expose
    public int page;

    @SerializedName("results")
    @Expose
    public List<Result> results = new ArrayList<Result>();

    @SerializedName("total_results")
    @Expose
    public int totalResults;

    @SerializedName("total_pages")
    @Expose
    public int totalPages;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
