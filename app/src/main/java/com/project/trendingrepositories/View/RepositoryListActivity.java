package com.project.trendingrepositories.View;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.R;
import com.project.trendingrepositories.Utils.AppUtils;
import com.project.trendingrepositories.View.Adapter.RepositoryListAdapter;
import com.project.trendingrepositories.Viewmodel.RepositoryListViewModel.RepositoryListViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryListActivity extends AppCompatActivity {

    @BindView(R.id.repository_recycler)
    RecyclerView repositoryRecycler;
    RepositoryListViewModel repositoryListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
        ButterKnife.bind(this);
        repositoryListViewModel = ViewModelProviders.of(this).get(RepositoryListViewModel.class);
        initRepositoryRecyclerView();
    }

    private void initRepositoryRecyclerView() {
        RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter(initGlide());
        repositoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        repositoryRecycler.setAdapter(repositoryListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        repositoryRecycler.addItemDecoration(dividerItemDecoration);
        String jsonString = AppUtils.getInstance().loadJSONFromAsset(this);
        ArrayList<Repositories> repositoryList = new Gson().fromJson(jsonString, new TypeToken<ArrayList<Repositories>>() {}.getType());
        repositoryListAdapter.setRepositoriesData(repositoryList);
    }

    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }


}
