package com.project.trendingrepositories.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.trendingrepositories.Persistence.TypeConvertors;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "repositories")
public class Repositories {
    @SerializedName("author")
    @Expose
    private String author;
    @PrimaryKey
    @NonNull
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("languageColor")
    @Expose
    private String languageColor;
    @SerializedName("stars")
    @Expose
    private Integer stars;
    @SerializedName("forks")
    @Expose
    private Integer forks;
    @SerializedName("currentPeriodStars")
    @Expose
    private Integer currentPeriodStars;
    @TypeConverters(TypeConvertors.class)
    @SerializedName("builtBy")
    @Expose
    private List<BuiltBy> builtBy = null;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(Integer currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public List<BuiltBy> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<BuiltBy> builtBy) {
        this.builtBy = builtBy;
    }

    public class BuiltBy {

        @SerializedName("href")
        @Expose
        private String href;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("username")
        @Expose
        private String username;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }
}
