package com.project.trendingrepositories.Persistence;

import com.project.trendingrepositories.Model.Repositories;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertRepositories(List<Repositories> githubRepository);

    @Query("SELECT * FROM repositories")
    LiveData<List<Repositories>> getRepositories();
}
