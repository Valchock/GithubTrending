package com.project.trendingrepositories.Persistence;

import android.content.Context;

import com.project.trendingrepositories.Model.Repositories;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Repositories.class}, version = 1, exportSchema = false)
public abstract class RepositoryDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "repositories_db";

    private static RepositoryDatabase instance;

    public static RepositoryDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RepositoryDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract RepositoryDao getRepositoryDao();

}
