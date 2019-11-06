package com.teoryul.newsly.persistence.repository;

import com.teoryul.newsly.persistence.model.ArticlePersist;
import io.reactivex.Single;

import java.util.List;

public interface ArticleRepository extends BaseRepository<ArticlePersist> {

    void insertArticlesWithReplaceStrategy(List<ArticlePersist> articles);

    void deleteArticlesOlderThan(String newsFeedTitle, long currentTime, long period);

    void deleteArticlesFromNewsFeed(String newsFeedTitle);

    Single<Boolean> getArticleBookmarkedState(int newsArticleId);

    Single<List<ArticlePersist>> getBookmarkedArticles();

    Single<List<ArticlePersist>> getArticlesFromNewsFeed(String newsFeedTitle);
}
