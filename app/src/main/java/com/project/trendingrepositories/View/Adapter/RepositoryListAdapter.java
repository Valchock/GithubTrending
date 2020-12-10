package com.project.trendingrepositories.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.R;
import com.project.trendingrepositories.Utils.AppUtils;

import java.util.ArrayList;
import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder> implements Filterable {


    private ArrayList<Repositories> repositoryList;
    private ArrayList<Repositories> repositoryListAll;
    RequestManager requestManager;
    Context context;


    public RepositoryListAdapter(RequestManager requestManager, Context context) {
        this.requestManager = requestManager;
        this.context = context;
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
        String languageColor = repositoryList.get(position).getLanguageColor();
        int color = languageColor != null ? Color.parseColor(languageColor) : context.getResources().getColor(R.color.colorGrey);
        String rating = repositoryList.get(position).getStars() != null ? repositoryList.get(position).getStars().toString() : "0";
        holder.repoAuthor.setText(author);
        holder.repoDescription.setText(description);
        holder.codingLanguage.setText(codingLanguage);
        holder.repoName.setText(repoName);
        holder.rating.setText(rating);
        holder.codingLanguageColor.getDrawable().mutate().setTint(color);
        requestManager
                .load(repositoryList.get(position).getAvatar())
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
        repositoryListAll = new ArrayList<>();
        repositoryListAll.addAll(repositoryList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return repositorySearchFilter;
    }

    Filter repositorySearchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Repositories> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(repositoryListAll);
            } else {
                for (Repositories repository : repositoryListAll) {
                    if (repository.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(repository);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            repositoryList.clear();
            repositoryList.addAll((Collection<? extends Repositories>) filterResults.values);
            notifyDataSetChanged();
        }
    };

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
        @BindView(R.id.coding_language_color)
        ImageButton codingLanguageColor;

        public RepositoryViewHolder(@NonNull View itemView, RequestManager requestManager) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.requestManager = requestManager;
        }
    }
}

