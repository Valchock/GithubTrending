package com.project.trendingrepositories.Persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.trendingrepositories.Model.Repositories;

import java.io.Serializable;

import androidx.room.TypeConverter;
import java.lang.reflect.Type;
import java.util.List;

public class TypeConvertors implements Serializable {
    @TypeConverter
    public String fromBuiltByList(List<Repositories.BuiltBy> builtBy) {
        if (builtBy == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Repositories.BuiltBy>>() {
        }.getType();
        String json = gson.toJson(builtBy, type);
        return json;
    }

    @TypeConverter
    public List<Repositories.BuiltBy> toBuiltByList(String builtByString) {
        if (builtByString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Repositories.BuiltBy>>() {
        }.getType();
        List<Repositories.BuiltBy> list = gson.fromJson(builtByString, type);
        return list;
    }
}
