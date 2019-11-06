package com.teoryul.newsly.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface ArticleDao extends BaseDao<ArticlePersist> {

    @Query("DELETE FROM news_articles_table WHERE parent_news_feed_title = :newsFeedTitle AND published_at > :currentTime + :period")
    void deleteArticlesOlderThan(String newsFeedTitle, long currentTime, long period);

    @Query("DELETE FROM news_articles_table WHERE parent_news_feed_title = :newsFeedTitle")
    void deleteArticlesFromNewsFeed(String newsFeedTitle);

    @Query("SELECT is_bookmarked FROM news_articles_table WHERE news_article_id = :newsArticleId LIMIT 1")
    Single<Boolean> getArticleBookmarkedState(int newsArticleId);

    @Query("SELECT * FROM news_articles_table WHERE is_bookmarked = 1 ORDER BY published_at DESC")
    Single<List<ArticlePersist>> getBookmarkedArticles();

    @Query("SELECT * FROM news_articles_table WHERE parent_news_feed_title = :newsFeedTitle ORDER BY published_at DESC")
    Single<List<ArticlePersist>> getArticlesFromNewsFeed(String newsFeedTitle);
}
