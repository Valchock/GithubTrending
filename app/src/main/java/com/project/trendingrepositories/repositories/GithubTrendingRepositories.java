package com.project.trendingrepositories.repositories;

import android.content.Context;

import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.Network.NetworkManager;
import com.project.trendingrepositories.Persistence.RepositoryDao;
import com.project.trendingrepositories.Persistence.RepositoryDatabase;
import com.project.trendingrepositories.Utils.ApiResponse;
import com.project.trendingrepositories.Utils.AppExecutors;
import com.project.trendingrepositories.Utils.NetworkBoundResource;
import com.project.trendingrepositories.Utils.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class GithubTrendingRepositories {
    private static final String TAG = "TrendingRepository";

    private static GithubTrendingRepositories instance;
    private RepositoryDao repositoryDao;


    public static GithubTrendingRepositories getInstance(Context context) {
        if (instance == null) {
            synchronized (GithubTrendingRepositories.class) {
                if (instance == null) {
                    instance = new GithubTrendingRepositories(context);
                }
            }
        }
        return instance;
    }


    private GithubTrendingRepositories(Context context) {
        repositoryDao = RepositoryDatabase.getInstance(context).getRepositoryDao();
    }


    public LiveData<Resource<List<Repositories>>> fetchTrendingRepositoriesApi() {

        return new NetworkBoundResource<List<Repositories>, List<Repositories>>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull List<Repositories> item) {
                List<Repositories> repositories = item;
                repositoryDao.insertRepositories(repositories);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Repositories> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Repositories>> loadFromDb() {
                return repositoryDao.getRepositories();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Repositories>>> createCall() {
                return NetworkManager.getInstance().getApiInterface().fetchTrendingRepositories();
            }
        }.getAsLiveData();
    }


}


