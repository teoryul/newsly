package com.teoryul.newsly.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.teoryul.newsly.persistence.model.NewsFeedPersist;

@Dao
public interface NewsFeedDao extends BaseDao<NewsFeedPersist> {

    @Query("DELETE FROM news_feeds_table WHERE news_feed_title = :title")
    void deleteNewsFeedByTitle(String title);
}
