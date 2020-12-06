package com.project.trendingrepositories.View;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.R;
import com.project.trendingrepositories.Utils.Resource;
import com.project.trendingrepositories.View.Adapter.RepositoryListAdapter;
import com.project.trendingrepositories.Viewmodel.RepositoryListViewModel.RepositoryListViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
    RepositoryListAdapter repositoryListAdapter;
    private static final String TAG = "RepositoryListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
        ButterKnife.bind(this);
        repositoryListViewModel = ViewModelProviders.of(this).get(RepositoryListViewModel.class);
        initRepositoryRecyclerView();
        observeRepositoriesResponse();
    }

    private void observeRepositoriesResponse() {
        repositoryListViewModel.getRepositories().observe(this, new Observer<Resource<List<Repositories>>>() {
            @Override
            public void onChanged(Resource<List<Repositories>> repositoryResource) {
                if (repositoryResource != null) {
                    if (repositoryResource.data != null) {
                        switch (repositoryResource.status) {
                            case LOADING: {
                                Log.e(TAG, "onChanged: Loading message: " + repositoryResource.message);
                                break;
                            }
                            case ERROR: {
                                Log.e(TAG, "onChanged: ERROR message: " + repositoryResource.message);
                                repositoryListAdapter.setRepositoriesData((ArrayList<Repositories>) repositoryResource.data);
                                break;
                            }
                            case SUCCESS: {
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                repositoryListAdapter.setRepositoriesData((ArrayList<Repositories>) repositoryResource.data);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void initRepositoryRecyclerView() {
        repositoryListAdapter = new RepositoryListAdapter(initGlide());
        repositoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        repositoryRecycler.setAdapter(repositoryListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        repositoryRecycler.addItemDecoration(dividerItemDecoration);
        //String jsonString = AppUtils.getInstance().loadJSONFromAsset(this);
        //ArrayList<Repositories> repositoryList = new Gson().fromJson(jsonString, new TypeToken<ArrayList<Repositories>>() {
        //}.getType());
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }


}
