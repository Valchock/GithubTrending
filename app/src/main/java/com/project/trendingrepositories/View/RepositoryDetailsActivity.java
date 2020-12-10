package com.project.trendingrepositories.View;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.trendingrepositories.Model.Repositories;
import com.project.trendingrepositories.R;
import com.project.trendingrepositories.Utils.AppUtils;
import com.project.trendingrepositories.Utils.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.repo_img)
    ImageView repoImg;
    @BindView(R.id.repo_name)
    TextView repoNameTv;
    @BindView(R.id.repo_stars)
    TextView repoStarsTv;
    @BindView(R.id.repo_forks)
    TextView repoForksTv;
    @BindView(R.id.share_btn)
    Button shareBtn;
    Context context;
    Repositories repositories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);
        ButterKnife.bind(this);
        shareBtn.setOnClickListener(this);
        context = RepositoryDetailsActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bindDataToUi();
    }

    private void bindDataToUi() {
        if (getIntent().getParcelableExtra(Constants.REPOSITORY_DATA) != null) {
            repositories = getIntent().getParcelableExtra(Constants.REPOSITORY_DATA);
            Glide.with(context)
                    .load(repositories.getAvatar())
                    .into(repoImg);
            String repoName = AppUtils.getInstance().stringNotNull(repositories.getName());
            String rating = repositories.getStars() != null ? repositories.getStars().toString() : "0";
            String forks = repositories.getForks() != null ? repositories.getForks().toString() : "0";
            repoNameTv.setText(repoName);
            repoStarsTv.setText(rating + " Stars");
            repoForksTv.setText(forks + " Forks");

        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.share_btn){
            AppUtils.getInstance().shareUrl(RepositoryDetailsActivity.this, repositories.getUrl());
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
