package com.project.trendingrepositories.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.RequestManager;
import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.R;
import com.project.trendingrepositories.Utils.AppUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder> {

    private ArrayList<Repositories> repositoryList;
    RequestManager requestManager;


    public RepositoryListAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_list_item, parent, false);
        return new RepositoryViewHolder(itemView, requestManager);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        String author = AppUtils.getInstance().stringNotNull(repositoryList.get(position).getAuthor());
        String description = AppUtils.getInstance().stringNotNull(repositoryList.get(position).getDescription());
        String codingLanguage = AppUtils.getInstance().stringNotNull(repositoryList.get(position).getLanguage());
        String repoName = AppUtils.getInstance().stringNotNull(repositoryList.get(position).getName());
        String rating = repositoryList.get(position).getStars() != null ? repositoryList.get(position).getStars().toString() : "0";
        holder.repoAuthor.setText(author);
        holder.repoDescription.setText(description);
        holder.codingLanguage.setText(codingLanguage);
        holder.repoName.setText(repoName);
        holder.rating.setText(rating);
        requestManager
                .load(repositoryList.get(position).getBuiltBy().get(0).getAvatar())
                .into(holder.repoImg);
    }

    @Override
    public int getItemCount() {
        if (repositoryList != null) {
            return repositoryList.size();
        } else {
            return 0;
        }
    }

    public void setRepositoriesData(ArrayList<Repositories> repositoryList) {
        this.repositoryList = repositoryList;
        notifyDataSetChanged();
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repo_img)
        ImageView repoImg;
        @BindView(R.id.repo_author)
        TextView repoAuthor;
        @BindView(R.id.repo_name)
        TextView repoName;
        @BindView(R.id.repo_description)
        TextView repoDescription;
        @BindView(R.id.coding_language)
        TextView codingLanguage;
        @BindView(R.id.rating)
        TextView rating;
        RequestManager requestManager;

        public RepositoryViewHolder(@NonNull View itemView, RequestManager requestManager) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.requestManager = requestManager;
        }
    }
}

