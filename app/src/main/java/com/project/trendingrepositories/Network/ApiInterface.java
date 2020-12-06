package com.project.trendingrepositories.Network;

import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.Utils.ApiResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("repositories")
    LiveData<ApiResponse<List<Repositories>>> fetchTrendingRepositories();
}
