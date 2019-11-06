package com.teoryul.newsly.persistence.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Model class for storing a list of {@link ArticlePersist} objects
 * for a particular source or topic, personalized feed, country feed (ie. BG, DE, UK).
 */
@Entity(tableName = "news_feeds_table")
public class NewsFeedPersist {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "news_feed_title")
    private String newsFeedTitle;

    public NewsFeedPersist(String newsFeedTitle) {
        this.newsFeedTitle = newsFeedTitle;
    }

    public String getNewsFeedTitle() {
        return newsFeedTitle;
    }
}
