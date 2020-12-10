package com.project.trendingrepositories.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.R;
import com.project.trendingrepositories.Utils.AppUtils;
import com.project.trendingrepositories.Utils.Constants;
import com.project.trendingrepositories.Utils.Resource;
import com.project.trendingrepositories.View.Adapter.RepositoryListAdapter;
import com.project.trendingrepositories.Viewmodel.RepositoryListViewModel.RepositoryListViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryListActivity extends LoaderActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, View.OnClickListener, RepositoryListAdapter.RecyclerViewClickInterface<Repositories> {

    @BindView(R.id.repository_recycler)
    RecyclerView repositoryRecycler;
    RepositoryListViewModel repositoryListViewModel;
    RepositoryListAdapter repositoryListAdapter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.try_again_btn)
    Button tryAgainBtn;
    @BindView(R.id.no_network_layout)
    ConstraintLayout noNetworkLayout;
    private Context context;
    MenuItem searchMenuItem;
    private static final String TAG = "RepositoryListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
        ButterKnife.bind(this);
        context = RepositoryListActivity.this;
        swipeRefreshLayout.setOnRefreshListener(this);
        setTitle(getResources().getString(R.string.app_title));
        repositoryListViewModel = ViewModelProviders.of(this).get(RepositoryListViewModel.class);
        initRepositoryRecyclerView();
        observeRepositoriesResponse(true);
    }

    private void observeRepositoriesResponse(Boolean showLoader) {
        repositoryListViewModel.getRepositories().observe(this, new Observer<Resource<List<Repositories>>>() {
            @Override
            public void onChanged(Resource<List<Repositories>> repositoryResource) {
                if (repositoryResource != null) {
                    displayRepositoryResponse(repositoryResource, showLoader);
                }
            }
        });
    }

    private void displayRepositoryResponse(Resource<List<Repositories>> repositoryResource, Boolean showLoader) {
        if (repositoryResource.data != null) {
            switch (repositoryResource.status) {
                case LOADING: {
                    if (showLoader)
                        showProgressBar(true);
                    break;
                }
                case ERROR: {
                    if (showLoader)
                        showProgressBar(false);
                    if (repositoryResource.data.size() > 0) {
                        if (!repositoryResource.message.isEmpty()) {
                            AppUtils.getInstance().alertDialog(context, context.getResources().getString(R.string.error), repositoryResource.message, context.getResources().getString(R.string.ok));
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.offline_version), Toast.LENGTH_SHORT).show();
                            repositoryRecycler.setVisibility(View.VISIBLE);
                            noNetworkLayout.setVisibility(View.GONE);
                            enableRepositorySearchView();
                            repositoryListAdapter.setRepositoriesData((ArrayList<Repositories>) repositoryResource.data);
                        }
                    } else {
                        AppUtils.getInstance().alertDialog(context, context.getResources().getString(R.string.error), repositoryResource.message, context.getResources().getString(R.string.ok));
                    }
                    break;
                }
                case SUCCESS: {
                    if (showLoader)
                        showProgressBar(false);
                    if (!AppUtils.getInstance().isNetworkAvailable(context) && repositoryResource.data.size() < 1) {
                        repositoryRecycler.setVisibility(View.GONE);
                        noNetworkLayout.setVisibility(View.VISIBLE);
                        tryAgainBtn.setOnClickListener(this);
                    } else {
                        if (!AppUtils.getInstance().isNetworkAvailable(context)){
                            Toast.makeText(context, getResources().getString(R.string.offline_version), Toast.LENGTH_SHORT).show();
                        }
                        repositoryRecycler.setVisibility(View.VISIBLE);
                        noNetworkLayout.setVisibility(View.GONE);
                        enableRepositorySearchView();
                        repositoryListAdapter.setRepositoriesData((ArrayList<Repositories>) repositoryResource.data);
                    }
                    break;
                }
            }
        }
    }

    private void enableRepositorySearchView() {
        if (searchMenuItem != null) {
            searchMenuItem.setVisible(true);
            SearchView searchView = (SearchView) searchMenuItem.getActionView();
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(this);
        }
    }


    private void initRepositoryRecyclerView() {
        repositoryListAdapter = new RepositoryListAdapter(initGlide(), context, this);
        repositoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        repositoryRecycler.setAdapter(repositoryListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        repositoryRecycler.addItemDecoration(dividerItemDecoration);
        /*String jsonString = AppUtils.getInstance().loadJSONFromAsset(this);
        ArrayList<Repositories> repositoryList = new Gson().fromJson(jsonString, new TypeToken<ArrayList<Repositories>>() {
        }.getType());
        repositoryListAdapter.setRepositoriesData(repositoryList);*/
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        repositoryRecycler.setVisibility(View.GONE);
        observeRepositoriesResponse(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchMenuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onRefresh() {
        observeRepositoriesResponse(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        repositoryListAdapter.getFilter().filter(query);
        if (repositoryListAdapter.getItemCount() < 1) {
            Toast.makeText(context, getResources().getString(R.string.no_repositories_found), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        repositoryListAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.try_again_btn) {
            noNetworkLayout.setVisibility(View.GONE);
            observeRepositoriesResponse(true);
        }
    }

    @Override
    public void onItemClick(Repositories repositories) {
        if (repositories != null) {
            Intent intent = new Intent(context, RepositoryDetailsActivity.class);
            intent.putExtra(Constants.REPOSITORY_DATA, repositories);
            startActivity(intent);
        }
    }
}
