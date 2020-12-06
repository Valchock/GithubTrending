package com.project.trendingrepositories.Viewmodel.RepositoryListViewModel;

import android.app.Application;

import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.Utils.Resource;
import com.project.trendingrepositories.repositories.GithubTrendingRepositories;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RepositoryListViewModel extends AndroidViewModel {

    GithubTrendingRepositories githubTrendingRepositories;

    public RepositoryListViewModel(@NonNull Application application) {
        super(application);
        githubTrendingRepositories = GithubTrendingRepositories.getInstance(application);
    }

    public LiveData<Resource<List<Repositories>>> getRepositories() {
        return githubTrendingRepositories.fetchTrendingRepositoriesApi();
    }
}
